package org.hzero.halm.rcv.app.service;

import org.hzero.halm.rcv.domain.entity.AcceptanceHeader;

/**
 * 验收单应用服务
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
public interface AcceptanceHeaderService {

	/**
	 * 插入验收单
	 *
	 * @param acceptanceHeader 验收单数据
	 * @return 验收单数据
	 */
	AcceptanceHeader insertAcceptanceHeader(AcceptanceHeader acceptanceHeader);

	/**
	 * 更新验收单数据
	 *
	 * @param acceptanceHeader 验收单数据
	 * @return 验收单数据
	 */
	AcceptanceHeader updateAcceptanceHeader(AcceptanceHeader acceptanceHeader);

	/**
	 * 租户内唯一性校验
	 *
	 * @param acceptanceHeader 输入参数
	 */
	void validUniqueAcceptanceHeaderFields(AcceptanceHeader acceptanceHeader);

	/**
	 * 提交审批
	 *
	 * @param tenantId           租户Id
	 * @param acceptanceHeaderId 验收单头Id
	 * @return 验收单头信息
	 */
	AcceptanceHeader submitApproval(Long tenantId, Long acceptanceHeaderId);

	/**
	 * 完成验收，生成资产明细行
	 *
	 * @param tenantId           租户ID
	 * @param acceptanceHeaderId 验收单头ID
	 * @return 验收单头数据
	 */
	AcceptanceHeader completeAcceptance(Long tenantId, Long acceptanceHeaderId);

	/**
	 * 完成资产，根据资产明细行生成资产
	 *
	 * @param tenantId           租户ID
	 * @param acceptanceHeaderId 验收单ID
	 */
	void completeAsset(Long tenantId, Long acceptanceHeaderId);
}
