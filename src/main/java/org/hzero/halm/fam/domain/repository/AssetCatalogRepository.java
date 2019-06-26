package org.hzero.halm.fam.domain.repository;

import org.hzero.halm.fam.api.dto.AssetCatalogDTO;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.fam.domain.entity.AssetCatalog;

import java.util.List;

/**
 * 资产目录资源库
 *
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
public interface AssetCatalogRepository extends BaseRepository<AssetCatalog> {

    /**
     * 查询树结构列表
     * @param assetCatalog 查询参数
     * @return 返回值
     */
    List<AssetCatalogDTO> assetCatalogTreeList(AssetCatalog assetCatalog);
}
