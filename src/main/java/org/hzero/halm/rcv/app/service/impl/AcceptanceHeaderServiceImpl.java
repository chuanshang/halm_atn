package org.hzero.halm.rcv.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.lang.StringUtils;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.boot.platform.code.constant.CodeConstants;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.app.service.AssetService;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.entity.AssetsSet;
import org.hzero.halm.afm.domain.repository.AssetsSetRepository;
import org.hzero.halm.afm.infra.constant.Constants;
import org.hzero.halm.rcv.app.service.AcceptanceAssetService;
import org.hzero.halm.rcv.app.service.AcceptanceHeaderService;
import org.hzero.halm.rcv.domain.entity.*;
import org.hzero.halm.rcv.domain.repository.*;
import org.hzero.halm.rcv.infra.constant.ArcvConstants;
import org.hzero.halm.rcv.infra.mapper.AcceptanceAssetMapper;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 验收单应用服务默认实现
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@Service
public class AcceptanceHeaderServiceImpl implements AcceptanceHeaderService {

	@Autowired
	private AcceptanceHeaderRepository acceptanceHeaderRepository;
	@Autowired
	private AcceptanceRelationRepository acceptanceRelationRepository;
	@Autowired
	private AcceptanceLineRepository acceptanceLineRepository;
	@Autowired
	private AcceptanceAssetRepository acceptanceAssetRepository;
	@Autowired
	AcceptanceAssetService acceptanceAssetService;
	@Autowired
	private CodeRuleBuilder codeRuleBuilder;
	@Autowired
	private AcceptanceTypeRepository acceptanceTypeRepository;
	@Autowired
	private AssetService assetService;
	@Autowired
	AcceptanceAssetMapper acceptanceAssetMapper;
	@Autowired
	AssetsSetRepository assetsSetRepository;
	@Autowired
	DeliveryListRepository deliveryListRepository;


