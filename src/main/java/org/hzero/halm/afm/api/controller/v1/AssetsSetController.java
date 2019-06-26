/*
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */

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
import org.hzero.halm.afm.api.dto.AssetsSetDTO;
import org.hzero.halm.afm.config.AafmSwaggerApiConfig;
import org.hzero.halm.afm.domain.entity.AssetsSet;
import org.hzero.halm.afm.domain.repository.AssetsSetRepository;
import org.hzero.halm.afm.infra.constant.Constants;
import org.hzero.halm.afm.infra.utils.DtoUtils;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资产组 管理 API
 *
 * @author sen.wang@hand-china.com 2019-01-11 13:58:49
 */
@Api(tags = AafmSwaggerApiConfig.AAFM_ASSETSET)
@RestController("assetsSetController.v1")
@RequestMapping("/v1/{organizationId}/assets-set")
public class AssetsSetController extends BaseController {

    @Autowired
    private AssetsSetRepository assetsSetRepository;

    /**
     * 列表数据的查询
     */
    @ApiOperation(value = "资产组列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    @CustomPageRequest
    public ResponseEntity<Page<AssetsSet>> assetSetPage(@PathVariable("organizationId")
                                                        @ApiParam(value = "租户Id", required = true) Long tenantId,
                                                        AssetsSet assetsSet,
                                                        @ApiIgnore @SortDefault(value = AssetsSet.FIELD_ASSETS_SET_ID,
                                                                direction = Sort.Direction.DESC) PageRequest pageRequest) {

        Page<AssetsSet> list = assetsSetRepository.selectAssetList(tenantId, pageRequest, assetsSet);
        return Results.success(list);
    }


    /**
     * 新增操作
     */
    @ApiOperation(value = "创建资产组")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<AssetsSet> assetSetCreate(@PathVariable("organizationId")
                                                    @ApiParam(value = "租户Id", required = true) Long tenantId, @RequestBody AssetsSet assetsSet) {
        validObject(assetsSet);
        assetsSet.setTenantId(tenantId);
        DtoUtils.validUniqueIndex(assetsSetRepository,Constants.AafmErrorCode.ASSET_SET_NAME_DUPLICATED,assetsSet,AssetsSet.FIELD_ASSETS_SET_NAME);
        DtoUtils.validUniqueIndex(assetsSetRepository, Constants.AafmErrorCode.ASSET_SET_NUM_DUPLICATED,assetsSet,AssetsSet.FIELD_ASSETS_SET_NUM);
        assetsSetRepository.insertSelective(assetsSet);
        return Results.created(assetsSet);
    }

    /**
     * 保存操作
     */
    @ApiOperation(value = "修改资产组")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<AssetsSet> assetSetUpdate(@PathVariable("organizationId")
                                                @ApiParam(value = "租户Id", required = true) Long tenantId,
                                                    @RequestBody AssetsSet assetsSet) {
        SecurityTokenHelper.validToken(assetsSet);
        assetsSetRepository.updateByPrimaryKey(assetsSet);
        return Results.success(assetsSet);
    }

    /**
     * 导出操作
     */
    @ApiOperation(value = "导出资产组")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ExcelExport(AssetsSetDTO.class)
    public ResponseEntity<List<AssetsSet>> export(@PathVariable("organizationId")
                                 @ApiParam(value = "租户Id", required = true) Long tenantId,
                                                  AssetsSet assetsSet, ExportParam exportParam,
                                                  HttpServletResponse response) {

        List<AssetsSet> list = assetsSetRepository.selectAllAssetList(tenantId,  assetsSet);
        return Results.success(list);
    }

    @ApiOperation(value = "资产组明细列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/detail/list")
    @CustomPageRequest
    public ResponseEntity<Page<AssetsSet>> assetSetDetailList(@PathVariable("organizationId")
                                                            @ApiParam(value = "租户Id", required = true) Long tenantId,
                                                              @RequestParam(required = false) @ApiParam(value = "详情列表的筛选条件", required = false)String detailCondition,
                                                              @ApiIgnore @SortDefault(value = AssetsSet.FIELD_ASSETS_SET_ID,
                                                                direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<AssetsSet> page = assetsSetRepository.selectListByDetailCondition(tenantId,pageRequest,detailCondition);
        return Results.success(page);
    }

    /**
     * 明细查询
     */
    @ApiOperation(value = "资产组明细详情")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{assetsSetId}")
    public ResponseEntity<AssetsSet> assetSetDetail(@PathVariable("organizationId")
                                                    @ApiParam(value = "租户Id", required = true) Long tenantId,
                                                    @PathVariable Long assetsSetId) {
        AssetsSet assetsSet = assetsSetRepository.selectDeatilAsset(tenantId,assetsSetId);
        return Results.success(assetsSet);
    }

}
