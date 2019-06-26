package org.hzero.halm.rcv.app.service;

import org.hzero.halm.rcv.domain.entity.AcceptanceLine;

import java.util.List;

/**
 * 验收单行应用服务
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
public interface AcceptanceLineService {
	/**
	 * 验收单行插入
	 *
	 * @param tenantId           租户ID
	 * @param acceptanceLineList 验收单行数据
	 * @return 验收单行数据
	 */
	List<AcceptanceLine> insertAcceptanceLine(Long tenantId, List<AcceptanceLine> acceptanceLineList);

	/**
	 * 验收单行编辑
	 *
	 * @param tenantId       租户ID
	 * @param acceptanceLine 验收单行数据
	 * @return 验收单行数据
	 */
	List<AcceptanceLine> updateAcceptanceLine(Long tenantId, List<AcceptanceLine> acceptanceLine);

	/**
	 * 验收单行数据校验
	 *
	 * @param acceptanceLine 验收单行数据
	 */
	void validAcceptanceLineFields(AcceptanceLine acceptanceLine);
}