	@Override
	@Transactional(rollbackFor = Exception.class)
	public AcceptanceHeader insertAcceptanceHeader(AcceptanceHeader acceptanceHeader) {

		if (StringUtils.isEmpty(acceptanceHeader.getAcceptanceNum()) && acceptanceHeader.getAcceptanceTypeId() != null) {
			//自动生成验收单编号
			AcceptanceType acceptanceType = acceptanceTypeRepository.selectByPrimaryKey(acceptanceHeader.getAcceptanceTypeId());
			acceptanceHeader.setAcceptanceNum(codeRuleBuilder.generateCode(acceptanceHeader.getTenantId(), acceptanceType.getCodeRule(), CodeConstants.CodeRuleLevelCode.GLOBAL, CodeConstants.CodeRuleLevelCode.GLOBAL, null));
		} else {
			//租户内唯一性校验
			validUniqueAcceptanceHeaderFields(acceptanceHeader);
		}
		//插入验收单头
		acceptanceHeaderRepository.insertSelective(acceptanceHeader);
		//插入验收单关联关系
		List<AcceptanceRelation> acceptanceRelationList = acceptanceHeader.getAcceptanceRelationList();
		if (acceptanceRelationList != null && acceptanceRelationList.size() > 0) {

			List<AcceptanceRelation> newAcceptanceRelationList = new ArrayList<>();

			for (AcceptanceRelation acceptanceRelation : acceptanceRelationList) {
				acceptanceRelation.setAcceptanceHeaderId(acceptanceHeader.getAcceptanceHeaderId());
				acceptanceRelation.setTenantId(acceptanceHeader.getTenantId());
				newAcceptanceRelationList.add(acceptanceRelation);
			}
			acceptanceRelationRepository.batchInsertSelective(newAcceptanceRelationList);
		}
		//插入验收单行
		List<AcceptanceLine> acceptanceLineList = acceptanceHeader.getAcceptanceLineList();
		if (acceptanceLineList != null && acceptanceLineList.size() > 0) {

			List<AcceptanceLine> newAcceptanceLineList = new ArrayList<>();

			for (AcceptanceLine acceptanceLine : acceptanceLineList) {
				acceptanceLine.setAcceptanceHeaderId(acceptanceHeader.getAcceptanceHeaderId());
				acceptanceLine.setTenantId(acceptanceHeader.getTenantId());
				newAcceptanceLineList.add(acceptanceLine);
			}

			acceptanceLineRepository.batchInsertSelective(newAcceptanceLineList);
		}

		acceptanceHeader.setAcceptanceRelationList(acceptanceRelationList);
		acceptanceHeader.setAcceptanceLineList(acceptanceLineList);

		return acceptanceHeader;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AcceptanceHeader updateAcceptanceHeader(AcceptanceHeader acceptanceHeader) {
		//租户内唯一性校验
		validUniqueAcceptanceHeaderFields(acceptanceHeader);
		//更新验收单头
		acceptanceHeaderRepository.updateByPrimaryKeySelective(acceptanceHeader);
		return acceptanceHeader;
	}

	@Override
	public void validUniqueAcceptanceHeaderFields(AcceptanceHeader acceptanceHeader) {
		int acceptanceHeaderCount;
		if (acceptanceHeader.getAcceptanceHeaderId() == null) {
			if (StringUtils.isEmpty(acceptanceHeader.getAcceptanceNum())) {
				throw new CommonException(ArcvConstants.ArcvErrorCode.ARCV_ACCEPTANCE_NUM_IS_NULL);
			}
			acceptanceHeaderCount = acceptanceHeaderRepository.selectCountByCondition(Condition.builder(AcceptanceHeader.class)
					.andWhere(Sqls.custom()
							.andEqualTo(AcceptanceHeader.FIELD_TENANT_ID, acceptanceHeader.getTenantId())
							.andEqualTo(AcceptanceHeader.FIELD_ACCEPTANCE_NUM, acceptanceHeader.getAcceptanceNum())).build());
		} else {
			acceptanceHeaderCount = acceptanceHeaderRepository.selectCountByCondition(Condition.builder(AcceptanceHeader.class)
					.andWhere(Sqls.custom()
							.andEqualTo(AcceptanceHeader.FIELD_TENANT_ID, acceptanceHeader.getTenantId())
							.andEqualTo(AcceptanceHeader.FIELD_ACCEPTANCE_NUM, acceptanceHeader.getAcceptanceNum())
							.andNotEqualTo(AcceptanceHeader.FIELD_ACCEPTANCE_HEADER_ID, acceptanceHeader.getAcceptanceHeaderId())
					).build());
		}
		if (acceptanceHeaderCount > 0) {
			throw new CommonException(ArcvConstants.ArcvErrorCode.ARCV_DUPLICATE_ACCEPTANCE_NUM);
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AcceptanceHeader submitApproval(Long tenantId, Long acceptanceHeaderId) {
		AcceptanceHeader acceptanceHeader = acceptanceHeaderRepository.selectByCondition(Condition.builder(AcceptanceHeader.class)
				.andWhere(
						org.hzero.mybatis.util.Sqls.custom()
								.andEqualTo(AcceptanceHeader.FIELD_TENANT_ID, tenantId)
								.andEqualTo(AcceptanceHeader.FIELD_ACCEPTANCE_HEADER_ID, acceptanceHeaderId)
				).build()).get(0);
		if (acceptanceHeader == null) {
			throw new CommonException(BaseConstants.ErrorCode.DATA_NOT_EXISTS);
		} else if (!acceptanceHeader.getAcceptanceStatusCode().equals(ArcvConstants.acceptanceStatus.NEW)) {
			throw new CommonException(BaseConstants.ErrorCode.DATA_INVALID);
		}
		//查询行,没有行记录无法提交
		List<AcceptanceLine> acceptanceLineList = acceptanceLineRepository.selectByCondition(Condition.builder(AcceptanceLine.class)
				.andWhere(
						org.hzero.mybatis.util.Sqls.custom()
								.andEqualTo(AcceptanceLine.FIELD_TENANT_ID, tenantId)
								.andEqualTo(AcceptanceLine.FIELD_ACCEPTANCE_HEADER_ID, acceptanceHeaderId)
				).build());
		if (acceptanceLineList.size() <= 0) {
			throw new CommonException(BaseConstants.ErrorCode.NOT_FOUND);
		}
		// 由于暂时没有审批逻辑，所以此处直接将状态改为完成
		acceptanceHeader.setAcceptanceStatusCode(ArcvConstants.acceptanceStatus.APPROVED);
		acceptanceHeader.setSubmitDate(new Date());
		acceptanceHeaderRepository.updateOptional(acceptanceHeader,
				AcceptanceHeader.FIELD_ACCEPTANCE_STATUS_CODE,
				AcceptanceHeader.FIELD_SUBMIT_DATE
		);
		return acceptanceHeader;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AcceptanceHeader completeAcceptance(Long tenantId, Long acceptanceHeaderId) {
		AcceptanceHeader acceptanceHeader = acceptanceHeaderRepository.selectByCondition(Condition.builder(AcceptanceHeader.class)
				.andWhere(
						org.hzero.mybatis.util.Sqls.custom()
								.andEqualTo(AcceptanceHeader.FIELD_TENANT_ID, tenantId)
								.andEqualTo(AcceptanceHeader.FIELD_ACCEPTANCE_HEADER_ID, acceptanceHeaderId)
				).build()).get(0);
		if (acceptanceHeader == null) {
			throw new CommonException(BaseConstants.ErrorCode.DATA_NOT_EXISTS);
		} else if (!acceptanceHeader.getAcceptanceStatusCode().equals(ArcvConstants.acceptanceStatus.APPROVED)) {
			throw new CommonException(BaseConstants.ErrorCode.DATA_INVALID);
		}


		List<AcceptanceLine> acceptanceLineList = acceptanceLineRepository.selectByCondition(Condition.builder(AcceptanceLine.class)
				.andWhere(
						org.hzero.mybatis.util.Sqls.custom()
								.andEqualTo(AcceptanceLine.FIELD_TENANT_ID, tenantId)
								.andEqualTo(AcceptanceLine.FIELD_ACCEPTANCE_HEADER_ID, acceptanceHeaderId)
				).build());
		if (acceptanceLineList != null && acceptanceLineList.size() > 0) {
			//验收类型是否有编号规则
			AcceptanceType acceptanceType = acceptanceTypeRepository.selectByPrimaryKey(acceptanceHeader.getAcceptanceTypeId());
			//生成验收单资产明细行
			List<AcceptanceAsset> acceptanceAssetList = new ArrayList<>();

			for (AcceptanceLine acceptanceLine : acceptanceLineList) {
				for (int i = 0; i < acceptanceLine.getDeliveryQuantity().intValue(); i++) {
					AcceptanceAsset acceptanceAsset = new AcceptanceAsset();
					//赋值
					acceptanceAsset.setTenantId(tenantId);
					acceptanceAsset.setAcceptanceLineId(acceptanceLine.getAcceptanceLineId());
					//资产编号
					if (!StringUtils.isEmpty(acceptanceType.getCodeRule())) {
						acceptanceAsset.setAssetNum(codeRuleBuilder.generateCode(tenantId, acceptanceType.getCodeRule(), CodeConstants.CodeRuleLevelCode.GLOBAL, CodeConstants.CodeRuleLevelCode.GLOBAL, null));
					}
					acceptanceAsset.setAssetClassId(acceptanceLine.getProductCategoryId());
					acceptanceAsset.setAssetsSetId(acceptanceLine.getAssetsSetId());
					//acceptanceAsset.setSupplierHeaderId(); 供应商模块和合同模块不存在，该默认复制先忽略
					acceptanceAsset.setAssetStatusId(acceptanceType.getCompleteAssetStatusId());
					acceptanceAsset.setOriginalCost(acceptanceLine.getUnitPrice());
					acceptanceAsset.setTransferFixedCode(acceptanceType.getTransferFixedCode());
					acceptanceAsset.setCompleteFlag(BaseConstants.Digital.ZERO);

					acceptanceAssetList.add(acceptanceAsset);
				}
			}
			acceptanceAssetRepository.batchInsertSelective(acceptanceAssetList);
		}
		//修改状态为待完善资产
		acceptanceHeader.setAcceptanceStatusCode(ArcvConstants.acceptanceStatus.SUPPLEMENT);
		acceptanceHeader.setCompleteDate(new Date());
		acceptanceHeaderRepository.updateOptional(acceptanceHeader,
				AcceptanceHeader.FIELD_ACCEPTANCE_STATUS_CODE,
				AcceptanceHeader.FIELD_COMPLETE_DATE
		);

		return acceptanceHeader;

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void completeAsset(Long tenantId, Long acceptanceHeaderId) {
		AcceptanceHeader acceptanceHeader = acceptanceHeaderRepository.selectByCondition(Condition.builder(AcceptanceHeader.class)
				.andWhere(
						org.hzero.mybatis.util.Sqls.custom()
								.andEqualTo(AcceptanceHeader.FIELD_TENANT_ID, tenantId)
								.andEqualTo(AcceptanceHeader.FIELD_ACCEPTANCE_HEADER_ID, acceptanceHeaderId)
				).build()).get(0);
		if (acceptanceHeader == null) {
			throw new CommonException(BaseConstants.ErrorCode.DATA_NOT_EXISTS);
		} else if (!acceptanceHeader.getAcceptanceStatusCode().equals(ArcvConstants.acceptanceStatus.SUPPLEMENT)) {
			throw new CommonException(BaseConstants.ErrorCode.DATA_INVALID);
		}
		//查询出该资产明细行
		List<Long> acceptanceHeaderIds = new ArrayList<>();
		acceptanceHeaderIds.add(acceptanceHeaderId);
		List<AcceptanceAsset> acceptanceAssetList = acceptanceAssetMapper.listacceptanceAssetByAcceptanceHeaderIds(
				tenantId,
				acceptanceHeaderIds);

		//校验资产明细数据是否补全
		acceptanceAssetService.validateAcceptanceAssetCompletion(acceptanceAssetList);
		for (AcceptanceAsset acceptanceAsset : acceptanceAssetList) {
			Asset asset = new Asset();
			//创建资产数据
			asset.setTenantId(acceptanceAsset.getTenantId());
			asset.setAssetNum(acceptanceAsset.getAssetNum());
			asset.setAssetClassId(acceptanceAsset.getAssetClassId());
			asset.setAssetLocationId(acceptanceAsset.getAssetLocationId());
			asset.setAssetsSetId(acceptanceAsset.getAssetsSetId());
			asset.setAssetStatusId(acceptanceAsset.getAssetStatusId());
			asset.setBrand(acceptanceAsset.getBrand());
			asset.setCostCenterId(acceptanceAsset.getCostCenterId());
			asset.setModel(acceptanceAsset.getModel());
			asset.setOriginalCost(acceptanceAsset.getOriginalCost());
			asset.setOwningOrgId(acceptanceAsset.getOwningOrgId());
			asset.setOwningPersonId(acceptanceAsset.getOwningPersonId());
			asset.setParentAssetId(acceptanceAsset.getParentAssetId());
			asset.setSupplierOrgId(acceptanceAsset.getSupplierHeaderId());
			asset.setUserPersonId(acceptanceAsset.getUserPersonId());
			asset.setUsingOrgId(acceptanceAsset.getUsingOrgId());
			//产品名称=资产组名称
			String productName = assetsSetRepository.selectByPrimaryKey(acceptanceAsset.getAssetsSetId()).getAssetsSetName();
			asset.setAssetName(productName);
			//资产标签/铭牌规则="=资产编号"
			asset.setNameRuleCode(Constants.NameplateRule.ASSET_NUM);
			asset.setName(acceptanceAsset.getAssetNum());
			asset.setAssetDesc(new StringBuilder()
					.append(productName)
					.append(BaseConstants.Symbol.POINT)
					.append(acceptanceAsset.getBrand())
					.append(BaseConstants.Symbol.POINT)
					.append(acceptanceAsset.getModel())
					.toString());
			//地图来源，先随便给个默认值 none
			asset.setMapSource("None");
			asset.setAssetSourceTypeCode(Constants.AssetSource.PURCHASE);
			//插入资产
			assetService.insertAsset(tenantId, asset);
		}
		//修改状态为已完成
		acceptanceHeader.setAcceptanceStatusCode(ArcvConstants.acceptanceStatus.COMPLETED);
		acceptanceHeaderRepository.updateOptional(acceptanceHeader, AcceptanceHeader.FIELD_ACCEPTANCE_STATUS_CODE);

		// 修改交付清单
		List<AcceptanceLine> acceptanceLineList = acceptanceLineRepository.selectByCondition(Condition.builder(AcceptanceLine.class)
				.andWhere(
						org.hzero.mybatis.util.Sqls.custom()
								.andEqualTo(AcceptanceLine.FIELD_TENANT_ID, tenantId)
								.andEqualTo(AcceptanceLine.FIELD_ACCEPTANCE_HEADER_ID, acceptanceHeaderId)
				).build());
		List<DeliveryList> deliveryListList = new ArrayList<>();
		for (AcceptanceLine acceptanceLine : acceptanceLineList) {
			DeliveryList deliveryList = deliveryListRepository.selectByPrimaryKey(acceptanceLine.getDeliveryListId());
			if (deliveryList != null) {
				// 已交付数量
				deliveryList.setDeliveredQuantity(acceptanceLine.getDeliveryQuantity());
				// 交付完成日期
				deliveryList.setDeliveryCompleteDate(new Date());
				// 是否交付完成
				deliveryList.setDeliveryCompleteFlag(BaseConstants.Digital.ONE);

				deliveryListList.add(deliveryList);
			}
		}
		deliveryListRepository.batchUpdateByPrimaryKeySelective(deliveryListList);
	}
}
