package org.hzero.halm.fam.infra.repository.impl;

import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.algorithm.tree.TreeBuilder;
import org.hzero.halm.fam.api.dto.AssetCatalogDTO;
import org.hzero.halm.fam.infra.mapper.AssetCatalogMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.fam.domain.entity.AssetCatalog;
import org.hzero.halm.fam.domain.repository.AssetCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 资产目录 资源库实现
 *
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
@Component
public class AssetCatalogRepositoryImpl extends BaseRepositoryImpl<AssetCatalog> implements AssetCatalogRepository {

    @Autowired
    private AssetCatalogMapper assetCatalogMapper;
    @Autowired
    private LovValueHandle lovValueHandle;


    @Override
    public List<AssetCatalogDTO> assetCatalogTreeList(AssetCatalog assetCatalog) {
        List<AssetCatalogDTO> assetCatalogsList = assetCatalogMapper.selectAssetCatalogList(assetCatalog);
        assetCatalogsList = (List<AssetCatalogDTO>) lovValueHandle.process(null, assetCatalogsList);
        List<AssetCatalogDTO> list = assetCatalogsList;
        List<AssetCatalogDTO> assetCatalogTree = TreeBuilder.buildTree(assetCatalogsList,
                AssetCatalog.ROOT_KEY,
                AssetCatalogDTO::getAssetCatalogId,
                assetCatalogDTO -> list.stream().filter(o -> o.getAssetCatalogId().equals(assetCatalogDTO.getParentCatalogId())).findAny().orElse(null) != null ? assetCatalogDTO.getParentCatalogId() : AssetCatalog.ROOT_KEY);
        Collections.sort(assetCatalogTree);
        return assetCatalogTree;
    }
}
