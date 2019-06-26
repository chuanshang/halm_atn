package org.hzero.halm.fam.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.fam.api.dto.FixedAssetsDTO;
import org.hzero.halm.fam.domain.entity.FixedAssets;
import org.hzero.halm.fam.domain.entity.FixedAssetsChanges;

import java.util.List;

/**
 * 固定资产应用服务
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:22:24
 */
public interface FixedAssetsService {

    /**
     * 查询固定资产分页列表
     *
     * @param tenantId 租户ID
     * @param fixedAssets 查询条件
     * @param pageRequest 分页信息
     * @return Page<FixedAssets>
     */
    Page<FixedAssetsDTO> pageFixedAssets(Long tenantId, FixedAssets fixedAssets, PageRequest pageRequest);

    /**
     * 查询固定资产详细信息
     *
     * @param tenantId 租户ID
     * @param fixedAssetId 固定资产ID
     * @return FixedAssets
     */
    FixedAssetsDTO getFixedAssets(Long tenantId, Long fixedAssetId);

    /**
     * 保存固定资产详细信息
     *
     * @param tenantId 租户ID
     * @param fixedAssets 固定资产
     * @return FixedAssets
     */
    FixedAssets createAndUpdateFixedAssets(Long tenantId, FixedAssets fixedAssets);

    /**
     * 批量删除固定资产
     *
     * @param tenantId 租户ID
     * @param fixedAssetsList 固定资产list
     * @return FixedAssets
     */
    void removeFixedAssets(Long tenantId,List<FixedAssets> fixedAssetsList);

    /**
     * 查询价值变动page
     *
     * @param tenantId 租户ID
     * @param fixedAssetId 固定资产ID
     * @return FixedAssets
     */
    Page<FixedAssetsChanges> pageFixedAssetsChanges(Long tenantId, Long fixedAssetId,PageRequest pageRequest);

    /**
     * 查询价值变动
     *
     * @param tenantId 租户ID
     * @param fixedAssetId 固定资产ID
     * @return FixedAssets
     */
    List<FixedAssetsChanges> listFixedAssetsChanges(Long tenantId, Long fixedAssetId);

    /**
     * 批量删除价值变动
     *
     * @param tenantId 租户ID
     * @param fixedAssetsChangesList 价值变动list
     * @return FixedAssets
     */
    void removeFixedAssetsChanges(Long tenantId,List<FixedAssetsChanges> fixedAssetsChangesList);

    /**
     * 导出事件类型数据
     *
     * @param fixedAssetsDto 查询条件
     * @return List<AttributeSetDetailExportVO>
     */
    List<FixedAssetsDTO> exportFixedAssets(FixedAssetsDTO fixedAssetsDto);

}
