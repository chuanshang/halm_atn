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
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.halm.afm.app.service.AssetService;
import org.hzero.halm.afm.config.AafmSwaggerApiConfig;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.repository.AssetRepository;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 资产/设备基本信息 管理 API
 *
 * @author zhisheng.zhang@hand-china.com 2019-01-24 16:43:37
 */
@Api(tags = AafmSwaggerApiConfig.AFM_ASSET)
@RestController("assetController.v1")
@RequestMapping("/v1/{organizationId}/asset-info")
public class AssetController extends BaseController {

	@Autowired
	private AssetRepository assetRepository;
	@Autowired
	private AssetService assetService;

	@ApiOperation(value = "创建资产/设备基本信息")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PostMapping
	public ResponseEntity<Asset> assetInfoCreate(@PathVariable("organizationId") Long tenantId, @RequestBody Asset asset) {
		asset.setTenantId(tenantId);
		Asset assetResult = assetService.insertAsset(tenantId, asset);
		return Results.success(assetResult);
	}

	@ApiOperation(value = "修改资产/设备基本信息")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PutMapping
	public ResponseEntity<Asset> assetInfoUpdate(@PathVariable("organizationId") Long tenantId, @RequestBody Asset asset) {
		asset.setTenantId(tenantId);
		SecurityTokenHelper.validToken(asset);
		Asset assetResult = assetService.updateAsset(asset);
		return Results.success(assetResult);
	}

	@ApiOperation(value = "删除资产/设备基本信息")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@DeleteMapping
	public ResponseEntity<List<Asset>> assetInfoRemove(@PathVariable("organizationId") Long tenantId, @RequestBody List<Asset> assetList) {
		SecurityTokenHelper.validTokenIgnoreInsert(assetList);
		List<Asset> assetListResult = new ArrayList<>();
		for (Asset asset : assetList) {
			asset.setTenantId(tenantId);
			Asset assetResult = assetService.deleteAsset(asset);
			assetListResult.add(assetResult);
		}

		return Results.success(assetListResult);
	}

	@ApiOperation(value = "资产基本信息查询")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@CustomPageRequest
	@GetMapping
	public ResponseEntity<Page<Asset>> list(Asset asset,
											@ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
											@ApiIgnore @SortDefault(value = Asset.FIELD_ASSET_ID,
													direction = Sort.Direction.DESC) PageRequest pageRequest) {

		Page<Asset> list = assetService.pageAsset(pageRequest, organizationId, asset);
		return Results.success(list);
	}

	@ApiOperation(value = "资产信息明细页，全局检索")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@CustomPageRequest
	@GetMapping("/list")
	public ResponseEntity<Page<Asset>> assetDetailList(@ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
													   @RequestParam(required = false) @ApiParam(value = "详情列表的筛选条件", required = false) String detailCondition,
													   @RequestParam(required = false) String organizationScope,
													   @RequestParam(required = false) String statusScope,
													   @RequestParam(required = false) String specialtyScope,
													   @ApiIgnore @SortDefault(value = Asset.FIELD_ASSET_ID,
															   direction = Sort.Direction.DESC) PageRequest pageRequest) {
		Page<Asset> list = assetService.selectAssetByDetailCondition(pageRequest, organizationId, detailCondition, organizationScope, statusScope, specialtyScope);
		return Results.success(list);
	}

	@ApiOperation(value = "资产信息明细查询")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@GetMapping("/{assetInfoId}")
	public ResponseEntity<Asset> assetDetail(@ApiParam(value = "资产ID", required = true) @PathVariable Long assetInfoId,
											 @ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId) {
		return Results.success(assetService.assetDetail(organizationId, assetInfoId));
	}

}
