package org.hzero.halm.atn.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.CaseFormat;
import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.AssetRepository;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
import org.hzero.halm.atn.app.service.OrderDynamicColumnService;
import org.hzero.halm.atn.app.service.TransactionHistoryService;
import org.hzero.halm.atn.app.service.TransferOrderLineService;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;
import org.hzero.halm.atn.domain.entity.TransferOrderHeader;
import org.hzero.halm.atn.domain.entity.TransferOrderLine;
import org.hzero.halm.atn.domain.repository.OrderDynamicColumnRepository;
import org.hzero.halm.atn.domain.repository.TransferOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.TransferOrderLineRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.constant.AatnTransferOrderConstantControlFields;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 调拨转移单行应用服务默认实现
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:33
 */
@Service
public class TransferOrderLineServiceImpl implements TransferOrderLineService {

	@Autowired
	private TransferOrderLineRepository transferOrderLineRepository;
	@Autowired
	private AssetRepository assetRepository;
	@Autowired
	private TransactionTypeFieldsRepository transactionTypeFieldsRepository;
	@Autowired
	private CommonProcessUtils commonProcessUtils;
	@Autowired
	private TransferOrderHeaderRepository transferOrderHeaderRepository;
	@Autowired
	private OrderDynamicColumnService orderDynamicColumnService;
	@Autowired
	private TransactionHistoryService historyService;
	@Autowired
	private OrderDynamicColumnRepository orderDynamicColumnRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TransferOrderLine transferConfirm(Long tenantId, TransferOrderLine transferOrderLine) {

		TransferOrderLine entity = transferOrderLineRepository.selectTransferLineById(transferOrderLine.getTransferLineId());
		Assert.notNull(entity, BaseConstants.ErrorCode.DATA_NOT_EXISTS);

		Asset asset = assetRepository.selectByPrimaryKey(transferOrderLine.getAssetId());
		Assert.notNull(asset, BaseConstants.ErrorCode.DATA_INVALID);
		/*
		 * 1、	“调出确认”：未处理状态下显示该按钮，确认后，状态变为"在途" 更改资产状态为  过程中资产状态
		 * 2、	“调入确认”：在途状态下显示该按钮，确认后，状态变为"已完成" 更改资产状态、所属组织、成本中心为目标值
		 */
		switch (entity.getProcessStatus()) {
			case AatnConstans.TransferOrderLineStatus.UNPROCESSED:
				transferOrderLine.setProcessStatus(AatnConstans.TransferOrderLineStatus.ON_THE_WAY);
				asset.setAssetStatusId(entity.getInprocessAssetStatusId());
				assetRepository.updateOptional(asset, Asset.FIELD_ASSET_STATUS_ID);
				break;
			case AatnConstans.TransferOrderLineStatus.ON_THE_WAY:
				transferOrderLine.setProcessStatus(AatnConstans.TransferOrderLineStatus.COMPLETED);
				asset.setAssetStatusId(entity.getTargetAssetStatusId());
				asset.setOwningOrgId(entity.getTargetOwningOrg());
//                asset.setCostCenterCode(entity.getCurrentCostCenter());
//				assetRepository.updateOptional(asset,
//						Asset.FIELD_ASSET_STATUS_ID,
//						Asset.FIELD_OWNING_ORG_ID
////                        Asset.FIELD_COST_CENTER_CODE
//				);
				// 查询动态字段,修改动态字段
				List<OrderDynamicColumn> orderDynamicColumnList = orderDynamicColumnRepository.selectByCondition(
						Condition.builder(OrderDynamicColumn.class)
								.andWhere(Sqls.custom()
										.andEqualTo(OrderDynamicColumn.FIELD_ORDER_LINE_ID, transferOrderLine.getTransferLineId())
										.andEqualTo(OrderDynamicColumn.FIELD_ORDER_HEADER_ID, transferOrderLine.getTransferHeaderId())
								).build());
				assetRepository.updateAssetOnDynamicColumn(orderDynamicColumnList,asset);
				break;
			default:
				throw new CommonException(AatnConstans.AatnErrorCode.AATN_PROCESS_STATUS_INVALID);
		}

		// 更新行处理状态
		transferOrderLineRepository.updateOptional(transferOrderLine, TransferOrderLine.FIELD_PROCESS_STATUS);

		// 单据中所有的行
		List<TransferOrderLine> lineEntities = transferOrderLineRepository.selectByCondition(Condition.builder(TransferOrderLine.class)
				.andWhere(Sqls.custom().andEqualTo(TransferOrderLine.FIELD_TRANSFER_HEADER_ID, entity.getTransferHeaderId())).build());
		// 数据库中的单据头
		TransferOrderHeader headerEntity = transferOrderHeaderRepository.selectByPrimaryKey(transferOrderLine.getTransferHeaderId());

		// 已完成数量
		Long completeNumber = lineEntities.stream().filter(line -> StringUtils.equals(AatnConstans.TransferOrderLineStatus.COMPLETED, line.getProcessStatus())).count();

		if (completeNumber < lineEntities.size()) {
			// 部分完成
			headerEntity.setProcessStatus(AatnConstans.ApproveStatus.PROCESSING);
		} else if (completeNumber == lineEntities.size()) {
			// 全部完成
			headerEntity.setProcessStatus(AatnConstans.ApproveStatus.COMPLETED);
		}
		transferOrderHeaderRepository.updateOptional(headerEntity, TransferOrderHeader.FIELD_PROCESS_STATUS);
		historyService.addTransactionHistory(transferOrderLine, transferOrderLineRepository, transferOrderHeaderRepository);
		return transferOrderLine;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<TransferOrderLine> saveTransferOrderLines(Long tenantId, Long transferHeaderId, TransactionTypes transactionTypes, List<TransferOrderLine> transferOrderLines) {
		if (CollectionUtils.isEmpty(transferOrderLines)) {
			return transferOrderLines;
		}

		// 资产状态变更范围
		List<String> statusScope = JSON.parseArray(transactionTypes.getStatusScope(), String.class);
		// 所属组织变更范围
		List<String> orgScope = JSON.parseArray(transactionTypes.getOrganizationScope(), String.class);
		// 专业资产变更范围
		List<String> specialScope = JSON.parseArray(transactionTypes.getSpecialtyScope(), String.class);
		// 获取字段控制规则
		Map<String, String> fieldRulesMap = transactionTypeFieldsRepository.selectByCondition(Condition.builder(TransactionTypeFields.class)
				.andWhere(Sqls.custom()
						.andEqualTo(TransactionTypeFields.FIELD_TRANSATION_TYPE_ID, transactionTypes.getTransactionTypeId())
						.andEqualTo(TransactionTypeFields.FIELD_TENANT_ID, tenantId))
				.build())
				.stream().collect(Collectors.toMap(TransactionTypeFields::getFieldColumn, TransactionTypeFields::getFieldType, (k1, k2) -> k1));

		// 校验目标资产状态、目标所属组织、目标成本中心是否根据控制规则修改
		commonProcessUtils.validTransactionTypeRules(transferOrderLines, fieldRulesMap, AatnTransferOrderConstantControlFields.values());

		// 分组
		List<TransferOrderLine> insertLines = transferOrderLines.stream().filter(line -> Objects.isNull(line.getTransferLineId())).collect(Collectors.toList());
		List<TransferOrderLine> updateLines = transferOrderLines.stream().filter(line -> !Objects.isNull(line.getTransferLineId())).collect(Collectors.toList());

		transferOrderLines.forEach(line -> {
			// 目标资产状态是否在选择范围
			if (!Objects.isNull(line.getTargetAssetStatusId()) && CollectionUtils.isNotEmpty(statusScope) && !statusScope.contains(line.getTargetAssetStatusId().toString())) {
				throw new CommonException(AatnConstans.AatnErrorCode.AATN_ASSET_STATUS_OUT_OF_RANGE, line.getTargetAssetStatusId(), transactionTypes.getStatusScope());
			}
		});

		// 数据库中行已选资产
		Set<Long> entitiesAssetIdSet = transferOrderLineRepository.selectByCondition(Condition.builder(TransferOrderLine.class)
				.andWhere(Sqls.custom()
						.andEqualTo(TransferOrderLine.FIELD_TRANSFER_HEADER_ID, transferHeaderId))
				.build())
				.stream().map(TransferOrderLine::getAssetId).collect(Collectors.toSet());

		if (CollectionUtils.isNotEmpty(insertLines)) {
			Set<Long> insertAssetIdSet = insertLines.stream().map(TransferOrderLine::getAssetId).collect(Collectors.toSet());

			// 校验资产是否重复选择
			CommonProcessUtils.validAsset(insertAssetIdSet, entitiesAssetIdSet, insertLines.size());
			// 写入租户ID和头ID
			insertLines.forEach(line -> {
				line.setTenantId(tenantId);
				line.setTransferHeaderId(transferHeaderId);
				line.setProcessStatus(AatnConstans.TransferOrderLineStatus.NEW);
				if (StringUtils.isEmpty(line.getDescription())) {
					line.setDescription(StringUtils.EMPTY);
				}
				transferOrderLineRepository.insertSelective(line);
				// 行明细操作
				OrderDynamicColumn.validScope(orgScope, specialScope, line.getOrderDynamicColumnList());
				orderDynamicColumnService.saveDynamicColumns(tenantId, line.getTransferLineId(), transferHeaderId, AatnConstans.TransactionType.TRANSFER, line.getOrderDynamicColumnList(), fieldRulesMap);
			});

			entitiesAssetIdSet.addAll(insertAssetIdSet);
		}

		if (CollectionUtils.isNotEmpty(updateLines)) {
			updateLines.forEach(line -> {
				transferOrderLineRepository.updateOptional(line,
						TransferOrderLine.FIELD_TARGET_ASSET_STATUS_ID,
						TransferOrderLine.FIELD_TARGET_COST_CENTER,
						TransferOrderLine.FIELD_TARGET_OWNING_ORG_ID,
						TransferOrderLine.FIELD_DESCRIPTION);
				// 行明细操作
				OrderDynamicColumn.validScope(orgScope, specialScope, line.getOrderDynamicColumnList());
				orderDynamicColumnService.saveDynamicColumns(tenantId, line.getTransferLineId(), transferHeaderId, AatnConstans.TransactionType.TRANSFER, line.getOrderDynamicColumnList(), fieldRulesMap);
			});

		}

		insertLines.addAll(updateLines);
		return insertLines;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public TransferOrderHeader submitTransferOrder(Long tenantId, TransferOrderHeader transferOrderHeader) {

		TransferOrderHeader entity = transferOrderHeaderRepository.selectByPrimaryKey(transferOrderHeader.getTransferHeaderId());
		Assert.notNull(entity, BaseConstants.ErrorCode.DATA_INVALID);
		Assert.isTrue(StringUtils.equals(entity.getProcessStatus(), AatnConstans.ApproveStatus.NEW), AatnConstans.AatnErrorCode.AATN_PROCESS_STATUS_INVALID);

		List<TransferOrderLine> lineEntities = transferOrderLineRepository.selectByCondition(Condition.builder(TransferOrderLine.class)
				.andWhere(Sqls.custom().andEqualTo(TransferOrderLine.FIELD_TRANSFER_HEADER_ID, transferOrderHeader.getTransferHeaderId())).build());

		// 提交直接审批 TODO
		entity.setProcessStatus(AatnConstans.ApproveStatus.APPROVED);

		if (CollectionUtils.isNotEmpty(lineEntities)) {
			lineEntities.forEach(line -> line.setProcessStatus(AatnConstans.TransferOrderLineStatus.UNPROCESSED));
		}

		transferOrderLineRepository.batchUpdateOptional(lineEntities, TransferOrderLine.FIELD_PROCESS_STATUS);
		transferOrderHeaderRepository.updateOptional(entity, TransferOrderHeader.FIELD_PROCESS_STATUS);
		entity.setTransferOrderLines(lineEntities);
		return entity;
	}
}
