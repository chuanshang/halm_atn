package org.hzero.halm.atn.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;

import java.util.List;

/**
 * 资产报废单头资源库
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:50
 */
public interface ScrapOrderHeaderRepository extends BaseRepository<ScrapOrderHeader> {
    /**
     * 分页查询资产报废单
     *
     * @param pageRequest         分页参数
     * @param scrapOrderHeader    查询参数
     * @return 查询结果
     */
    Page<ScrapOrderHeader> pageScrapOrder(PageRequest pageRequest, ScrapOrderHeader scrapOrderHeader);

    /**
     *
     * @param tenantId 租户ID
     * @param scrapOrderHeaderId 资产报废头ID
     * @return 资产报废单数据
     */
    ScrapOrderHeader detailScrapOrder(Long tenantId,Long scrapOrderHeaderId);
    /**
     * 全文查询资产报废单列表
     * @param tenantId 租户ID
     * @param content 检索内容
     * @return 所有
     */
    Page<ScrapOrderHeader> listScrapOrderHeader(PageRequest pageRequest,Long tenantId, String content);
}
