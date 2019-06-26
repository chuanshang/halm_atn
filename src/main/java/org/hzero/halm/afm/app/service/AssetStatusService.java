package org.hzero.halm.afm.app.service;

import org.hzero.halm.afm.domain.entity.AssetStatus;

import java.util.List;

/**
 * 资产状态应用服务
 *
 * @author zhisheng.zhang@hand-china.com 2019-02-20 15:56:29
 */
public interface AssetStatusService {
	/**
	 * 批量修改资产状态
	 * @param tenantId 租户ID
	 * @param assetStatusList 资产状态数据集合
	 * @return
	 */
	List<AssetStatus> assetStatusListUpdate(Long tenantId, List<AssetStatus> assetStatusList);

	/**
	 * 租户内唯一性校验
	 * @param assetStatus 资产状态数据
	 */
	void validUniqueFields(AssetStatus assetStatus);
}
