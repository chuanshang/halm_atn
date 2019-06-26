package org.hzero.halm.atn.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.CustomPageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.atn.app.service.TransferOrderHeaderService;
import org.hzero.halm.atn.app.service.TransferOrderLineService;
import org.hzero.halm.atn.config.AatnSwaggerApiConfig;
import org.hzero.halm.atn.domain.entity.TransferOrderHeader;
import org.hzero.halm.atn.domain.entity.TransferOrderLine;
import org.hzero.halm.atn.domain.repository.TransferOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.TransferOrderLineRepository;
import org.hzero.halm.atn.infra.mapper.TransferOrderHeaderMapper;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

/**
 * 调拨转移单头 管理 API
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:34
 */
@RestController("transferOrderController.v1")
@RequestMapping("/v1/{organizationId}/transfer")
@Api(tags = AatnSwaggerApiConfig.AATN_TRANSFER_ORDER)
public class TransferOrderController extends BaseController {

    @Autowired
    private TransferOrderHeaderRepository transferOrderHeaderRepository;
    @Autowired
    private TransferOrderHeaderMapper transferOrderHeaderMapper;
    @Autowired
    private TransferOrderLineRepository transferOrderLineRepository;
    @Autowired
    private TransferOrderHeaderService transferOrderHeaderService;
    @Autowired
    private TransferOrderLineService transferOrderLineService;
    @Autowired
    private LovValueHandle lovValueHandle;

    @ApiOperation(value = "调拨转移单列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    @CustomPageRequest
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<TransferOrderHeader>> pageTransferOrder(@PathVariable("organizationId") Long tenantId,
                                                                       TransferOrderHeader transferOrderHeader,
                                                                       @ApiIgnore @SortDefault(value = TransferOrderHeader.FIELD_TRANSFER_HEADER_ID,
                                                                               direction = Sort.Direction.DESC) PageRequest pageRequest) {
        transferOrderHeader.setTenantId(tenantId);
        Page<TransferOrderHeader> list = transferOrderHeaderRepository.pageTransferOrder(pageRequest, transferOrderHeader);
        return Results.success(list);
    }

    @ApiOperation(value = "调拨转移单导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ExcelExport(TransferOrderHeader.class)
    @SuppressWarnings("unchecked")
    public ResponseEntity<List<TransferOrderHeader>> exportTransferOrder(@PathVariable("organizationId") Long tenantId,
                                                                         ExportParam exportParam,
                                                                         HttpServletResponse httpServletResponse,
                                                                         TransferOrderHeader transferOrderHeader) {
        transferOrderHeader.setTenantId(tenantId);
        List<TransferOrderHeader> transferOrderHeaders = transferOrderHeaderMapper.selectTransferOrderHeader(transferOrderHeader);
        return Results.success((List<TransferOrderHeader>) lovValueHandle.process(null, transferOrderHeaders));
    }

    @ApiOperation(value = "调拨转移单明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/list")
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<TransferOrderLine>> detailTransferOrder(@PathVariable("organizationId") Long tenantId,
                                                                       TransferOrderHeader transferOrderHeader,
                                                                       @ApiIgnore @SortDefault(value = TransferOrderLine.FIELD_TRANSFER_LINE_ID,
                                                                               direction = Sort.Direction.DESC) PageRequest pageRequest) {
        transferOrderHeader.setTenantId(tenantId);
        return Results.success(transferOrderLineRepository.listTransferOrderLine(transferOrderHeader, pageRequest));
    }

    @ApiOperation(value = "创建调拨转移单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<TransferOrderHeader> createTransferOrder(@PathVariable("organizationId") Long tenantId,
                                                                   @RequestBody TransferOrderHeader transferOrderHeader) {
        validObject(transferOrderHeader);
        return Results.success(transferOrderHeaderService.createTransferOrder(tenantId, transferOrderHeader));
    }

    @ApiOperation(value = "修改调拨转移单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<TransferOrderHeader> updateTransferOrder(@PathVariable("organizationId") Long tenantId,
                                                                   @RequestBody TransferOrderHeader transferOrderHeader) {
        SecurityTokenHelper.validTokenIgnoreInsert(transferOrderHeader);
        validObject(transferOrderHeader);
        return Results.success(transferOrderHeaderService.updateTransferOrder(tenantId, transferOrderHeader));
    }

    @ApiOperation(value = "调入调出确认")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/confirm")
    public ResponseEntity<TransferOrderLine> transferConfirm(@PathVariable("organizationId") Long tenantId,
                                                             @RequestBody TransferOrderLine transferOrderLine) {
        SecurityTokenHelper.validToken(transferOrderLine);
        validObject(transferOrderLine);
        return Results.success(transferOrderLineService.transferConfirm(tenantId, transferOrderLine));
    }

    @ApiOperation(value = "调拨单提交")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit")
    public ResponseEntity<TransferOrderHeader> submitTransferOrder(@PathVariable("organizationId") Long tenantId,
                                                                   @RequestBody TransferOrderHeader transferOrderHeader) {
        //提交前，先保存
        if(Objects.isNull(transferOrderHeader.getTransferHeaderId())){
            this.createTransferOrder(tenantId,transferOrderHeader);
        }
        return Results.success(transferOrderLineService.submitTransferOrder(tenantId, transferOrderHeader));
    }
}
