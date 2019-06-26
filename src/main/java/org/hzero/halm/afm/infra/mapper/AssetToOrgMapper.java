package org.hzero.halm.afm.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hzero.halm.afm.domain.entity.AssetToOrg;
import org.hzero.halm.afm.domain.vo.AssetToOrgDetailExportVO;

import java.util.List;

/**
 * 资产专业-组织分配(行)Mapper
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:39
 */
public interface AssetToOrgMapper extends BaseMapper<AssetToOrg> {

    List<AssetToOrg> selectBySpecialtyId(@Param(value = "assetSpecialtyId") Long assetSpecialtyId);

    List<AssetToOrgDetailExportVO> exportAssetToOrgBySpecialtyId(@Param("assetSpecialtyIdList") List<Long> assetSpecialtyIdList);

}
