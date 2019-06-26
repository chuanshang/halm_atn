package org.hzero.halm.rcv.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.repository.AssetRepository;
import org.hzero.halm.rcv.app.service.AcceptanceAssetService;
import org.hzero.halm.rcv.domain.entity.AcceptanceAsset;
import org.hzero.halm.rcv.domain.repository.AcceptanceAssetRepository;
import org.hzero.halm.rcv.infra.constant.ArcvConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 验收单资产明细应用服务默认实现
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@Service
public class AcceptanceAssetServiceImpl implements AcceptanceAssetService {

	@Autowired
	private AcceptanceAssetRepository acceptanceAssetRepository;
	@Autowired
	private AssetRepository assetRepository;
	@Autowired
	LovValueHandle lovValueHandle;

	@Override
	public AcceptanceAsset updateAcceptanceAsset(Long tenantId, AcceptanceAsset acceptanceAsset) {
		acceptanceAsset.setTenantId(tenantId);
		validateUniqueAcceptanceAsset(acceptanceAsset);
		List<AcceptanceAsset> acceptanceAssetList = new ArrayList<>();
		acceptanceAssetList.add(acceptanceAsset);
		validateAcceptanceAssetCompletion(acceptanceAssetList);
		acceptanceAsset.setCompleteFlag(BaseConstants.Digital.ONE);
		acceptanceAssetRepository.updateByPrimaryKeySelective(acceptanceAsset);
		//值集翻译
		acceptanceAsset = (AcceptanceAsset) lovValueHandle.process(null, acceptanceAsset);
		return acceptanceAsset;
	}

	@Override
	public void validateUniqueAcceptanceAsset(AcceptanceAsset acceptanceAsset) {
		if (StringUtils.isEmpty(acceptanceAsset.getAssetNum())
		) {
			throw new CommonException(ArcvConstants.ArcvErrorCode.ARCV_ASSET_NUM_IS_NULL);
		}
		//和资产与资产明细租户内唯一性校验
		int countAsset = assetRepository.selectCountByCondition(Condition.builder(Asset.class)
				.andWhere(Sqls.custom()
						.andEqualTo(Asset.FIELD_TENANT_ID, acceptanceAsset.getTenantId())
						.andEqualTo(Asset.FIELD_ASSET_NUM, acceptanceAsset.getAssetNum())
				).build());
		if (countAsset > 0) {
			throw new CommonException(ArcvConstants.ArcvErrorCode.ARCV_DUPLICATE_ASSET_NUM_FROM_ASSET);
		}
		int countAcceptanceAsset = acceptanceAssetRepository.selectCountByCondition(Condition.builder(AcceptanceAsset.class)
				.andWhere(Sqls.custom()
						.andEqualTo(AcceptanceAsset.FIELD_TENANT_ID, acceptanceAsset.getTenantId())
						.andEqualTo(AcceptanceAsset.FIELD_ASSET_NUM, acceptanceAsset.getAssetNum())
						.andNotEqualTo(AcceptanceAsset.FIELD_ACCEPTANCE_ASSET_ID, acceptanceAsset.getAcceptanceAssetId())
				).build());
		if (countAcceptanceAsset > 0) {
			throw new CommonException(ArcvConstants.ArcvErrorCode.ARCV_DUPLICATE_ASSET_NUM_FROM_ACCEPTANCE_ASSET);
		}

	}

	@Override
	public void validateAcceptanceAssetCompletion(List<AcceptanceAsset> acceptanceAssetList) {
		for (AcceptanceAsset acceptanceAsset : acceptanceAssetList) {
			if (StringUtils.isEmpty(acceptanceAsset.getAssetNum()) ||
					acceptanceAsset.getAssetClassId() == null ||
					acceptanceAsset.getAssetsSetId() == null ||
					acceptanceAsset.getSupplierHeaderId() == null ||
					acceptanceAsset.getAssetStatusId() == null ||
					acceptanceAsset.getAssetLocationId() == null ||
					acceptanceAsset.getOwningOrgId() == null ||
					acceptanceAsset.getUsingOrgId() == null ||
					acceptanceAsset.getUserPersonId() == null ||
					acceptanceAsset.getOwningPersonId() == null ||
					acceptanceAsset.getCostCenterId() == null ||
					acceptanceAsset.getOriginalCost() == null
			) {
				throw new CommonException(ArcvConstants.ArcvErrorCode.ARCV_ASSET_DETAIL_DATA_NOT_COMPLETED);
			}
		}
	}
}
