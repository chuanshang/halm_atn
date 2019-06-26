package org.hzero.halm.afm.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.afm.domain.entity.AttributeLine;
import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.halm.afm.domain.vo.AttributeLineDetailExportVO;

import java.util.List;

/**
 * 属性组行信息Mapper
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:23
 */
public interface AttributeLineMapper extends BaseMapper<AttributeLine> {

    List<AttributeLineDetailExportVO> exportAttributeLineBySetId(@Param("attributeSetIdList") List<Long> attributeSetIdList);

    List<AttributeLine> selectBySetId(@Param("attrSetId") Long attrSetId);

}
