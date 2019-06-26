package org.hzero.halm.atn.app.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.CaseFormat;
import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.AssetRepository;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
import org.hzero.halm.atn.app.service.OrderDynamicColumnService;
import org.hzero.halm.atn.app.service.ScrapOrderLineService;
import org.hzero.halm.atn.app.service.TransactionHistoryService;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;
import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;
import org.hzero.halm.atn.domain.entity.ScrapOrderLine;
import org.hzero.halm.atn.domain.repository.OrderDynamicColumnRepository;
import org.hzero.halm.atn.domain.repository.ScrapOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.ScrapOrderLineRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 资产报废单行应用服务默认实现
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:10
 */
@Service
public class ScrapOrderLineServiceImpl implements ScrapOrderLineService {

	@Autowired
	private ScrapOrderLineRepository scrapOrderLineRepository;
	@Autowired
	private ScrapOrderHeaderRepository headerRepository;
	@Autowired
	private AssetRepository assetRepository;
	@Autowired
	private TransactionTypeFieldsRepository transactionTypeFieldsRepository;
	@Autowired
	private OrderDynamicColumnService orderDynamicColumnService;
	@Autowired
	private OrderDynamicColumnRepository orderDynamicColumnRepository;
	@Autowired
	private TransactionHistoryService historyService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ScrapOrderLine scrapConfirm(Long tenantId, ScrapOrderLine scrapOrderLine) {

		ScrapOrderLine entity = scrapOrderLineRepository.selectScrapLineById(scrapOrderLine.getScrapLineId());
		Assert.notNull(entity, BaseConstants.ErrorCode.DATA_NOT_EXISTS);

		Asset asset = assetRepository.selectByPrimaryKey(scrapOrderLine.getAssetId());
		Assert.notNull(asset, BaseConstants.ErrorCode.DATA_INVALID);
		//查询动态字段
		List<OrderDynamicColumn> orderDynamicColumnList = orderDynamicColumnRepository.selectByCondition(
				Condition.builder(OrderDynamicColumn.class)
						.andWhere(Sqls.custom()
								.andEqualTo(OrderDynamicColumn.FIELD_ORDER_LINE_ID, scrapOrderLine.getScrapLineId())
								.andEqualTo(OrderDynamicColumn.FIELD_ORDER_HEADER_ID, scrapOrderLine.getScrapHeaderId())
						).build());
		/*
		 * 1、	“执行处理”：当前资产事务处理行状态为“待报废”时，资产处理行的明细上出现“报废确认”按钮，点击之后，
		 *                 执行资产台账变更操作，同时资产事务处理行状态变为“已报废”
		 */
		switch (entity.getProcessStatus()) {
			case AatnConstans.ScrapStatus.WAIT_FOR_SCRAP:
				scrapOrderLine.setProcessStatus(AatnConstans.ScrapStatus.SCRAPPED);
				asset.setAssetStatusId(entity.getTargetAssetStatusId());
				asset.setAssetLocationId(entity.getTargetLocationId());
				if (CollectionUtils.isNotEmpty(orderDynamicColumnList)) {
					for (OrderDynamicColumn scrapDetail : orderDynamicColumnList) {
						String columnName = scrapDetail.getCurrentColumnName();
						Object targetValue = orderDynamicColumnService.columnTypeConversion(scrapDetail.getTargetColumnType(), scrapDetail.getTargetColumnValue());
						try {
							FieldUtils.writeDeclaredField(asset, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName), targetValue, true);
						} catch (IllegalAccessException e) {
							throw new CommonException(e);
						}
					}
				}
				assetRepository.updateByPrimaryKeySelective(asset);
				break;
			default:
				throw new CommonException(AatnConstans.AatnErrorCode.AATN_PROCESS_STATUS_INVALID);
		}

		// 更新行处理状态
		scrapOrderLineRepository.updateOptional(scrapOrderLine, ScrapOrderLine.FIELD_PROCESS_STATUS);
		historyService.addTransactionHistory(scrapOrderLine, scrapOrderLineRepository, headerRepository);

