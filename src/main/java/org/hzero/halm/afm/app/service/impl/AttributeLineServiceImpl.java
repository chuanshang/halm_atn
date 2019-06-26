package org.hzero.halm.afm.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.afm.app.service.AttributeLineService;
import org.hzero.halm.afm.domain.entity.AttributeLine;
import org.hzero.halm.afm.infra.mapper.AttributeLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 属性组行信息应用服务默认实现
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:23
 */
@Service
public class AttributeLineServiceImpl implements AttributeLineService {

    @Autowired
    AttributeLineMapper attributeLineMapper;

    @Override
    public Page<AttributeLine> pageAndSortBySetId(PageRequest pageRequest, Long attributeSetId) {
        return PageHelper.doPageAndSort(pageRequest, () -> {
            return attributeLineMapper.selectBySetId(attributeSetId);
        });
    }
}
