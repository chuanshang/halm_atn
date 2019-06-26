package org.hzero.halm.atn.app.service;

import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;
import org.hzero.halm.atn.domain.entity.ScrapOrderLine;

import java.util.List;

/**
 * 资产报废单头应用服务
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:50
 */
public interface ScrapOrderHeaderService {

    /**
     * 新建资产报废行数据
     *
     * @param tenantId            租户ID
     * @param scrapOrderHeader 创建数据
     * @return 创建结果
     */
    ScrapOrderHeader createScrapOrder(Long tenantId, ScrapOrderHeader scrapOrderHeader);

    /**
     * 编辑资产报废头行数据
     *
     * @param tenantId            租户ID
     * @param scrapOrderHeader 更新数据
     * @return 更新结果
     */
    ScrapOrderHeader updateScrapOrder(Long tenantId, ScrapOrderHeader scrapOrderHeader);

    /**
     * 校验行必输字段，放回信息翻译成可阅读的信息
     * @param scrapOrderLines 行信息
     */
    void validateNotEmpty(List<ScrapOrderLine> scrapOrderLines);

    /**
     * 资产报废提交审批
     * @param tenantId 租户Id
     * @param scrapOrderHeader 资产报废头
     * @return 资产报废数据
     */
    ScrapOrderHeader submitApprovalRequest(Long tenantId, ScrapOrderHeader scrapOrderHeader);

}
