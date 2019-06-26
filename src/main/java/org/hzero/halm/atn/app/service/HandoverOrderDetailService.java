package org.hzero.halm.atn.app.service;

import org.hzero.halm.atn.domain.entity.HandoverOrderDetail;
import org.hzero.halm.atn.domain.entity.HandoverOrderLine;
import org.hzero.halm.atn.infra.constant.AatnHandoverFields;

import java.util.List;

/**
 * 资产移交归还明细应用服务
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:11
 */
public interface HandoverOrderDetailService {

    /**
     * 生成资产移交归还行的说明字段数据。
     * @param orderDetail 明细
     * @param handoverFields 枚举集合
     * @return 生成的说明数据。
     */
    String generateDes(HandoverOrderDetail orderDetail, AatnHandoverFields[] handoverFields);

    /**
     * 根据行查询明细数据。
     * @param line 行数据
     * @return 明细集合
     */
    List<HandoverOrderDetail> selectDetailList(HandoverOrderLine line);
}
