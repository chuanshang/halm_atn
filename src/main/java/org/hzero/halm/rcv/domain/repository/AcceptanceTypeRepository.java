package org.hzero.halm.rcv.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.rcv.domain.entity.AcceptanceType;

/**
 * 验收单类型资源库
 *
 * @author zhiguang.guo@hand-china.com 2019-04-17 17:55:24
 */
public interface AcceptanceTypeRepository extends BaseRepository<AcceptanceType> {

    /**
     * 明细查询
     * @param tenantId 租户id
     * @param acceptanceTypeId 明细id
     * @return 返回值
     */
    AcceptanceType acceptanceTypeDetail(Long tenantId,Long acceptanceTypeId);
}
