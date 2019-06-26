package org.hzero.halm.afm.infra.repository.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.halm.afm.api.dto.AttributeSetDTO;
import org.hzero.halm.afm.domain.vo.AttributeSetDetailExportVO;
import org.hzero.halm.afm.infra.mapper.AttributeSetMapper;
import org.hzero.halm.afm.infra.utils.IdsExportConvert;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.afm.domain.entity.AttributeSet;
import org.hzero.halm.afm.domain.repository.AttributeSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性组头信息 资源库实现
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:24
 */
@Component
public class AttributeSetRepositoryImpl extends BaseRepositoryImpl<AttributeSet> implements AttributeSetRepository {


    @Autowired
    private AttributeSetMapper attributeSetMapper;

    @Override
    @ProcessLovValue
    public List<AttributeSetDetailExportVO> exportAttributeSetByIds(AttributeSetDTO attributeSet) {
        attributeSet.setAttributeSetIdList(IdsExportConvert.stringToList(attributeSet.getAttributeSetIds()));
        List<AttributeSetDetailExportVO> AttributeSetDetailExportList = attributeSetMapper.exportAttributeSetByIds(attributeSet);
        if (CollectionUtils.isEmpty(AttributeSetDetailExportList)){
            AttributeSetDetailExportList = new ArrayList<>();
        }
        return AttributeSetDetailExportList;
    }

}
