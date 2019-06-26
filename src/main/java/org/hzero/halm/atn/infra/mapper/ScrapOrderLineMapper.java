package org.hzero.halm.atn.infra.mapper;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;
import org.hzero.halm.atn.domain.entity.ScrapOrderLine;
import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资产报废单行Mapper
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:10
 */
public interface ScrapOrderLineMapper extends BaseMapper<ScrapOrderLine> {


    /**
     * 查询本资产信息变更单的最大行号
     *
     * @param scrapOrderHeaderId 头ID
     * @return 最大行号
     */
    Integer selectMaxLineNum(@Param("scrapOrderHeaderId") Long scrapOrderHeaderId);

    /**
     * 查询报废单行
     * @param tenantId 租户ID
     * @param scrapOrderHeaderId 报废单头ID
     * @return
     */
    List<ScrapOrderLine> selectOrderLines(@Param("tenantId") Long tenantId, @Param("scrapOrderHeaderId")Long scrapOrderHeaderId);

    /**
     *  入口&处理页面详情查询
     * @param scrapOrderHeader 查询条件
     * @return 返回值
     */
    List<ScrapOrderLine> entryProcess(ScrapOrderHeader scrapOrderHeader);
}
