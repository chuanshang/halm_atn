package org.hzero.halm.fam.infra.mapper;

import org.hzero.halm.fam.api.dto.AssetCatalogDTO;
import org.hzero.halm.fam.domain.entity.AssetCatalog;

import java.util.List;

import io.choerodon.mybatis.common.BaseMapper;

/**
 * 资产目录Mapper
 *
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
public interface AssetCatalogMapper extends BaseMapper<AssetCatalog> {

    /**
     * 列表页查询
     * @param assetCatalog 查询参数
     * @return 返回值
     */
    List<AssetCatalogDTO> selectAssetCatalogList(AssetCatalog assetCatalog);

    /**
     * 查询所有下级
     * @param assetCatalog 查询开始层级
     * @return 返回值
     */
    List<AssetCatalog> selectLowerLevelAssetCatalog(AssetCatalog assetCatalog);

    /**
     * 查询当前节点的所有下级节点
     *
     * @param assetCatalog 当前节点
     * @return 返回值下级节点集合
     */
    List<AssetCatalogDTO> selectChildList(AssetCatalog assetCatalog);
}
