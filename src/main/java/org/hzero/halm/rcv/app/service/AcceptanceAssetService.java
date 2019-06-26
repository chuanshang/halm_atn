package org.hzero.halm.rcv.app.service;

import org.hzero.halm.rcv.domain.entity.AcceptanceAsset;

import java.util.List;

/**
 * 验收单资产明细应用服务
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
public interface AcceptanceAssetService {
	/**
	 * 修改资产明细行
	 *
	 * @param tenantId        租户ID
	 * @param acceptanceAsset 资产明细行数据
	 * @return 				  资产明细行数据
	 */
	AcceptanceAsset updateAcceptanceAsset(Long tenantId, AcceptanceAsset acceptanceAsset);

	/**
	 * 校验资产明细行
	 * @param acceptanceAsset
	 */
	void validateUniqueAcceptanceAsset(AcceptanceAsset acceptanceAsset);

	/**
	 * 完成资产时 和 修改资产明细行 时,校验资产明细行是否补全
	 * @param acceptanceAssetList 资产明细行
	 */
	void validateAcceptanceAssetCompletion(List<AcceptanceAsset> acceptanceAssetList);
}
