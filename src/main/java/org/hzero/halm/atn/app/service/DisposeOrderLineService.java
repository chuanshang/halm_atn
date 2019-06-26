package org.hzero.halm.atn.app.service;

import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.atn.domain.entity.DisposeOrderHeader;
import org.hzero.halm.atn.domain.entity.DisposeOrderLine;

import java.util.List;

/**
 * 资产处置单行应用服务
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 15:48:13
 */
public interface DisposeOrderLineService {

    /**
     * 新增/修改资产处置单行
     *
     * @param tenantId           租户ID
     * @param disposeOrderHeader   资产处置单行头
     * @param transactionTypes   事务管理类型
     * @param disposeOrderLines 操作的行数据
     * @return List<DisposeOrderLine> 操作结果
     */
    List<DisposeOrderLine> saveDisposeOrderLines(Long tenantId, DisposeOrderHeader disposeOrderHeader, TransactionTypes transactionTypes, List<DisposeOrderLine> disposeOrderLines);

    /**
     * 删除行
     * @param disposeOrderLine 删除的行信息
     */
    void removeLine(DisposeOrderLine disposeOrderLine);

    /**
     * 处置确认
     * @param tenantId 租户id
     * @param disposeOrderLine 被处置的行信息
     * @return 返回值
     */
    DisposeOrderLine processConfirm(Long tenantId,DisposeOrderLine disposeOrderLine);

}
