package org.hzero.halm.afm.api.controller.v1;

import io.choerodon.swagger.annotation.CustomPageRequest;
import io.swagger.annotations.Api;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.halm.afm.config.AafmSwaggerApiConfig;
import org.hzero.halm.afm.app.service.AssetStatusService;
import org.hzero.halm.afm.domain.entity.AssetStatus;
import org.hzero.halm.afm.domain.repository.AssetStatusRepository;
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

import java.util.List;

/**
 * 资产状态 管理 API
 *
 * @author zhisheng.zhang@hand-china.com 2019-02-20 15:56:29
 */
@Api(tags = AafmSwaggerApiConfig.ASSET_STATUS)
@RestController("assetStatusController.v1")
@RequestMapping("/v1/{organizationId}/asset-status")
public class AssetStatusController extends BaseController {

    @Autowired
    private AssetStatusRepository assetStatusRepository;
    @Autowired
    private AssetStatusService assetStatusService;

    @ApiOperation(value = "资产状态列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping
    public ResponseEntity<Page<AssetStatus>> assetStatusList(@PathVariable("organizationId") Long tenantId,AssetStatus assetStatus, @ApiIgnore @SortDefault(value = AssetStatus.FIELD_ASSET_STATUS_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        assetStatus.setTenantId(tenantId);
        Page<AssetStatus> list = assetStatusRepository.pageAndSort(pageRequest, assetStatus);
        return Results.success(list);
    }

    @ApiOperation(value = "批量修改资产状态")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<List<AssetStatus>> assetStatusUpdate(@PathVariable("organizationId") Long tenantId,@RequestBody List<AssetStatus> assetStatusList) {
        SecurityTokenHelper.validToken(assetStatusList);
        return Results.success(assetStatusService.assetStatusListUpdate(tenantId,assetStatusList));
    }

}
