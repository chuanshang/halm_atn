package org.hzero.halm.afm.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.halm.afm.api.dto.ProductCategoriesDTO;
import org.hzero.halm.afm.domain.entity.ProductCategories;

import java.util.List;

/**
 * 产品类别/资产分类Mapper
 *
 * @author like.zhang@hand-china.com 2019-01-11 10:42:38
 */
public interface ProductCategoriesMapper extends BaseMapper<ProductCategories>{

    /**
     * 查询产品类别
     *
     * @param productCategories 查询参数
     * @return 查询结果
     */
    List<ProductCategoriesDTO> selectProductCategories(ProductCategories productCategories);

    /**
     * 查询当前节点的所有下级节点
     *
     * @param productCategoriesDTO 当前节点
     * @return 所有下级节点
     */
    List<ProductCategoriesDTO> selectChildList(ProductCategoriesDTO productCategoriesDTO);

    /**
     * 查询树的根节点
     *
     * @param productCategories 查询参数
     * @return 根节点列表
     */
    List<ProductCategoriesDTO> selectParent(ProductCategories productCategories);
}
