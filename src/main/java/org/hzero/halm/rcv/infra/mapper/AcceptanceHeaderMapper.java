package org.hzero.halm.rcv.infra.mapper;

import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.ibatis.annotations.Param;
import org.hzero.halm.rcv.domain.entity.AcceptanceHeader;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 验收单Mapper
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
public interface AcceptanceHeaderMapper extends BaseMapper<AcceptanceHeader> {
	/**
	 * 验收单列表查询
	 *
	 * @param acceptanceHeader 查询条件
	 * @return 返回结果
	 */
	List<AcceptanceHeader> listAcceptanceHeader(AcceptanceHeader acceptanceHeader);

	/**
	 * 验收单列表查询
	 *
	 * @param tenantId            租户Id
	 * @param acceptanceHeaderIds 验收单头id数组
	 * @return 返回结果
	 */
	List<AcceptanceHeader> listAcceptanceHeaderByIds(@Param("tenantId") Long tenantId, @Param("acceptanceHeaderIds") List<Long> acceptanceHeaderIds);

	/**
	 * 全文检索验收单列表
	 *
	 * @param tenantId  租户ID
	 * @param condition 查询条件
	 * @return 验收单列表
	 */
	List<AcceptanceHeader> listAcceptanceHeaderByCondition(@Param("tenantId") Long tenantId, @Param("condition") String condition);
}
