package org.hzero.halm.atn.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.atn.domain.entity.HandoverOrderDetail;

/**
 * 资产移交归还明细资源库
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:11
 */
public interface HandoverOrderDetailRepository extends BaseRepository<HandoverOrderDetail> {
    /**
     * 保存移交归还明细的数据
     * @param detail detail
     * @param tenantId 租户
     * @return 移交归还数据
     */
    HandoverOrderDetail saveDetail(HandoverOrderDetail detail,Long tenantId);
}
