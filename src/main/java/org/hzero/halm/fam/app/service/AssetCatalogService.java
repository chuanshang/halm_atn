package org.hzero.halm.fam.app.service;

import org.hzero.halm.fam.api.dto.AssetCatalogDTO;
import org.hzero.halm.fam.domain.entity.AssetCatalog;

import java.util.List;

/**
 * 资产目录应用服务
 *
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
public interface AssetCatalogService {

    /**
     * 查询树结构列表
     * @param assetCatalog 查询参数
     * @return 返回值
     */
    List<AssetCatalogDTO> assetCatalogTreeList(AssetCatalog assetCatalog);

    /**
     * 新建/编辑接口
     * @param tenantId 租户id
     * @param assetCatalogList 新建/编辑数据
     * @return 返回值
     */
    List<AssetCatalog> assetCatalogCreateAndEdit(Long tenantId,List<AssetCatalog> assetCatalogList);

    /**
     * 删除资产目录
     * @param tenantId 租户id
     * @param assetCatalogList 新建/编辑数据
     * @return 返回值
     */
    void assetCatalogRemove(Long tenantId,List<AssetCatalog> assetCatalogList);

    /**
     * 资产目录启用/禁用标记
     * @param tenantId 租户ID
     * @param assetCatalogId 资产目录id
     * @param enabledFlag 启用/禁用标记
     * @return 返回值
     */
    List<AssetCatalog> changeAssetCatalogEnabledFlag(Long tenantId,Long assetCatalogId,Integer enabledFlag);
}
