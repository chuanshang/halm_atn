package org.hzero.halm.fam.domain.repository;

import org.hzero.halm.fam.api.dto.FixedAssetsDTO;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.fam.domain.entity.FixedAssets;

import java.util.List;

/**
 * 固定资产资源库
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:22:24
 */
public interface FixedAssetsRepository extends BaseRepository<FixedAssets> {

    /**
     * 查询固定资产列表
     *
     * @param tenantId 租户ID
     * @param fixedAssets 查询条件
     * @return List<FixedAssets>
     */
    List<FixedAssetsDTO> listFixedAssets(Long tenantId, FixedAssets fixedAssets);

    /**
     * 查询固定资产
     *
     * @param tenantId 租户ID
     * @param fixedAssetId 固定资产ID
     * @return FixedAssets
     */
    FixedAssetsDTO getFixedAssets(Long tenantId, Long fixedAssetId);

    /**
     * 查询固定资产导出数据
     *
     * @param fixedAssets 查询条件
     * @return List<FixedAssetsDTO>
     */
    List<FixedAssetsDTO> exportFixedAssets(FixedAssetsDTO fixedAssets);
}
