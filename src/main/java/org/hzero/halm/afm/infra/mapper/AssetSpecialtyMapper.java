package org.hzero.halm.afm.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hzero.halm.afm.domain.entity.AssetSpecialty;
import org.hzero.halm.afm.domain.vo.AssetSpecialtyDetailExportVO;

import java.util.List;

/**
 * 资产专业定义(头)Mapper
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:37
 */
public interface AssetSpecialtyMapper extends BaseMapper<AssetSpecialty> {

    List<AssetSpecialtyDetailExportVO> exportAttributeSetByIds(AssetSpecialty assetSpecialty);

    List<AssetSpecialty> selectListByCondition(@Param("tenantId") Long tenantId, @Param("condition") String condition);
}
