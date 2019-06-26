package org.hzero.halm.atn.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.atn.app.service.HandoverOrderHeaderService;
import org.hzero.halm.atn.app.service.HandoverOrderLineService;
import org.hzero.halm.atn.config.AatnSwaggerApiConfig;
import org.hzero.halm.atn.domain.entity.HandoverOrderDetail;
import org.hzero.halm.atn.domain.entity.HandoverOrderHeader;
import org.hzero.halm.atn.domain.entity.HandoverOrderLine;
import org.hzero.halm.atn.domain.repository.HandoverOrderHeaderRepository;
import org.hzero.halm.atn.infra.mapper.HandoverOrderDetailMapper;
import org.hzero.halm.atn.infra.mapper.HandoverOrderHeaderMapper;
import org.hzero.halm.atn.infra.mapper.HandoverOrderLineMapper;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 资产移交归还单头 管理 API
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:03
 */
@RestController("handoverOrderHeaderController.v1")
@RequestMapping("/v1/{organizationId}/handover-order-headers")
@Api(tags = AatnSwaggerApiConfig.AATN_HANDOVER_ORDER)
public class HandoverOrderHeaderController extends BaseController {
    @Autowired
    private HandoverOrderHeaderRepository handoverOrderHeaderRepository;
    @Autowired
    private HandoverOrderLineService lineService;
    @Autowired
    private HandoverOrderHeaderService headerService;

    @Autowired
    private HandoverOrderHeaderMapper headerMapper;
    @Autowired
    private HandoverOrderLineMapper lineMapper;
    @Autowired
    private HandoverOrderDetailMapper detailMapper;

    @ApiOperation(value = "资产移交归还单头列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @CustomPageRequest
    @GetMapping
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<HandoverOrderHeader>> handoverOrderHeaderList(
            @PathVariable("organizationId") Long tenantId, HandoverOrderHeader handoverOrderHeader, @ApiIgnore @SortDefault(value = HandoverOrderHeader.FIELD_HANDOVER_HEADER_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        handoverOrderHeader.setTenantId(tenantId);
        Page<HandoverOrderHeader> list = PageHelper.doPageAndSort(pageRequest, () ->
                headerMapper.selectHeaderList(handoverOrderHeader)
        );
        return Results.success(list);
    }

    @ApiOperation(value = "资产移交归还单明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{handoverHeaderId}")
    public ResponseEntity<HandoverOrderHeader> handoverOrderDetail(@PathVariable Long handoverHeaderId, @PathVariable("organizationId") Long tenantId) {
        HandoverOrderHeader handoverOrderHeader = handoverOrderHeaderRepository.selectDetail(handoverHeaderId, tenantId);
        return Results.success(handoverOrderHeader);
    }

    @ApiOperation(value = "新增或编辑资产移交归还单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<HandoverOrderHeader> saveHandoverOrder(@RequestBody HandoverOrderHeader handoverOrderHeader, @PathVariable("organizationId") Long tenantId) throws IllegalAccessException {
        validObject(handoverOrderHeader);
        SecurityTokenHelper.validTokenIgnoreInsert(handoverOrderHeader);
        handoverOrderHeaderRepository.saveHandoverOrder(handoverOrderHeader, tenantId);
        return Results.success(handoverOrderHeader);
    }

    @ApiOperation(value = "资产移交归还单导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ExcelExport(HandoverOrderHeader.class)
    public ResponseEntity<List<HandoverOrderHeader>> exportHandoverOrder(@PathVariable("organizationId") Long tenantId,
                                                                         ExportParam exportParam,
                                                                         HttpServletResponse httpServletResponse,
                                                                         HandoverOrderHeader handoverOrderHeader) {
        handoverOrderHeader.setTenantId(tenantId);
        List<HandoverOrderHeader> headerList = headerMapper.selectHeaderList(handoverOrderHeader);
        return Results.success(headerList);
    }

    @ApiOperation(value = "执行处理移交归还单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/execute")
    public ResponseEntity<HandoverOrderLine> executeHandoverOrder(@RequestBody HandoverOrderLine handoverOrderLine, @PathVariable("organizationId") Long tenantId) throws IllegalAccessException {
        handoverOrderLine.setTenantId(tenantId);
        return Results.success(lineService.executeHandoverOrder(handoverOrderLine));
    }

    @ApiOperation(value = "提交移交归还单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping("/submit")
    public ResponseEntity<HandoverOrderHeader> submitHandoverOrder(@PathVariable("organizationId") Long tenantId, @RequestBody HandoverOrderHeader handoverOrderHeader) throws IllegalAccessException {
        handoverOrderHeader.setTenantId(tenantId);
        //提交前，先保存
        if(Objects.isNull(handoverOrderHeader.getHandoverHeaderId())){
            this.saveHandoverOrder(handoverOrderHeader,tenantId);
        }
        headerService.submitHandover(handoverOrderHeader);
        return Results.success(handoverOrderHeader);
    }

    @ApiOperation(value = "删除处理移交归还单行")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<HandoverOrderLine> removeHandoverOrderLine(@RequestBody List<HandoverOrderLine> lineList, @PathVariable("organizationId") Long tenantId) {
        lineService.deleteLine(lineList);
        return Results.success();
    }



    @ApiOperation(value = "查询移交归还单行执行列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping("/exelinelist")
    public ResponseEntity<Page<HandoverOrderLine>> handoverExeLineList(
            @PathVariable("organizationId") Long tenantId,
            HandoverOrderLine handoverOrderLine, @ApiIgnore @SortDefault(value = HandoverOrderLine.FIELD_HANDOVER_LINE_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        handoverOrderLine.setTenantId(tenantId);
        return Results.success(PageHelper.doPageAndSort(pageRequest, () ->
                lineMapper.selectLineAndHeader(handoverOrderLine)));
    }

    @ApiOperation(value = "查询移交归还单行的详细数据")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/detail")
    public ResponseEntity<List<HandoverOrderDetail>> queryHandoverDetail(@RequestParam Long lineId, @PathVariable("organizationId") Long tenantId) throws IllegalAccessException {
        HandoverOrderDetail detail = new HandoverOrderDetail();
        detail.setHandoverLineId(lineId);
        detail.setTenantId(tenantId);
        return Results.success(detailMapper.selectDetail(detail));
    }



}
