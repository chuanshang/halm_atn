package org.hzero.halm.afm.app.service;

import org.hzero.halm.afm.api.dto.ProductCategoriesDTO;
import org.hzero.halm.afm.domain.entity.ProductCategories;

import java.util.List;

/**
 * 产品类别/资产分类应用服务
 *
 * @author like.zhang@hand-china.com 2019-01-11 10:42:38
 */
public interface ProductCategoriesService {

    /**
     * 产品类别/资产分类批量保存
     *
     * @param productCategories 保存数据
     * @param tenantId          租户ID
     * @return
     */
    List<ProductCategories> batchSaveProductCategory(List<ProductCategories> productCategories, Long tenantId);

    /**
     * 启用当前级以及下级
     *
     * @param tenantId          租户ID
     * @param productCategoriesDTO 当前级数据信息
     * @param enabledFlag 是否启用
     * @return 更新后内容
     */
    List<ProductCategories> changeEnableStatus(Long tenantId, ProductCategoriesDTO productCategoriesDTO, Integer enabledFlag);

}
