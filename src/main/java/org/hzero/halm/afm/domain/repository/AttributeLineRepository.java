package org.hzero.halm.afm.domain.repository;

import org.hzero.halm.afm.domain.vo.AttributeLineDetailExportVO;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.afm.domain.entity.AttributeLine;

import java.util.List;

/**
 * 属性组行信息资源库
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:23
 */
public interface AttributeLineRepository extends BaseRepository<AttributeLine> {

    List<AttributeLineDetailExportVO> exportAttributeLineBySetId(String attributeSetIds);
}
