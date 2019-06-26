package org.hzero.halm.atn.domain.repository;

import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.atn.domain.entity.ScrapOrderLine;

import java.util.List;

/**
 * 资产报废单行资源库
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:10
 */
public interface ScrapOrderLineRepository extends BaseRepository<ScrapOrderLine> {

    /**
     * 条件查询资产报废单行列表
     *
     * @param scrapOrderHeader 查询条件
     * @return 查询结果
     */
    List<ScrapOrderLine> listScrapOrderLine(ScrapOrderHeader scrapOrderHeader);

    /**
     * 联表查询资产报废单行（根据行ID）
     *
     * @param scrapOrderLineId 行ID
     * @return 调拨转移单行
     */
    ScrapOrderLine selectScrapLineById(Long scrapOrderLineId);

    /**
     * 行插入
     *
     * @param tenantId 租户ID
     * @param scrapHeaderId 头ID
     * @param scrapOrderLine 插入行信息
     */
    void insertScrapOrderLine(Long tenantId, Long scrapHeaderId, ScrapOrderLine scrapOrderLine);

    /**
     * 入口处理细页
     * @param scrapOrderHeader 查询条件
     * @return  返回结果
     */
    List<ScrapOrderLine> entryProcessDetail(ScrapOrderHeader scrapOrderHeader);

}
