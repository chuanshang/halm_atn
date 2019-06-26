package org.hzero.halm.afm.infra.mapper;

import org.hzero.halm.afm.api.dto.AttributeSetDTO;
import org.hzero.halm.afm.domain.entity.AttributeSet;
import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.halm.afm.domain.vo.AttributeSetDetailExportVO;

import java.util.List;

/**
 * 属性组头信息Mapper
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:24
 */
public interface AttributeSetMapper extends BaseMapper<AttributeSet> {

    /**
     * 查询可导出的属性组
     * @param attributeSet 查询条件
     * @return List<AttributeSetDetailExportVO>
     */
    List<AttributeSetDetailExportVO> exportAttributeSetByIds(AttributeSetDTO attributeSet);

}
