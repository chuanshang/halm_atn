package org.hzero.halm.atn.app.service;

import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;
import org.hzero.halm.atn.domain.entity.ChangeOrderLine;

import java.util.List;

/**
 * 资产信息变更单行应用服务
 *
 * @author like.zhang@hand-china.com 2019-03-26 11:49:44
 */
public interface ChangeOrderLineService {

    /**
     * 执行处理
     *
     * @param tenantId        租户ID
     * @param changeOrderLine 执行处理的数据
     * @return 处理结果
     */
    ChangeOrderLine executeProcess(Long tenantId, ChangeOrderLine changeOrderLine);

    /**
     * 新增/修改资产信息变更行数据
     *
     * @param tenantId          租户ID
     * @param changeOrderHeader 头
     * @param transactionTypes  资产事务处理类型
     * @param changeOrderLines  操作数据
     * @return 操作结果
     */
    List<ChangeOrderLine> saveChangeOrderLine(Long tenantId, ChangeOrderHeader changeOrderHeader, TransactionTypes transactionTypes, List<ChangeOrderLine> changeOrderLines);

    /**
     * 删除行，同时删除行下面的明细
     *
     * @param tenantId        租户ID
     * @param changeOrderLine 删除的行信息
     */
    void removeLine(Long tenantId, ChangeOrderLine changeOrderLine);
}
