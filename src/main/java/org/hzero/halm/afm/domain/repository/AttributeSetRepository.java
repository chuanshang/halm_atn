package org.hzero.halm.afm.domain.repository;

import org.hzero.halm.afm.api.dto.AttributeSetDTO;
import org.hzero.halm.afm.domain.vo.AttributeSetDetailExportVO;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.afm.domain.entity.AttributeSet;

import java.util.List;

/**
 * 属性组头信息资源库
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:24
 */
public interface AttributeSetRepository extends BaseRepository<AttributeSet> {
    /**
     * 查询可导出的送货单头
     * @param attributeSet 查询条件
     * @return List<AttributeSetDetailExportVO>
     */
    List<AttributeSetDetailExportVO> exportAttributeSetByIds(AttributeSetDTO attributeSet);

}
