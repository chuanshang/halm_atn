package org.hzero.halm.afm.domain.repository;

import org.hzero.halm.afm.api.dto.ProductCategoriesDTO;
import org.hzero.halm.afm.domain.entity.ProductCategories;
import org.hzero.mybatis.base.BaseRepository;

import java.util.List;

/**
 * 产品类别/资产分类资源库
 *
 * @author like.zhang@hand-china.com 2019-01-11 10:42:38
 */
public interface ProductCategoriesRepository extends BaseRepository<ProductCategories> {

    /**
     * 树状查询产品类别/资产分类
     *
     * @param productCategories 查询参数
     * @param tenantId          租户ID
     * @return 树形结构数据
     */
    List<ProductCategoriesDTO> treeProductCategories(ProductCategories productCategories, Long tenantId);

}
