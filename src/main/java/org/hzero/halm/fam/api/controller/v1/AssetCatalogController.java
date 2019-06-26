package org.hzero.halm.fam.api.controller.v1;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.fam.infra.utils.TreeDataUtils;
import org.hzero.halm.fam.api.dto.AssetCatalogDTO;
import org.hzero.halm.fam.app.service.AssetCatalogService;
import org.hzero.halm.fam.config.AfamSwaggerApiConfig;
import org.hzero.halm.fam.domain.entity.AssetCatalog;
import org.hzero.halm.fam.domain.repository.AssetCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.hzero.mybatis.helper.SecurityTokenHelper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 资产目录 管理 API
 *
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
@RestController("assetCatalogController.v1")
@RequestMapping("/v1/{organizationId}/asset-catalog")
@Api(tags = AfamSwaggerApiConfig.AFAM_ASSET_CATALOG)
public class AssetCatalogController extends BaseController {

    @Autowired
    private AssetCatalogRepository assetCatalogRepository;
    @Autowired
    private AssetCatalogService assetCatalogService;

    @ApiOperation(value = "资产目录列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping
    public ResponseEntity<List<AssetCatalogDTO>> assetCatalogList(@PathVariable("organizationId") Long tenantId, AssetCatalog assetCatalog, @ApiIgnore @SortDefault(value = AssetCatalog.FIELD_ASSET_CATALOG_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        assetCatalog.setTenantId(tenantId);
        List<AssetCatalogDTO> list = assetCatalogService.assetCatalogTreeList(assetCatalog);
        return Results.success(list);
    }

    @ApiOperation(value = "资产目录新增/编辑")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<AssetCatalog>> assetCatalogCreateAndEdit(@PathVariable("organizationId") Long tenantId, @RequestBody List<AssetCatalog> assetCatalogList) {
        validList(assetCatalogList);
        SecurityTokenHelper.validTokenIgnoreInsert(assetCatalogList);
        List<AssetCatalog> assetCatalogs = assetCatalogService.assetCatalogCreateAndEdit(tenantId,assetCatalogList);
        return Results.success(assetCatalogs);
    }

    @ApiOperation(value = "资产目录列表导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ExcelExport(AssetCatalogDTO.class)
    @GetMapping("/export")
    public ResponseEntity<List<AssetCatalogDTO>> assetCatalogExport(@PathVariable("organizationId") Long tenantId,
                                                                         AssetCatalog assetCatalog,
                                                                         ExportParam exportParam,
                                                                         HttpServletResponse response) {
        assetCatalog.setTenantId(tenantId);
        List<AssetCatalogDTO> assetCatalogDTOList = assetCatalogService.assetCatalogTreeList(assetCatalog);
        TreeDataUtils<AssetCatalogDTO> treeDataUtil = new TreeDataUtils<>();
        List<AssetCatalogDTO> result = new ArrayList<>();
        treeDataUtil.treeDataToList(assetCatalogDTOList, AssetCatalogDTO.class, result);
        return Results.success(result);
    }

    @ApiOperation(value = "资产目录禁用当前及下级")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/disabled")
    public ResponseEntity<List<AssetCatalog>> assetCatalogDisabled(@PathVariable("organizationId") Long tenantId,Long assetCatalogId) {
        return Results.success(assetCatalogService.changeAssetCatalogEnabledFlag(tenantId,assetCatalogId,BaseConstants.Flag.NO));
    }

    @ApiOperation(value = "资产目录启用")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/enabled")
    public ResponseEntity<List<AssetCatalog>> assetCatalogEnabled(@PathVariable("organizationId") Long tenantId,Long assetCatalogId) {
        return Results.success(assetCatalogService.changeAssetCatalogEnabledFlag(tenantId,assetCatalogId,BaseConstants.Flag.YES));
    }

    @ApiOperation(value = "删除资产目录")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/{assetCatalogId}")
    public ResponseEntity<AssetCatalog> assetCatalogRemove(@ApiParam(value = "资产目录ID",required = true) @PathVariable Long assetCatalogId,
                                                           @PathVariable("organizationId") Long tenantId,
                                                           @RequestBody List<AssetCatalog> assetCatalogList) {
        SecurityTokenHelper.validTokenIgnoreInsert(assetCatalogList);
        assetCatalogService.assetCatalogRemove(tenantId,assetCatalogList);
        return Results.success();
    }

}
