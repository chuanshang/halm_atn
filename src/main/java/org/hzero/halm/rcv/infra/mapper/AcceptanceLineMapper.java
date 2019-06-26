package org.hzero.halm.rcv.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.rcv.domain.entity.AcceptanceLine;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 验收单行Mapper
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
public interface AcceptanceLineMapper extends BaseMapper<AcceptanceLine> {

	/**
	 * 通过查询条件查询验收单行信息
	 *
	 * @param tenantId            租户Id
	 * @param acceptanceHeaderIds 验收单头id数组
	 * @return 查询结果
	 */
	List<AcceptanceLine> listAcceptanceLineByHeaderIds(@Param("tenantId") Long tenantId, @Param("acceptanceHeaderIds") List<Long> acceptanceHeaderIds);
}
