package org.hzero.halm.afm.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.afm.api.dto.ProductCategoriesDTO;
import org.hzero.halm.afm.app.service.ProductCategoriesService;
import org.hzero.halm.afm.config.AafmSwaggerApiConfig;
import org.hzero.halm.afm.domain.entity.ProductCategories;
import org.hzero.halm.afm.domain.repository.ProductCategoriesRepository;
import org.hzero.halm.afm.infra.mapper.ProductCategoriesMapper;
import org.hzero.halm.afm.infra.utils.TreeDataUtils;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 产品类别/资产分类 管理 API
 *
 * @author like.zhang@hand-china.com 2019-01-11 10:42:38
 */
@RestController("productCategoriesController.v1")
@RequestMapping("/v1/{organizationId}/product-categories")
@Api(tags = AafmSwaggerApiConfig.AAFM_PRODUCT_CATEGORY)
public class ProductCategoriesController extends BaseController {

    @Autowired
    private ProductCategoriesService productCategoriesService;
    @Autowired
    private ProductCategoriesRepository productCategoriesRepository;
    @Autowired
    private ProductCategoriesMapper productCategoriesMapper;

    @ApiOperation(value = "产品类别/资产分类树状结构")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<List<ProductCategoriesDTO>> treeProductCategories(@PathVariable("organizationId") Long tenantId,
                                                                            ProductCategories productCategories) {
        return Results.success(productCategoriesRepository.treeProductCategories(productCategories, tenantId));
    }

    @ApiOperation(value = "产品类别/资产分类列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/list")
    @CustomPageRequest
    public ResponseEntity<Page<ProductCategoriesDTO>> listProductCategories(@PathVariable("organizationId") Long tenantId,
                                                                            ProductCategories productCategories,
                                                                            @ApiIgnore @SortDefault(value = ProductCategories.FIELD_PRODUCT_CATEGORY_ID, direction = Sort.Direction.DESC) PageRequest pageRequest) {
        productCategories.setTenantId(tenantId);
        return Results.success(PageHelper.doPageAndSort(pageRequest, () -> productCategoriesMapper.selectProductCategories(productCategories)));
    }


    @ApiOperation(value = "产品类别/资产分类批量保存")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    @ApiImplicitParam(name = "productCategories", value = "保存产品类别/资产分类信息", paramType = "body")
    public ResponseEntity<List<ProductCategories>> batchSaveCategory(@PathVariable("organizationId") Long tenantId,
                                                                     @RequestBody List<ProductCategories> productCategories) {
        SecurityTokenHelper.validTokenIgnoreInsert(productCategories);
        validList(productCategories);
        return Results.success(productCategoriesService.batchSaveProductCategory(productCategories, tenantId));
    }

    @ApiOperation(value = "产品类别/资产分类导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ExcelExport(ProductCategoriesDTO.class)
    public ResponseEntity<List<ProductCategoriesDTO>> exportProductCategory(@PathVariable("organizationId") Long tenantId,
                                                                         ProductCategories productCategories,
                                                                         ExportParam exportParam,
                                                                         HttpServletResponse httpServletResponse) {
        productCategories.setTenantId(tenantId);
        List<ProductCategoriesDTO> productCategoriesDTOS = productCategoriesRepository.treeProductCategories(productCategories, tenantId);
        TreeDataUtils<ProductCategoriesDTO> treeDataUtil = new TreeDataUtils<>();
        List<ProductCategoriesDTO> result = new ArrayList<>();
        treeDataUtil.treeDataToList(productCategoriesDTOS, ProductCategoriesDTO.class, result);
        return Results.success(result);
    }

    @ApiOperation(value = "产品类别/资产分类启用")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/{categoryId}/enabled")
    public ResponseEntity<List<ProductCategories>> enableProductCategories(@PathVariable("organizationId") Long tenantId,
                                                                           @RequestBody ProductCategoriesDTO productCategories) {
        SecurityTokenHelper.validToken(productCategories);
        return Results.success(productCategoriesService.changeEnableStatus(tenantId, productCategories, BaseConstants.Flag.YES));
    }

    @ApiOperation(value = "产品类别/资产分类禁用")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/{categoryId}/disabled")
    public ResponseEntity<List<ProductCategories>> disableProductCategories(@PathVariable("organizationId") Long tenantId,
                                                                            @RequestBody ProductCategoriesDTO productCategories) {
        SecurityTokenHelper.validToken(productCategories);
        return Results.success(productCategoriesService.changeEnableStatus(tenantId, productCategories, BaseConstants.Flag.NO));
    }

}
