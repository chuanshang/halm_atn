package org.hzero.halm.afm.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.halm.afm.domain.entity.AssetLinear;
import org.hzero.halm.afm.domain.repository.AssetLinearRepository;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 资产-线性属性 管理 API
 *
 * @author zhisheng.zhang@hand-china.com 2019-01-24 16:43:37
 */
@RestController("assetLinearController.v1")
@RequestMapping("/v1/asset-linears")
public class AssetLinearController extends BaseController {

    @Autowired
    private AssetLinearRepository assetLinearRepository;

    @ApiOperation(value = "资产-线性属性列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<?> list(AssetLinear assetLinear, @ApiIgnore @SortDefault(value = AssetLinear.FIELD_LINEAR_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<AssetLinear> list = assetLinearRepository.pageAndSort(pageRequest, assetLinear);
        return Results.success(list);
    }

    @ApiOperation(value = "资产-线性属性明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{assetId}")
    public ResponseEntity<?> detail(@PathVariable Long assetId) {
        AssetLinear assetLinear = assetLinearRepository.selectByPrimaryKey(assetId);
        return Results.success(assetLinear);
    }

    @ApiOperation(value = "创建资产-线性属性")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody AssetLinear assetLinear) {
        assetLinearRepository.insertSelective(assetLinear);
        return Results.success(assetLinear);
    }

    @ApiOperation(value = "修改资产-线性属性")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<?> update(@RequestBody AssetLinear assetLinear) {
        SecurityTokenHelper.validToken(assetLinear);
        assetLinearRepository.updateByPrimaryKeySelective(assetLinear);
        return Results.success(assetLinear);
    }

    @ApiOperation(value = "删除资产-线性属性")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody AssetLinear assetLinear) {
        SecurityTokenHelper.validToken(assetLinear);
        assetLinearRepository.deleteByPrimaryKey(assetLinear);
        return Results.success();
    }

}
