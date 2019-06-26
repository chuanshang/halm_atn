package org.hzero.halm.afm.infra.repository.impl;

import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.algorithm.tree.TreeBuilder;
import org.hzero.halm.afm.api.dto.ProductCategoriesDTO;
import org.hzero.halm.afm.domain.entity.ProductCategories;
import org.hzero.halm.afm.domain.repository.ProductCategoriesRepository;
import org.hzero.halm.afm.infra.mapper.ProductCategoriesMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 产品类别/资产分类 资源库实现
 *
 * @author like.zhang@hand-china.com 2019-01-11 10:42:38
 */
@Component
public class ProductCategoriesRepositoryImpl extends BaseRepositoryImpl<ProductCategories> implements ProductCategoriesRepository {

    @Autowired
    private ProductCategoriesMapper productCategoriesMapper;
    @Autowired
    private LovValueHandle lovValueHandle;

    @Override
    @SuppressWarnings("unchecked")
    public List<ProductCategoriesDTO> treeProductCategories(ProductCategories productCategories, Long tenantId) {

        productCategories.setTenantId(tenantId);
        List<ProductCategoriesDTO> result = productCategoriesMapper.selectProductCategories(productCategories);
        // 值集翻译
        result = (List<ProductCategoriesDTO>) lovValueHandle.process(null, result);

        result = TreeBuilder.buildTree(result, ProductCategories.ROOT_KEY, ProductCategoriesDTO::getProductCategoryId,
                           category -> category.getParentCategoryId() != null ? category.getParentCategoryId() : ProductCategories.ROOT_KEY);

        Collections.sort(result);
        return result;
    }
}
