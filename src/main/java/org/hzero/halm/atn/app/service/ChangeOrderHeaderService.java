package org.hzero.halm.atn.app.service;

import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;

/**
 * 资产信息变更单头应用服务
 *
 * @author like.zhang@hand-china.com 2019-03-26 10:20:34
 */
public interface ChangeOrderHeaderService {

    /**
     * 编辑资产信息变更单行数据
     *
     * @param tenantId          租户ID
     * @param changeOrderHeader 更新数据
     * @return 更新结果
     */
    ChangeOrderHeader updateChangeOrder(Long tenantId, ChangeOrderHeader changeOrderHeader);

    /**
     * 新建资产信息变更单
     *
     * @param tenantId          租户ID
     * @param changeOrderHeader 创建数据
     * @return 创建结果
     */
    ChangeOrderHeader createChangeOrder(Long tenantId, ChangeOrderHeader changeOrderHeader);

    /**
     * 提交变更单
     *
     * @param tenantId          租户ID
     * @param changeOrderHeader 提交内容
     * @return 提交结果
     */
    ChangeOrderHeader submitChangeOrder(Long tenantId, ChangeOrderHeader changeOrderHeader);
}
