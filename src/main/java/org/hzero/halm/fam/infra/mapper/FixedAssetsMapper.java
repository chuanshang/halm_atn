package org.hzero.halm.fam.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.fam.api.dto.FixedAssetsDTO;
import org.hzero.halm.fam.domain.entity.FixedAssets;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 固定资产Mapper
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:22:24
 */
public interface FixedAssetsMapper extends BaseMapper<FixedAssets> {

    /**
     * 查询固定资产分页列表
     *
     * @param fixedAssets 查询条件
     * @return List<FixedAssets>
     */
    List<FixedAssetsDTO> listFixedAssets(FixedAssets fixedAssets);

    /**
     * 查询固定资产
     *
     * @param tenantId 查询条件
     * @param fixedAssetId 固定资产ID
     * @return FixedAssets
     */
    FixedAssetsDTO getFixedAssets(@Param("tenantId") Long tenantId,@Param("fixedAssetId") Long fixedAssetId);

    /**
     * 导出固定资产列表
     *
     * @param fixedAssets 查询条件
     * @return  List<FixedAssetsDTO>
     */
    List<FixedAssetsDTO> exportFixedAssets(FixedAssetsDTO fixedAssets);
}
