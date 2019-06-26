package org.hzero.halm.rcv.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.rcv.domain.entity.AcceptanceType;

import java.util.List;

import io.choerodon.mybatis.common.BaseMapper;

/**
 * 验收单类型Mapper
 *
 * @author zhiguang.guo@hand-china.com 2019-04-17 17:55:24
 */
public interface AcceptanceTypeMapper extends BaseMapper<AcceptanceType> {

    /**
     * 列表页查询
     * @param acceptanceType 查询参数
     * @return 返回值
     */
    List<AcceptanceType> acceptanceTypeList(AcceptanceType acceptanceType);

    /**
     * 明细查询
     * @param tenantId 租户id
     * @param acceptanceTypeId 明细id
     * @return 返回值
     */
    AcceptanceType acceptanceTypeDetail(@Param("tenantId") Long tenantId, @Param("acceptanceTypeId") Long acceptanceTypeId);
}
