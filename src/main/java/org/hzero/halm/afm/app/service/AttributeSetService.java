package org.hzero.halm.afm.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.afm.api.dto.AttributeSetDTO;
import org.hzero.halm.afm.domain.entity.AttributeSet;
import org.hzero.halm.afm.domain.vo.AttributeSetDetailExportVO;

import java.util.List;

/**
 * 属性组头信息应用服务
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:24
 */
public interface AttributeSetService {

    /**
     * 条件查询订单变更配置
     * @param pageRequest pageRequest
     * @param organizationId 租户ID
     * @return
     */
    Page<AttributeSet> pageAttributeSet(PageRequest pageRequest, Long organizationId, AttributeSetDTO attributeSet);

    /**
     * 条件查询订单变更配置
     * @param pageRequest pageRequest
     * @param attributeSetId 属性组ID
     * @return
     */
    AttributeSet attributeSetDetail(PageRequest pageRequest,Long attributeSetId);

    /**
     * 批量新增或更新订单变更配置
     * @param organizationId 租户ID
     * @param attributeSets 属性组list
     */
    void batchCreateAndUpdate(Long organizationId, List<AttributeSet> attributeSets);

    /**
     * 头行导出属性组数据
     *
     * @param attributeSet 查询条件
     * @return List<AttributeSetDetailExportVO>
     */
    List<AttributeSetDetailExportVO> exportAttributeSetByIds(AttributeSetDTO attributeSet);


}
