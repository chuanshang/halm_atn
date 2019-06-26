package org.hzero.halm.rcv.infra.repository.impl;

import org.hzero.halm.rcv.infra.mapper.AcceptanceTypeMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.rcv.domain.entity.AcceptanceType;
import org.hzero.halm.rcv.domain.repository.AcceptanceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验收单类型 资源库实现
 *
 * @author zhiguang.guo@hand-china.com 2019-04-17 17:55:24
 */
@Component
public class AcceptanceTypeRepositoryImpl extends BaseRepositoryImpl<AcceptanceType> implements AcceptanceTypeRepository {

    @Autowired
    private AcceptanceTypeMapper acceptanceTypeMapper;

    @Override
    public AcceptanceType acceptanceTypeDetail(Long tenantId, Long acceptanceTypeId) {
        return acceptanceTypeMapper.acceptanceTypeDetail(tenantId, acceptanceTypeId);
    }
}