		//更新头状态
		ScrapOrderHeader scrapOrderHeader = headerRepository.selectByPrimaryKey(entity.getScrapHeaderId());
		// 未报废行的数量
		int scrapedLineCount = scrapOrderLineRepository.selectCountByCondition(Condition.builder(ScrapOrderLine.class)
				.andWhere(Sqls.custom()
						.andEqualTo(ScrapOrderLine.FIELD_SCRAP_HEADER_ID, scrapOrderLine.getScrapHeaderId())
//						.andNotEqualTo(ScrapOrderLine.FIELD_SCRAP_LINE_ID, scrapOrderLine.getScrapLineId())
						.andNotEqualTo(ScrapOrderLine.FIELD_PROCESS_STATUS, AatnConstans.ScrapStatus.SCRAPPED)
				).build());
		if (scrapedLineCount > 0) {
			//没有全部报废
			scrapOrderHeader.setProcessStatus(AatnConstans.ApproveStatus.PROCESSING);
		} else {
			//没有全部报废
			scrapOrderHeader.setProcessStatus(AatnConstans.ApproveStatus.COMPLETED);
		}
		headerRepository.updateOptional(scrapOrderHeader, ScrapOrderHeader.FIELD_PROCESS_STATUS);
		return scrapOrderLine;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<ScrapOrderLine> saveScrapOrderLines(Long tenantId, Long scrapHeaderId, TransactionTypes
			transactionTypes, List<ScrapOrderLine> scrapOrderLines) {

		if (CollectionUtils.isEmpty(scrapOrderLines)) {
			return scrapOrderLines;
		}

		// 资产状态变更范围
		List<String> statusScope = (List<String>) JSONArray.parse(transactionTypes.getStatusScope());
		// 获取三个字段控制规则
		Map<String, String> fieldRulesMap = transactionTypeFieldsRepository.selectByCondition(Condition.builder(TransactionTypeFields.class)
				.andWhere(Sqls.custom()
						.andEqualTo(TransactionTypeFields.FIELD_TRANSATION_TYPE_ID, transactionTypes.getTransactionTypeId())
						.andEqualTo(TransactionTypeFields.FIELD_TENANT_ID, tenantId))
				.build())
				.stream().collect(Collectors.toMap(TransactionTypeFields::getFieldColumn, TransactionTypeFields::getFieldType, (k1, k2) -> k1));

		// 校验目标资产状态、目标所属组织、目标成本中心是否根据控制规则修改
//         commonProcessUtils.validTransactionTypeRules(scrapOrderLines, fieldRulesMap);

		// 分组
		List<ScrapOrderLine> insertLines = scrapOrderLines.stream().filter(line -> Objects.isNull(line.getScrapLineId())).collect(Collectors.toList());
		List<ScrapOrderLine> updateLines = scrapOrderLines.stream().filter(line -> !Objects.isNull(line.getScrapLineId())).collect(Collectors.toList());

		scrapOrderLines.forEach(line -> {
			// 目标资产状态是否在选择范围
//			if (statusScope != null &&
//					statusScope.size() > 0 &&
//					!statusScope.contains(line.getTargetAssetStatusId().toString())) {
//				throw new CommonException(AatnConstans.AatnErrorCode.AATN_ASSET_STATUS_OUT_OF_RANGE, line.getTargetAssetStatusId(), statusScope);
//			}
			// 当前资产状态是否在选择范围
			if (statusScope != null &&
					statusScope.size() > 0 &&
					!statusScope.contains(line.getCurrentAssetStatusId().toString())) {
				throw new CommonException(AatnConstans.AatnErrorCode.AATN_CURRENT_ASSET_STATUS_OUT_OF_RANGE, line.getCurrentAssetStatusId(), statusScope);
			}
		});

		if (CollectionUtils.isNotEmpty(insertLines)) {
			// 写入租户ID和头ID
			insertLines.forEach(line -> {
				scrapOrderLineRepository.insertScrapOrderLine(tenantId, scrapHeaderId, line);
			});
		}

		if (CollectionUtils.isNotEmpty(updateLines)) {
			scrapOrderLineRepository.batchUpdateOptional(updateLines,
					ScrapOrderLine.FIELD_ASSET_ID,
					ScrapOrderLine.FIELD_CURRENT_ASSET_STATUS_ID,
					ScrapOrderLine.FIELD_TARGET_ASSET_STATUS_ID,
					ScrapOrderLine.FIELD_CURRENT_LOCATION_ID,
					ScrapOrderLine.FIELD_TARGET_LOCATION_ID,
					ScrapOrderLine.FIELD_ORIGINAL_COST,
					ScrapOrderLine.FIELD_REMAIN_COST,
					ScrapOrderLine.FIELD_CAPITALIZATION_DATE,
					ScrapOrderLine.FIELD_REMAIN_DEPRECIATION_MOUTH,
					ScrapOrderLine.FIELD_SCRAP_TYPE_CODE,
					ScrapOrderLine.FIELD_DISPOSE_TYPE_CODE,
					ScrapOrderLine.FIELD_DESCRIPTION);
		}

		insertLines.addAll(updateLines);
		// 插入/新增动态字段
		for (ScrapOrderLine scrapOrderLineData : insertLines) {
			//动态字段
			List<OrderDynamicColumn> orderDynamicColumnList = scrapOrderLineData.getOrderDynamicColumnList();
			if (CollectionUtils.isNotEmpty(orderDynamicColumnList)) {
				orderDynamicColumnService.saveDynamicColumns(tenantId, scrapOrderLineData.getScrapLineId(), scrapHeaderId, AatnConstans.TransactionType.SCRAP, orderDynamicColumnList, fieldRulesMap);
			}

		}
		return insertLines;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeLine(Long tenantId, ScrapOrderLine scrapOrderLine) {
		if ((AatnConstans.ScrapStatus.NEW).equals(scrapOrderLine.getProcessStatus())) {
			//删除行
			scrapOrderLineRepository.deleteByPrimaryKey(scrapOrderLine);
			//查询行所关联的动态字段，也要删除
			List<OrderDynamicColumn> orderDynamicColumns = orderDynamicColumnRepository.selectByCondition(Condition.builder(OrderDynamicColumn.class)
					.andWhere(Sqls.custom().andEqualTo(OrderDynamicColumn.FIELD_ORDER_LINE_ID, scrapOrderLine.getScrapLineId())).build());
			// 删除行以及行下所有明细字段
			if (CollectionUtils.isNotEmpty(orderDynamicColumns)) {
				orderDynamicColumnRepository.batchDeleteByPrimaryKey(orderDynamicColumns);
			}
		} else {
			throw new CommonException(AatnConstans.AatnErrorCode.AATN_SCRAP_LINE_PROCESS_STATUS_INVALID);
		}
	}
}
