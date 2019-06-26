package org.hzero.halm.atn.app.service;

import org.hzero.halm.atn.domain.entity.DisposeOrderHeader;

/**
 * 资产处置单头应用服务
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 11:05:34
 */
public interface DisposeOrderHeaderService {

    /**
     * 创建资产处置单
     * @param tenantId 租户ID
     * @param disposeOrderHeader 创建数据参数
     * @return DisposeOrderHeader 返回结果
     */
    DisposeOrderHeader disposeOrderHeaderCreate(Long tenantId, DisposeOrderHeader disposeOrderHeader);
    /**
     * 修改资产处置单
     * @param tenantId 租户ID
     * @param disposeOrderHeader 修改数据参数
     * @return DisposeOrderHeader 返回结果
     */
    DisposeOrderHeader disposeOrderHeaderUpdate(Long tenantId, DisposeOrderHeader disposeOrderHeader);
    /**
     * 状态提交
     * @param tenantId 租户ID
     * @param disposeOrderHeader 提交
     * @return 返回值
     */
    DisposeOrderHeader commitStatus(Long tenantId, DisposeOrderHeader disposeOrderHeader);
}
