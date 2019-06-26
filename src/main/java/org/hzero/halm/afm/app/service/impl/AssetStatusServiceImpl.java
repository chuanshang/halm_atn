package org.hzero.halm.afm.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.hzero.halm.afm.app.service.AssetStatusService;
import org.hzero.halm.afm.domain.entity.AssetStatus;
import org.hzero.halm.afm.domain.repository.AssetStatusRepository;
import org.hzero.halm.afm.infra.constant.Constants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 资产状态应用服务默认实现
 *
 * @author zhisheng.zhang@hand-china.com 2019-02-20 15:56:29
 */
@Service
public class AssetStatusServiceImpl implements AssetStatusService {

	@Autowired
	private AssetStatusRepository assetStatusRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<AssetStatus> assetStatusListUpdate(Long tenantId, List<AssetStatus> assetStatusList) {
		List<AssetStatus> updateAssetStatusList = new ArrayList<>();
		for (AssetStatus assetStatus:assetStatusList) {
			assetStatus.setTenantId(tenantId);
			//校验
			validUniqueFields(assetStatus);
			updateAssetStatusList.add(assetStatus);
		}
		assetStatusRepository.batchUpdateByPrimaryKeySelective(updateAssetStatusList);
		return updateAssetStatusList;
	}

	@Override
	public void validUniqueFields(AssetStatus assetStatus) {
		// 条件语句组装
		Sqls listAssetStatusOfNameSql = Sqls.custom()
				.andEqualTo(AssetStatus.FIELD_TENANT_ID, assetStatus.getTenantId())
				.andEqualTo(AssetStatus.FIELD_SYS_STATUS_NAME, assetStatus.getSysStatusName());
		if(assetStatus.getAssetStatusId() != null){
			listAssetStatusOfNameSql=listAssetStatusOfNameSql.andNotEqualTo(AssetStatus.FIELD_ASSET_STATUS_ID,assetStatus.getAssetStatusId());
		}

		if (assetStatusRepository.selectByCondition(Condition.builder(AssetStatus.class)
				.andWhere(listAssetStatusOfNameSql)
				.build()).size() > 0) {
			throw new CommonException(Constants.AafmErrorCode.DUPLICATE_ASSET_STATUS_NAME);
		}
	}
}
