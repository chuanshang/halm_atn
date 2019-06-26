package org.hzero.halm.afm.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.afm.domain.entity.AttributeLine;

/**
 * 属性组行信息应用服务
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:23
 */
public interface AttributeLineService {

    /**
     * 根据头ID查询行信息
     * @param pageRequest PageRequest
     * @param attributeSetId 属性组ID
     * @return
     */
    Page<AttributeLine> pageAndSortBySetId(PageRequest pageRequest, Long attributeSetId);
}
