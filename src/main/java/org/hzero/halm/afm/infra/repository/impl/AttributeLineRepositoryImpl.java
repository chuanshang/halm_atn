package org.hzero.halm.afm.infra.repository.impl;

import org.hzero.halm.afm.domain.vo.AttributeLineDetailExportVO;
import org.hzero.halm.afm.infra.mapper.AttributeLineMapper;
import org.hzero.halm.afm.infra.utils.IdsExportConvert;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.afm.domain.entity.AttributeLine;
import org.hzero.halm.afm.domain.repository.AttributeLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 属性组行信息 资源库实现
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:23
 */
@Component
public class AttributeLineRepositoryImpl extends BaseRepositoryImpl<AttributeLine> implements AttributeLineRepository {

    @Autowired
    AttributeLineMapper attributeLineMapper;

    @Override
    public List<AttributeLineDetailExportVO> exportAttributeLineBySetId(String attributeSetIds) {
        List<AttributeLineDetailExportVO> attributeLineDetailExportList = attributeLineMapper.exportAttributeLineBySetId(IdsExportConvert.stringToList(attributeSetIds));
        return attributeLineDetailExportList;
    }

}
