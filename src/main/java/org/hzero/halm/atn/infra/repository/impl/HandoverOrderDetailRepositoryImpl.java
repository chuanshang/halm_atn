package org.hzero.halm.atn.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.atn.domain.entity.HandoverOrderDetail;
import org.hzero.halm.atn.domain.repository.HandoverOrderDetailRepository;
import org.springframework.stereotype.Component;

/**
 * 资产移交归还明细 资源库实现
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:11
 */
@Component
public class HandoverOrderDetailRepositoryImpl extends BaseRepositoryImpl<HandoverOrderDetail> implements HandoverOrderDetailRepository {


    @Override
    public HandoverOrderDetail saveDetail(HandoverOrderDetail detail, Long tenantId) {
        if(detail.getHandoverDetailId()==null){
            detail.setTenantId(tenantId);
            this.insert(detail);
        }else {
            this.updateOptional(detail,
                    HandoverOrderDetail.FIELD_TARGET_OWNING_PERSON_ID,
                    HandoverOrderDetail.FIELD_TARGET_USING_PERSON_ID,
                    HandoverOrderDetail.FIELD_DESCRIPTION);
        }
        return detail;
    }
}
