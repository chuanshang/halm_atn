package org.hzero.halm.fam.api.controller.v1;

import io.choerodon.swagger.annotation.CustomPageRequest;
import io.swagger.annotations.Api;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.fam.api.dto.FixedAssetsDTO;
import org.hzero.halm.fam.app.service.FixedAssetsService;
import org.hzero.halm.fam.config.AfamSwaggerApiConfig;
import org.hzero.halm.fam.domain.entity.FixedAssets;
import org.hzero.halm.fam.domain.entity.FixedAssetsChanges;
import org.hzero.halm.fam.domain.repository.FixedAssetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.hzero.mybatis.helper.SecurityTokenHelper;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 固定资产 管理 API
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:22:24
 */
@RestController("fixedAssetsController.v1")
@RequestMapping("/v1/{organizationId}/fixed-assets")
@Api(tags = AfamSwaggerApiConfig.AFAM_FIXED_ASSETS)
public class FixedAssetsController extends BaseController {

    @Autowired
    private FixedAssetsRepository fixedAssetsRepository;

    @Autowired
    private FixedAssetsService fixedAssetsService;

    @ApiOperation(value = "固定资产列表")
    @CustomPageRequest
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping
    public ResponseEntity<Page<FixedAssetsDTO>> list(@PathVariable("organizationId") Long tenantId,
                                                  FixedAssets fixedAssets,
                                                  @ApiIgnore @SortDefault(value = FixedAssets.FIELD_FIXED_ASSET_ID,
                                                          direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<FixedAssetsDTO> list = fixedAssetsService.pageFixedAssets(tenantId, fixedAssets, pageRequest);
        return Results.success(list);
    }

    @ApiOperation(value = "固定资产明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{fixedAssetId}")
    public ResponseEntity<FixedAssetsDTO> detail(@PathVariable("organizationId") Long tenantId,
                                              @PathVariable Long fixedAssetId) {
        FixedAssetsDTO fixedAssets = fixedAssetsService.getFixedAssets(tenantId, fixedAssetId);
        return Results.success(fixedAssets);
    }

    @ApiOperation(value = "创建和编辑固定资产")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<FixedAssets> createAndUpdate(@PathVariable("organizationId") Long tenantId,
                                                       @RequestBody FixedAssets fixedAssets) {

        fixedAssets.validateFixedAssets(tenantId, fixedAssetsRepository);
        validObject(fixedAssets);
        SecurityTokenHelper.validTokenIgnoreInsert(fixedAssets);
        fixedAssetsService.createAndUpdateFixedAssets(tenantId, fixedAssets);
        return Results.success(fixedAssets);
    }

    @ApiOperation(value = "批量删除固定资产")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity remove(@PathVariable("organizationId") Long tenantId,
                                 @RequestBody List<FixedAssets> fixedAssetsList) {
        SecurityTokenHelper.validToken(fixedAssetsList);
        fixedAssetsService.removeFixedAssets(tenantId, fixedAssetsList);
        return Results.success();
    }

    @ApiOperation(value = "批量删除价值变动")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/{fixedAssetId}/removeChanges")
    public ResponseEntity removeChanges(@PathVariable("organizationId") Long tenantId,
                                        @PathVariable("fixedAssetId") Long fixedAssetId,
                                        @RequestBody List<FixedAssetsChanges> fixedAssetsChangesList) {
        SecurityTokenHelper.validToken(fixedAssetsChangesList);
        fixedAssetsService.removeFixedAssetsChanges(tenantId, fixedAssetsChangesList);
        return Results.success();
    }

    @ApiOperation(value = "查询价值变动list")
    @CustomPageRequest
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping("/{fixedAssetId}/changes")
    public ResponseEntity<List<FixedAssetsChanges>> listChanges(@PathVariable("organizationId") Long tenantId,
                                                                @PathVariable("fixedAssetId") Long fixedAssetId) {
        List<FixedAssetsChanges> list = fixedAssetsService.listFixedAssetsChanges(tenantId, fixedAssetId);
        return Results.success(list);
    }

    /**
     * 固定资产导出\勾选导出—固定资产
     *
     * @param organizationId
     * @param exportParam    excel参数
     * @param response       response
     * @return
     */
    @ApiOperation(value = "固定资产导出\\勾选导出—固定资产")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping("/export")
    @ExcelExport(FixedAssetsDTO.class)
    public ResponseEntity<List<FixedAssetsDTO>> exportFixedAssets(@PathVariable("organizationId") Long organizationId,
                                                                  FixedAssetsDTO fixedAssetsDTO,
                                                                  ExportParam exportParam,
                                                                  HttpServletResponse response
    ) {
        fixedAssetsDTO.setTenantId(organizationId);
        return Results.success(fixedAssetsService.exportFixedAssets(fixedAssetsDTO));
    }
}
