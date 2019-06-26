package org.hzero.halm.atn.api.controller.v1;

import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.swagger.annotations.Api;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.atn.app.service.ScrapOrderHeaderService;
import org.hzero.halm.atn.app.service.ScrapOrderLineService;
import org.hzero.halm.atn.config.AatnSwaggerApiConfig;
import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;
import org.hzero.halm.atn.domain.entity.ScrapOrderLine;
import org.hzero.halm.atn.domain.repository.ScrapOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.ScrapOrderLineRepository;
import org.hzero.halm.atn.infra.mapper.ScrapOrderHeaderMapper;
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
import java.util.Objects;

/**
 * 资产报废单行 管理  API
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:10
 */
@RestController("scrapOrderLineController.v1")
@RequestMapping("/v1/{organizationId}/scrap")
@Api(tags = AatnSwaggerApiConfig.AATN_SCRAP_ORDER)
public class ScrapOrderController extends BaseController {

	@Autowired
	private ScrapOrderHeaderRepository scrapOrderHeaderRepository;
	@Autowired
	private ScrapOrderHeaderMapper scrapOrderHeaderMapper;
	@Autowired
	private ScrapOrderLineRepository scrapOrderLineRepository;
	@Autowired
	private ScrapOrderHeaderService scrapOrderHeaderService;
	@Autowired
	private ScrapOrderLineService scrapOrderLineService;

	@ApiOperation(value = "资产报废单列表")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@GetMapping
	@CustomPageRequest
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	public ResponseEntity<Page<ScrapOrderHeader>> pageScrapOrder(@PathVariable("organizationId") Long tenantId,
																 ScrapOrderHeader scrapOrderHeader,
																 @ApiIgnore @SortDefault(value = ScrapOrderHeader.FIELD_SCRAP_HEADER_ID,
																		 direction = Sort.Direction.DESC) PageRequest pageRequest) {
		scrapOrderHeader.setTenantId(tenantId);
		Page<ScrapOrderHeader> list = scrapOrderHeaderRepository.pageScrapOrder(pageRequest, scrapOrderHeader);
		return Results.success(list);
	}

	@ApiOperation(value = "资产报废单导出")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@GetMapping("/export")
	@ExcelExport(ScrapOrderHeader.class)
	public ResponseEntity<List<ScrapOrderHeader>> exportScrapOrder(@PathVariable("organizationId") Long tenantId,
																   ExportParam exportParam,
																   HttpServletResponse httpServletResponse,
																   ScrapOrderHeader scrapOrderHeader) {
		scrapOrderHeader.setTenantId(tenantId);
		return Results.success(scrapOrderHeaderMapper.selectScrapOrderHeader(scrapOrderHeader));
	}

	@ApiOperation(value = "资产报废单数据检索")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@GetMapping("/list")
	@CustomPageRequest
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	public ResponseEntity<Page<ScrapOrderHeader>> searchScrapOrder(@PathVariable("organizationId") Long tenantId,
																   String content, @ApiIgnore @SortDefault(value = ScrapOrderHeader.FIELD_SCRAP_HEADER_ID,
			direction = Sort.Direction.DESC) PageRequest pageRequest) {
		return Results.success(scrapOrderHeaderRepository.listScrapOrderHeader(pageRequest, tenantId, content));
	}

	@ApiOperation(value = "资产报废单明细查询")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@GetMapping("/{scapId}")
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	public ResponseEntity<ScrapOrderHeader> detailScrapOrder(@PathVariable("organizationId") Long tenantId,
															 @PathVariable("scapId") Long scapId) {

		return Results.success(scrapOrderHeaderRepository.detailScrapOrder(tenantId, scapId));
	}

	@ApiOperation(value = "创建资产报废单")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PostMapping
	public ResponseEntity<ScrapOrderHeader> createScrapOrder(@PathVariable("organizationId") Long tenantId,
															 @RequestBody ScrapOrderHeader scrapOrderHeader) {
		scrapOrderHeaderService.validateNotEmpty(scrapOrderHeader.getScrapOrderLines());
		validObject(scrapOrderHeader);
		return Results.success(scrapOrderHeaderService.createScrapOrder(tenantId, scrapOrderHeader));
	}

	@ApiOperation(value = "修改资产报废单")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PutMapping
	public ResponseEntity<ScrapOrderHeader> updateScrapOrder(@PathVariable("organizationId") Long tenantId,
															 @RequestBody ScrapOrderHeader scrapOrderHeader) {
		SecurityTokenHelper.validTokenIgnoreInsert(scrapOrderHeader);
		validObject(scrapOrderHeader);
		return Results.success(scrapOrderHeaderService.updateScrapOrder(tenantId, scrapOrderHeader));
	}

	@ApiOperation(value = "资产报废确认")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PostMapping("/confirm")
	public ResponseEntity<ScrapOrderLine> scrapConfirm(@PathVariable("organizationId") Long tenantId,
													   @RequestBody ScrapOrderLine scrapOrderLine) {
		SecurityTokenHelper.validToken(scrapOrderLine);
		validObject(scrapOrderLine);
		return Results.success(scrapOrderLineService.scrapConfirm(tenantId, scrapOrderLine));
	}

	@ApiOperation(value = "入口&处理明细查询")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@GetMapping("/entryProcess")
	public ResponseEntity<Page<ScrapOrderLine>> entryProcessDetail(@PathVariable("organizationId") Long tenantId,
																   ScrapOrderHeader scrapOrderHeader,
																   @ApiIgnore @SortDefault(value = ScrapOrderHeader.FIELD_SCRAP_HEADER_ID,
																		   direction = Sort.Direction.DESC) PageRequest pageRequest) {
		scrapOrderHeader.setTenantId(tenantId);
		Page<ScrapOrderLine> scrapOrderLineList = PageHelper.doPageAndSort(pageRequest, () -> scrapOrderLineRepository.entryProcessDetail(scrapOrderHeader));
		return Results.success(scrapOrderLineList);
	}

	@ApiOperation(value = "删除行")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@DeleteMapping("/line")
	public ResponseEntity<ScrapOrderLine> removeLine(@PathVariable("organizationId") Long tenantId,
													 @RequestBody ScrapOrderLine scrapOrderLine) {
		SecurityTokenHelper.validToken(scrapOrderLine);
		scrapOrderLineService.removeLine(tenantId, scrapOrderLine);
		return Results.success();
	}

	@ApiOperation(value = "提交审批")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PostMapping("/submit")
	public ResponseEntity<ScrapOrderHeader> submitApprovalRequest(@PathVariable("organizationId") Long tenantId,
																  @RequestBody ScrapOrderHeader scrapOrderHeader) {
		//提交前，先保存
		if(Objects.isNull(scrapOrderHeader.getScrapHeaderId())){
			this.createScrapOrder(tenantId,scrapOrderHeader);
		}
		return Results.success(scrapOrderHeaderService.submitApprovalRequest(tenantId, scrapOrderHeader));
	}
}
