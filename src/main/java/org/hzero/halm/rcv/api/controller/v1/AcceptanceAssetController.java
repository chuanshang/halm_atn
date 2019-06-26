package org.hzero.halm.rcv.api.controller.v1;

import io.swagger.annotations.Api;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.halm.rcv.app.service.AcceptanceAssetService;
import org.hzero.halm.rcv.config.ArcvSwaggerApiConfig;
import org.hzero.halm.rcv.domain.entity.AcceptanceAsset;
import org.hzero.halm.rcv.domain.repository.AcceptanceAssetRepository;
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

/**
 * 验收单资产明细 管理 API
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@RestController("acceptanceAssetController.v1")
@RequestMapping("/v1/{organizationId}/acceptance-assets")
@Api(tags = ArcvSwaggerApiConfig.ARCV_ACCEPTANCE_ASSET)
public class AcceptanceAssetController extends BaseController {

	@Autowired
	private AcceptanceAssetService acceptanceAssetService;

	@ApiOperation(value = "修改验收单资产明细")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PutMapping
	public ResponseEntity<AcceptanceAsset> update(@PathVariable("organizationId") Long tenantId,
												  @RequestBody AcceptanceAsset acceptanceAsset) {
		SecurityTokenHelper.validToken(acceptanceAsset);
		acceptanceAssetService.updateAcceptanceAsset(tenantId, acceptanceAsset);
		return Results.success(acceptanceAsset);
	}


}
