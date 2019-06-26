package org.hzero.halm.atn.app.service;

import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.atn.domain.entity.TransferOrderHeader;
import org.hzero.halm.atn.domain.entity.TransferOrderLine;

import java.util.List;

/**
 * 调拨转移单行应用服务
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:33
 */
public interface TransferOrderLineService {

    /**
     * 调入调出确认
     *
     * @param tenantId          租户ID
     * @param transferOrderLine 操作数据
     * @return 确认结果
     */
    TransferOrderLine transferConfirm(Long tenantId, TransferOrderLine transferOrderLine);

    /**
     * 新增/修改资产调拨单行数据
     *
     * @param tenantId           租户ID
     * @param transferHeaderId   资产调拨单头ID
     * @param transactionTypes   事务管理类型
     * @param transferOrderLines 操作的行数据
     * @return 操作结果
     */
    List<TransferOrderLine> saveTransferOrderLines(Long tenantId, Long transferHeaderId, TransactionTypes transactionTypes, List<TransferOrderLine> transferOrderLines);

    /**
     * 调教调拨单
     *
     * @param tenantId            租户ID
     * @param transferOrderHeader 提交内容
     * @return 提交结果
     */
    TransferOrderHeader submitTransferOrder(Long tenantId, TransferOrderHeader transferOrderHeader);
}
