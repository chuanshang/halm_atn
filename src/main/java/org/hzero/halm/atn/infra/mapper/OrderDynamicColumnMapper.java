package org.hzero.halm.atn.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hzero.halm.atn.api.dto.EmployeeDTO;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;

/**
 * 资产事务处理单据动态字段Mapper
 *
 * @author like.zhang@hand-china.com 2019-03-26 11:49:44
 */
public interface OrderDynamicColumnMapper extends BaseMapper<OrderDynamicColumn> {

    /**
     * 查询当前用户关联的用户
     *
     * @param tenantId 租户ID
     * @param userId   用户ID
     * @return 员工信息
     */
    EmployeeDTO selectEmployeeByUserId(@Param("tenantId") Long tenantId, @Param("userId") Long userId);
}
