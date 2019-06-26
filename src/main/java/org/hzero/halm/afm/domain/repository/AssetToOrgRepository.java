package org.hzero.halm.afm.domain.repository;

import org.hzero.halm.afm.domain.entity.AssetToOrg;
import org.hzero.halm.afm.domain.vo.AssetToOrgDetailExportVO;
import org.hzero.mybatis.base.BaseRepository;

import java.util.List;

/**
 * 资产专业-组织分配(行)资源库
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:39
 */
public interface AssetToOrgRepository extends BaseRepository<AssetToOrg> {

    /**
     * 查询可导出的资产专业-组织分配行数据
     * @param assetSpecialtyIds 查询条件
     * @return List<AssetToOrgDetailExportVO>
     */
    List<AssetToOrgDetailExportVO> exportAssetToOrgBySpecialtyId(String assetSpecialtyIds);
}
