package org.hzero.halm.atn.app.service;

import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;
import org.hzero.halm.atn.domain.entity.ScrapOrderLine;

import java.util.List;

/**
 * 资产报废单行应用服务
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:10
 */
public interface ScrapOrderLineService {
    /**
     * 资产报废确认
     *
     * @param tenantId          租户ID
     * @param scrapOrderLine 操作数据
     * @return 确认结果
     */
    ScrapOrderLine scrapConfirm(Long tenantId, ScrapOrderLine scrapOrderLine);

    /**
     * 新增/修改资产报废单行数据
     *
     * @param tenantId           租户ID
     * @param transferHeaderId   资产报废单头ID
     * @param transactionTypes   事务管理类型
     * @param scrapOrderLines 操作的行数据
     * @return 操作结果
     */
    List<ScrapOrderLine> saveScrapOrderLines(Long tenantId, Long transferHeaderId, TransactionTypes transactionTypes, List<ScrapOrderLine> scrapOrderLines);

    /**
     * 删除行
     *
     * @param tenantId        租户ID
     * @param scrapOrderLine 删除的行信息
     */
    void removeLine(Long tenantId, ScrapOrderLine scrapOrderLine);
}
