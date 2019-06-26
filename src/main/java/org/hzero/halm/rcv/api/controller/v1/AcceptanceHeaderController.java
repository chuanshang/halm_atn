package org.hzero.halm.rcv.api.controller.v1;

import io.swagger.annotations.Api;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.rcv.app.service.AcceptanceHeaderService;
import org.hzero.halm.rcv.config.ArcvSwaggerApiConfig;
import org.hzero.halm.rcv.domain.entity.AcceptanceHeader;
import org.hzero.halm.rcv.domain.repository.AcceptanceHeaderRepository;
import org.hzero.halm.rcv.infra.constant.ArcvConstants;
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
 * 验收单 管理 API
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@RestController("acceptanceHeaderController.v1")
@RequestMapping("/v1/{organizationId}/acceptance-headers")
@Api(tags = ArcvSwaggerApiConfig.ARCV_ACCEPTANCE_HEADER)
public class AcceptanceHeaderController extends BaseController {

	@Autowired
	private AcceptanceHeaderRepository acceptanceHeaderRepository;

	@Autowired
	private AcceptanceHeaderService acceptanceHeaderService;

	@ApiOperation(value = "验收单列表")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@GetMapping
	public ResponseEntity<Page<AcceptanceHeader>> listAcceptanceHeader(@PathVariable("organizationId") Long tenantId,
																	   AcceptanceHeader acceptanceHeader,
																	   @ApiIgnore @SortDefault(value = AcceptanceHeader.FIELD_ACCEPTANCE_HEADER_ID,
																			   direction = Sort.Direction.DESC) PageRequest pageRequest) {
		acceptanceHeader.setTenantId(tenantId);
		Page<AcceptanceHeader> list = acceptanceHeaderRepository.listAcceptanceHeader(pageRequest, acceptanceHeader);
		return Results.success(list);
	}
	@ApiOperation(value = "验收单列表导出")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@GetMapping("/export")
	@ExcelExport(AcceptanceHeader.class)
	public ResponseEntity<Page<AcceptanceHeader>> listAcceptanceExport(@PathVariable("organizationId") Long tenantId,
																	   AcceptanceHeader acceptanceHeader,
																	   @ApiIgnore @SortDefault(value = AcceptanceHeader.FIELD_ACCEPTANCE_HEADER_ID,
																			   direction = Sort.Direction.DESC) PageRequest pageRequest,
																	   ExportParam exportParam,
																	   HttpServletResponse httpServletResponse) {
		acceptanceHeader.setTenantId(tenantId);
		Page<AcceptanceHeader> list = acceptanceHeaderRepository.listAcceptanceHeader(pageRequest, acceptanceHeader);
		return Results.success(list);
	}

	@ApiOperation(value = "验收单全文检索")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@GetMapping("/list")
	public ResponseEntity<Page<AcceptanceHeader>> listAcceptanceHeaderByCondition(@PathVariable("organizationId") Long tenantId,
																				  @RequestParam(required = false) String condition,
																				  @ApiIgnore @SortDefault(value = AcceptanceHeader.FIELD_ACCEPTANCE_HEADER_ID,
																						  direction = Sort.Direction.DESC) PageRequest pageRequest) {
		Page<AcceptanceHeader> list = acceptanceHeaderRepository.listAcceptanceHeaderByCondition(pageRequest, tenantId, condition);
		return Results.success(list);
	}

	@ApiOperation(value = "验收单明细")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@GetMapping("/details")
	public ResponseEntity<List<AcceptanceHeader>> detailAcceptance(@PathVariable("organizationId") Long tenantId,
																   @RequestParam String acceptanceHeaderIds) {
		return Results.success(acceptanceHeaderRepository.detailAcceptance(tenantId, acceptanceHeaderIds));
	}

	@ApiOperation(value = "创建验收单")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PostMapping
	public ResponseEntity<AcceptanceHeader> createAcceptanceHeader(@PathVariable("organizationId") Long tenantId,
																   @RequestBody AcceptanceHeader acceptanceHeader) {
		acceptanceHeader.setTenantId(tenantId);
		acceptanceHeader.setAcceptanceStatusCode(ArcvConstants.acceptanceStatus.NEW);
		validObject(acceptanceHeader);
		return Results.success(acceptanceHeaderService.insertAcceptanceHeader(acceptanceHeader));
	}

	@ApiOperation(value = "修改验收单头")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PutMapping
	public ResponseEntity<AcceptanceHeader> update(@PathVariable("organizationId") Long tenantId,
												   @RequestBody AcceptanceHeader acceptanceHeader) {
		acceptanceHeader.setTenantId(tenantId);
		SecurityTokenHelper.validToken(acceptanceHeader);
		return Results.success(acceptanceHeaderService.updateAcceptanceHeader(acceptanceHeader));
	}

	@ApiOperation(value = "提交审批")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PostMapping("/submit")
	public ResponseEntity<AcceptanceHeader> submit(@PathVariable("organizationId") Long tenantId,
												   @RequestParam Long acceptanceHeaderId) {
		return Results.success(acceptanceHeaderService.submitApproval(tenantId, acceptanceHeaderId));
	}

	@ApiOperation(value = "完成验收(生成资产明细行)")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PostMapping("/completeAcceptance")
	public ResponseEntity<AcceptanceHeader> completeAcceptance(@PathVariable("organizationId") Long tenantId,
												   @RequestParam Long acceptanceHeaderId) {
		return Results.success(acceptanceHeaderService.completeAcceptance(tenantId, acceptanceHeaderId));
	}

	@ApiOperation(value = "完成资产(根据资产明细行生成资产)")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PostMapping("/completeAsset")
	public ResponseEntity<AcceptanceHeader> completeAsset(@PathVariable("organizationId") Long tenantId,
															   @RequestParam Long acceptanceHeaderId) {
		acceptanceHeaderService.completeAsset(tenantId, acceptanceHeaderId);
		return Results.success();
	}


}
