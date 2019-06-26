package org.hzero.halm.afm.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.afm.app.service.AssetSpecialtyService;
import org.hzero.halm.afm.config.AafmSwaggerApiConfig;
import org.hzero.halm.afm.domain.entity.AssetSpecialty;
import org.hzero.halm.afm.domain.repository.AssetSpecialtyRepository;
import org.hzero.halm.afm.domain.vo.AssetSpecialtyDetailExportVO;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 资产专业定义(头) 管理 API
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:37
 */
@RestController("assetSpecialtyController.v1")
@RequestMapping("/v1/{organizationId}/asset-specialty")
@Api(tags = AafmSwaggerApiConfig.AAFM_ASSET_SPECIALTY)
public class AssetSpecialtyController extends BaseController {

    @Autowired
    private AssetSpecialtyRepository assetSpecialtyRepository;

    @Autowired
    private AssetSpecialtyService assetSpecialtyService;


    @ApiOperation(value = "资产专业分类列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    @CustomPageRequest
    public ResponseEntity<Page<AssetSpecialty>> list(AssetSpecialty assetSpecialty,
                                                     @ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
                                                     @ApiIgnore @SortDefault(value = AssetSpecialty.FIELD_ASSET_SPECIALTY_ID,
                                    direction = Sort.Direction.DESC) PageRequest pageRequest) {
        assetSpecialty.setTenantId(organizationId);
        Page<AssetSpecialty> list =
                        assetSpecialtyService.pageAssetSpecialty(pageRequest, organizationId, assetSpecialty);
        return Results.success(list);
    }

    @ApiOperation(value = "资产专业分类明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{assetSpecialtyId}")
    @CustomPageRequest
    public ResponseEntity<AssetSpecialty> detail(@PathVariable Long assetSpecialtyId,
                    @ApiIgnore @SortDefault(value = AssetSpecialty.FIELD_ASSET_SPECIALTY_ID,
                                    direction = Sort.Direction.DESC) PageRequest pageRequest) {
        AssetSpecialty assetSpecialty = assetSpecialtyService.assetSpecialtyDetail(pageRequest, assetSpecialtyId);
        return Results.success(assetSpecialty);
    }

    @ApiOperation(value = "创建或修改资产专业分类")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<AssetSpecialty> createAndUpdate(
                    @ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
                    @RequestBody AssetSpecialty assetSpecialty) {
        // 校验对象
        this.validObject(assetSpecialty);
        this.validList(assetSpecialty.getAssetToOrgList());
        SecurityTokenHelper.validTokenIgnoreInsert(assetSpecialty);

        assetSpecialtyService.createAndUpdate(organizationId, assetSpecialty);
        return Results.success(assetSpecialty);
    }

    @ApiOperation(value = "批量启用或禁用资产专业分类")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<List<AssetSpecialty>> batchUpdate(
                    @ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
                    @RequestBody List<AssetSpecialty> assetSpecialtyList) {
        SecurityTokenHelper.validTokenIgnoreInsert(assetSpecialtyList);
        /*assetSpecialtyService.createAndUpdate(organizationId, assetSpecialty);*/
        assetSpecialtyRepository.batchUpdateByPrimaryKeySelective(assetSpecialtyList);
        return Results.success(assetSpecialtyList);
    }

    @ApiOperation(value = "资产专业分类导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ExcelExport(AssetSpecialtyDetailExportVO.class)
    public ResponseEntity<List<AssetSpecialtyDetailExportVO>> exportAssetSpecialty(
            @PathVariable("organizationId") Long organizationId, AssetSpecialty assetSpecialty,
            ExportParam exportParam, HttpServletResponse response) {
        assetSpecialty.setTenantId(organizationId);
        return Results.success(assetSpecialtyService.exportAssetSpecialtyByIds(assetSpecialty));
    }

    @ApiOperation(value = "资产专业分类明细页模糊检索")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/list")
    @CustomPageRequest
    public ResponseEntity<Page<AssetSpecialty>> selectListByCondition(
                    @PathVariable("organizationId") @ApiParam(value = "租户Id", required = true) Long organizationId,
                    @RequestParam(required = false) @ApiParam(value = "筛选条件", required = false) String condition,
                    @ApiIgnore @SortDefault(value = AssetSpecialty.FIELD_ASSET_SPECIALTY_ID,
                                    direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<AssetSpecialty> page =
                        assetSpecialtyRepository.selectListByCondition(pageRequest, organizationId, condition);
        return Results.success(page);
    }

}
