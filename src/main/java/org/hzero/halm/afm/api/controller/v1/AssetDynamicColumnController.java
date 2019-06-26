package org.hzero.halm.afm.api.controller.v1;

import io.choerodon.mybatis.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.halm.afm.app.service.AssetDynamicColumnService;
import org.hzero.halm.afm.config.AafmSwaggerApiConfig;
import org.hzero.halm.afm.domain.entity.AssetDynamicColumn;
import org.hzero.halm.afm.domain.repository.AssetDynamicColumnRepository;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
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
 * 资产动态字段配置 管理 API
 *
 * @author wen.luo@hand-china.com 2019-04-02 16:06:20
 */
@RestController("assetDynamicColumnController.v1")
@RequestMapping("/v1/{organizationId}/dynamic-columns")
@Api(tags = AafmSwaggerApiConfig.ASSET_DYNAMIC_COLUMN)
public class AssetDynamicColumnController extends BaseController {

    @Autowired
    private AssetDynamicColumnRepository assetDynamicColumnRepository;

    @Autowired
    private AssetDynamicColumnService assetDynamicColumnService;

    @ApiOperation(value = "资产动态字段配置列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping
    public ResponseEntity<Page<AssetDynamicColumn>> list(@PathVariable("organizationId") Long tenantId,
                                                         AssetDynamicColumn assetDynamicColumn,
                                                         @ApiIgnore @SortDefault(value = AssetDynamicColumn.FIELD_COLUMN_ID,
                                                                 direction = Sort.Direction.DESC) PageRequest pageRequest) {
        assetDynamicColumn.setTenantId(tenantId);
        Page<AssetDynamicColumn> list = PageHelper.doPageAndSort(pageRequest, () -> assetDynamicColumnRepository.selectByCondition(
                Condition.builder(AssetDynamicColumn.class)
                        .andWhere(
                                Sqls.custom().andEqualTo(AssetDynamicColumn.FIELD_TENANT_ID, assetDynamicColumn.getTenantId())
                        ).build()));

        return Results.success(list);
    }

    @ApiOperation(value = "批量保存资产动态字段配置")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<AssetDynamicColumn>> create(@PathVariable("organizationId") Long tenantId,
                                                           @RequestBody List<AssetDynamicColumn> assetDynamicColumnList) {
        //校验批量创建的数据
        this.validList(assetDynamicColumnList);
        //验证Token
        SecurityTokenHelper.validTokenIgnoreInsert(assetDynamicColumnList);

        List<AssetDynamicColumn> resultAssetDynamicColumnList = assetDynamicColumnService.insertBatchAssetDynamicColumn(tenantId, assetDynamicColumnList);
        return Results.success(resultAssetDynamicColumnList);
    }

    @ApiOperation(value = "删除单条资产动态字段配置")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/{columnId}")
    public ResponseEntity remove(@PathVariable("organizationId") Long tenantId,
                                 @PathVariable("columnId") Long columnId,
                                 @RequestBody AssetDynamicColumn assetDynamicColumn) {
        SecurityTokenHelper.validToken(assetDynamicColumn);
        assetDynamicColumn.setTenantId(tenantId);
        assetDynamicColumn.setColumnId(columnId);
        assetDynamicColumnRepository.deleteByPrimaryKey(assetDynamicColumn);
        return Results.success();
    }

}
