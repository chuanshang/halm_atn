package org.hzero.halm.rcv.api.controller.v1;

import io.swagger.annotations.Api;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.halm.rcv.app.service.AcceptanceLineService;
import org.hzero.halm.rcv.config.ArcvSwaggerApiConfig;
import org.hzero.halm.rcv.domain.entity.AcceptanceLine;
import org.hzero.halm.rcv.domain.repository.AcceptanceLineRepository;
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
 * 验收单行 管理 API
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@RestController("acceptanceLineController.v1")
@RequestMapping("/v1/{organizationId}/acceptance-lines")
@Api(tags = ArcvSwaggerApiConfig.ARCV_ACCEPTANCE_LINE)
public class AcceptanceLineController extends BaseController {

	@Autowired
	private AcceptanceLineService acceptanceLineService;
	@Autowired
	private AcceptanceLineRepository acceptanceLineRepository;

	@ApiOperation(value = "创建验收单行")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PostMapping
	public ResponseEntity<List<AcceptanceLine>> create(@PathVariable("organizationId") Long tenantId,
													   @RequestBody List<AcceptanceLine> acceptanceLineList) {
		validList(acceptanceLineList);
		return Results.success(acceptanceLineService.insertAcceptanceLine(tenantId, acceptanceLineList));
	}

	@ApiOperation(value = "修改验收单行")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PutMapping
	public ResponseEntity<List<AcceptanceLine>> update(@PathVariable("organizationId") Long tenantId,
												 @RequestBody List<AcceptanceLine> acceptanceLineList) {
		SecurityTokenHelper.validToken(acceptanceLineList);
		return Results.success(acceptanceLineService.updateAcceptanceLine(tenantId,acceptanceLineList));
	}

	@ApiOperation(value = "删除验收单行")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@DeleteMapping
	public ResponseEntity<AcceptanceLine> remove(@RequestBody AcceptanceLine acceptanceLine) {
		SecurityTokenHelper.validToken(acceptanceLine);
		acceptanceLineRepository.deleteByPrimaryKey(acceptanceLine);
		return Results.success();
	}


}
