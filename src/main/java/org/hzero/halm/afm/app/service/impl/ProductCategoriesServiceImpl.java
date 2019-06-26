package org.hzero.halm.afm.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.api.dto.ProductCategoriesDTO;
import org.hzero.halm.afm.app.service.ProductCategoriesService;
import org.hzero.halm.afm.domain.entity.ProductCategories;
import org.hzero.halm.afm.domain.repository.ProductCategoriesRepository;
import org.hzero.halm.afm.infra.constant.Constants;
import org.hzero.halm.afm.infra.mapper.ProductCategoriesMapper;
import org.hzero.halm.afm.infra.utils.TreeDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 产品类别/资产分类应用服务默认实现
 *
 * @author like.zhang@hand-china.com 2019-01-11 10:42:38
 */
@Service
public class ProductCategoriesServiceImpl implements ProductCategoriesService {

    @Autowired
    private ProductCategoriesRepository productCategoriesRepository;
    @Autowired
    private ProductCategoriesMapper productCategoriesMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProductCategories> batchSaveProductCategory(List<ProductCategories> productCategories, Long tenantId) {
        if (CollectionUtils.isEmpty(productCategories)) {
            return productCategories;
        }

        // 将插入的数据的父节点levelPath从数据库中取出
        String parentIds = productCategories.stream().filter(category -> Objects.isNull(category.getProductCategoryId()))
                .map(category -> ObjectUtils.defaultIfNull(category.getParentCategoryId(), Constants.DEFAULT_PARENT_ID).toString())
                .collect(Collectors.joining(BaseConstants.Symbol.COMMA));
        Map<Long, ProductCategories> parentLevelPathMap = new HashMap<>();
        if (StringUtils.isNotEmpty(parentIds)){
            parentLevelPathMap = productCategoriesRepository.selectByIds(parentIds).stream()
                    .collect(Collectors.toMap(ProductCategories::getProductCategoryId, a -> a, (k1, k2) -> k1));
        }

        for (ProductCategories productCategory : productCategories) {
            // 新增
            if (Objects.isNull(productCategory.getProductCategoryId())) {
                // 唯一索引校验
                productCategory.validUniqueIndex(tenantId, productCategoriesRepository);
                // 校验开启必输的字段
                productCategory.validUniqueFields(tenantId, productCategoriesRepository);

                if (Objects.isNull(productCategory.getParentCategoryId())){
                    // 新建顶层
                    productCategory.setLevelPath(StringUtils.EMPTY);
                    productCategory.setLevelNumber(Constants.DEFAULT_LEVEL_NUMBER);
                } else {
                    ProductCategories parent = parentLevelPathMap.get(productCategory.getParentCategoryId());
                    productCategory.setLevelNumber(parent.getLevelNumber() + 1);
                    if (StringUtils.isEmpty(parent.getLevelPath())){
                        productCategory.setLevelPath(parent.getProductCategoryId().toString());
                    } else {
                        productCategory.setLevelPath(String.join(BaseConstants.Symbol.SLASH,
                                parent.getLevelPath(),
                                parent.getProductCategoryId().toString()));
                    }
                }
                productCategoriesRepository.insertSelective(productCategory);

            } else {
                // 更新，全称、是否启用代码、代码
                productCategoriesRepository.updateOptional(productCategory, ProductCategories.FIELD_CATEGORY_DESCRIPTION,
                        ProductCategories.FIELD_PRODUCT_CATEGORY_CODE);
            }
        }
        return productCategories;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ProductCategories> changeEnableStatus(Long tenantId, ProductCategoriesDTO productCategoriesDTO, Integer enabledFlag) {
        List<ProductCategoriesDTO> treeNodes = productCategoriesMapper.selectChildList(productCategoriesDTO);
        // 判断父级是否启用
        ProductCategories entity = productCategoriesRepository.selectByPrimaryKey(productCategoriesDTO.getProductCategoryId());
        Long parentCategoryId = entity.getParentCategoryId();
        ProductCategories parent = productCategoriesRepository.selectByPrimaryKey(parentCategoryId);
        if (!Objects.isNull(parent) && !Objects.equals(BaseConstants.Flag.YES, parent.getEnabledFlag())){
            throw new CommonException(Constants.AafmErrorCode.AAFM_PARENT_CATEGORY_NOT_ENABLE);
        }

        treeNodes.add(productCategoriesDTO);
        if (Objects.equals(BaseConstants.Flag.YES, enabledFlag)) {
            treeNodes.forEach(ProductCategories::enableCategory);
        } else {
            treeNodes.forEach(ProductCategories::disableCategory);
        }

        TreeDataUtils<ProductCategoriesDTO> treeDataUtil = new TreeDataUtils<>();

        List<ProductCategories> updateList = new ArrayList<>();
        treeDataUtil.treeDataToList(treeNodes, ProductCategories.class, updateList);

        productCategoriesRepository.batchUpdateOptional(updateList, ProductCategories.FIELD_ENABLED_FLAG);
        return updateList;
    }
}
