package org.hzero.halm.atn.app.service;

import org.hzero.halm.atn.domain.entity.TransferOrderHeader;

/**
 * 调拨转移单头应用服务
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:34
 */
public interface TransferOrderHeaderService {

    /**
     * 新建调拨转移头行数据
     *
     * @param tenantId            租户ID
     * @param transferOrderHeader 创建数据
     * @return 创建结果
     */
    TransferOrderHeader createTransferOrder(Long tenantId, TransferOrderHeader transferOrderHeader);

    /**
     * 编辑调拨转移头行数据
     *
     * @param tenantId            租户ID
     * @param transferOrderHeader 更新数据
     * @return 更新结果
     */
    TransferOrderHeader updateTransferOrder(Long tenantId, TransferOrderHeader transferOrderHeader);
}
