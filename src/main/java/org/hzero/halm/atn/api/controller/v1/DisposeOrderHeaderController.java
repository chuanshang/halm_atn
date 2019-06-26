package org.hzero.halm.atn.api.controller.v1;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.atn.app.service.DisposeOrderHeaderService;
import org.hzero.halm.atn.app.service.DisposeOrderLineService;
import org.hzero.halm.atn.config.AatnSwaggerApiConfig;
import org.hzero.halm.atn.domain.entity.DisposeOrderHeader;
import org.hzero.halm.atn.domain.entity.DisposeOrderLine;
import org.hzero.halm.atn.domain.repository.DisposeOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.DisposeOrderLineRepository;
import org.hzero.halm.atn.infra.mapper.DisposeOrderHeaderMapper;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

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
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 资产处置单头 管理 API
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 11:05:34
 */
@RestController("disposeOrderHeaderController.v1")
@RequestMapping("/v1/{organizationId}/dispose")
@Api(tags = AatnSwaggerApiConfig.AATN_DISPOSE_ORDER)
public class DisposeOrderHeaderController extends BaseController {

    @Autowired
    private DisposeOrderHeaderRepository disposeOrderHeaderRepository;
    @Autowired
    private DisposeOrderLineRepository disposeOrderLineRepository;
    @Autowired
    private DisposeOrderHeaderMapper disposeOrderHeaderMapper;
    @Autowired
    private DisposeOrderHeaderService disposeOrderHeaderService;
    @Autowired
    private DisposeOrderLineService disposeOrderLineService;
    @Autowired
    private LovValueHandle lovValueHandle;
    @Autowired
    private CommonProcessUtils commonProcessUtils;

    @ApiOperation(value = "资产处置单头列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping
    public ResponseEntity<Page<DisposeOrderHeader>> disposeOrderHeaderList(@PathVariable("organizationId") Long tenantId, DisposeOrderHeader disposeOrderHeader, @ApiIgnore @SortDefault(value = DisposeOrderHeader.FIELD_DISPOSE_HEADER_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        disposeOrderHeader.setTenantId(tenantId);
        disposeOrderHeader.setProcessStatusCodeList(commonProcessUtils.processStatusCondition(tenantId, disposeOrderHeader.getCondition()));
        Page<DisposeOrderHeader> list = PageHelper.doPageAndSort(pageRequest, () ->disposeOrderHeaderMapper.pageDisposeOrderHeaderList(disposeOrderHeader));
        return Results.success(list);
    }

    @ApiOperation(value = "资产处置单头行明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping("/{disposeHeaderId}")
    public ResponseEntity<DisposeOrderHeader> disposeOrderHeaderDetail(@PathVariable Long disposeHeaderId,@PathVariable("organizationId") Long tenantId) {
        DisposeOrderHeader disposeOrderHeader = disposeOrderHeaderRepository.selectDetail(disposeHeaderId,tenantId);
        return Results.success(disposeOrderHeader);
    }

    @ApiOperation(value = "处理页面明细查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping("/entHandle")
    public ResponseEntity<Page<DisposeOrderLine>>  entHandle(@PathVariable("organizationId") Long tenantId,
                                                             DisposeOrderHeader disposeOrderHeader,
                                                             @ApiIgnore @SortDefault(value = DisposeOrderHeader.FIELD_DISPOSE_HEADER_ID,
                                                                     direction = Sort.Direction.DESC) PageRequest pageRequest) {
        disposeOrderHeader.setTenantId(tenantId);
        Page<DisposeOrderLine> disposeOrderHeaderList = PageHelper.doPageAndSort(pageRequest, () ->disposeOrderHeaderRepository.entryProcessDetail(disposeOrderHeader));
        return Results.success(disposeOrderHeaderList);
    }

    @ApiOperation(value = "创建资产处置单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<DisposeOrderHeader> disposeOrderHeaderCreate(@PathVariable("organizationId") Long tenantId,
                                                                       @RequestBody DisposeOrderHeader disposeOrderHeader) {
        validObject(disposeOrderHeader);
        SecurityTokenHelper.validTokenIgnoreInsert(disposeOrderHeader);
        return Results.success(disposeOrderHeaderService.disposeOrderHeaderCreate(tenantId,disposeOrderHeader));
    }

    @ApiOperation(value = "修改资产处置单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<DisposeOrderHeader> disposeOrderHeaderUpdate(@PathVariable("organizationId") Long tenantId,
                                                                       @RequestBody DisposeOrderHeader disposeOrderHeader) {
        validObject(disposeOrderHeader);
        SecurityTokenHelper.validTokenIgnoreInsert(disposeOrderHeader);
        return Results.success(disposeOrderHeaderService.disposeOrderHeaderUpdate(tenantId,disposeOrderHeader));
    }

    @ApiOperation(value = "资产处置单导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ExcelExport(DisposeOrderHeader.class)
    public ResponseEntity<List<DisposeOrderHeader>> disposeOrderExport(@PathVariable("organizationId") Long tenantId,
                                                                       ExportParam exportParam,
                                                                       HttpServletResponse httpServletResponse,
                                                                       DisposeOrderHeader disposeOrderHeader){
        disposeOrderHeader.setTenantId(tenantId);
        List<DisposeOrderHeader> disposeOrderHeaderList = disposeOrderHeaderMapper.pageDisposeOrderHeaderList(disposeOrderHeader);
        disposeOrderHeaderList = (List<DisposeOrderHeader>) lovValueHandle.process(null, disposeOrderHeaderList);
        return Results.success(disposeOrderHeaderList);
    }

    @ApiOperation(value = "行删除")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/lineRemove")
    public ResponseEntity<DisposeOrderLine> removeLine(@PathVariable("organizationId") Long tenantId,
                                                       @RequestBody DisposeOrderLine disposeOrderLine) {
        SecurityTokenHelper.validTokenIgnoreInsert(disposeOrderLine);
        disposeOrderLine.setTenantId(tenantId);
        disposeOrderLineService.removeLine(disposeOrderLine);
        return Results.success();
    }

    @ApiOperation(value = "处置确认")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/disposeConfirm")
    public ResponseEntity<DisposeOrderLine> executeProcess(@PathVariable("organizationId") Long tenantId,
                                                           @RequestBody DisposeOrderLine disposeOrderLine) {
        SecurityTokenHelper.validToken(disposeOrderLine);
//        validObject(disposeOrderLine);
        return Results.success(disposeOrderLineService.processConfirm(tenantId, disposeOrderLine));
    }

    @ApiOperation(value = "提交")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/commit")
    public ResponseEntity<DisposeOrderHeader> commitStatus(@PathVariable("organizationId") Long tenantId,
                                                           @RequestBody DisposeOrderHeader disposeOrderHeader) {
        SecurityTokenHelper.validTokenIgnoreInsert(disposeOrderHeader);
        //提交前，先保存
        if(Objects.isNull(disposeOrderHeader.getDisposeHeaderId())){
            this.disposeOrderHeaderCreate(tenantId,disposeOrderHeader);
        }
        return Results.success(disposeOrderHeaderService.commitStatus(tenantId, disposeOrderHeader));
    }
}
