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
import org.hzero.halm.atn.app.service.ChangeOrderHeaderService;
import org.hzero.halm.atn.app.service.ChangeOrderLineService;
import org.hzero.halm.atn.config.AatnSwaggerApiConfig;
import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;
import org.hzero.halm.atn.domain.entity.ChangeOrderLine;
import org.hzero.halm.atn.domain.repository.ChangeOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.ChangeOrderLineRepository;
import org.hzero.halm.atn.infra.mapper.ChangeOrderHeaderMapper;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

/**
 * 资产信息变更单 管理 API
 *
 * @author like.zhang@hand-china.com 2019-03-26 10:20:34
 */
@RestController("changeOrderController.v1")
@RequestMapping("/v1/{organizationId}/change")
@Api(tags = AatnSwaggerApiConfig.AATN_CHANGE_ORDER)
public class ChangeOrderController extends BaseController {

    @Autowired
    private ChangeOrderHeaderRepository changeOrderHeaderRepository;
    @Autowired
    private ChangeOrderHeaderMapper changeOrderHeaderMapper;
    @Autowired
    private ChangeOrderLineRepository changeOrderLineRepository;
    @Autowired
    private ChangeOrderHeaderService changeOrderHeaderService;
    @Autowired
    private ChangeOrderLineService changeOrderLineService;
    @Autowired
    private LovValueHandle lovValueHandle;

    @ApiOperation(value = "资产状态信息变更单列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    @CustomPageRequest
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<ChangeOrderHeader>> pageChangeOrder(@PathVariable("organizationId") Long tenantId,
                                                                   ChangeOrderHeader changeOrderHeader,
                                                                   @ApiIgnore @SortDefault(value = ChangeOrderHeader.FIELD_CHANGE_HEADER_ID,
                                                                           direction = Sort.Direction.DESC) PageRequest pageRequest) {
        changeOrderHeader.setTenantId(tenantId);
        Page<ChangeOrderHeader> list = changeOrderHeaderRepository.pageChangeOrder(pageRequest, changeOrderHeader);
        return Results.success(list);
    }

    @ApiOperation(value = "资产状态信息变更单导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ExcelExport(ChangeOrderHeader.class)
    @SuppressWarnings("unchecked")
    public ResponseEntity<List<ChangeOrderHeader>> exportChangeOrder(@PathVariable("organizationId") Long tenantId,
                                                                     ExportParam exportParam,
                                                                     HttpServletResponse httpServletResponse,
                                                                     ChangeOrderHeader changeOrderHeader) {
        changeOrderHeader.setTenantId(tenantId);
        List<ChangeOrderHeader> changeOrderHeaders = changeOrderHeaderMapper.selectChangeOrderHeader(changeOrderHeader);
        return Results.success((List<ChangeOrderHeader>) lovValueHandle.process(null, changeOrderHeaders));
    }

    @ApiOperation(value = "资产状态信息变更单行列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/list")
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    public ResponseEntity<Page<ChangeOrderLine>> listChangeOrderLine(@PathVariable("organizationId") Long tenantId,
                                                                     ChangeOrderHeader changeOrderHeader,
                                                                     @ApiIgnore @SortDefault(value = ChangeOrderLine.FIELD_CHANGE_LINE_ID,
                                                                             direction = Sort.Direction.DESC) PageRequest pageRequest) {
        changeOrderHeader.setTenantId(tenantId);
        return Results.success(changeOrderLineRepository.listChangeOrderLine(changeOrderHeader, pageRequest));
    }

    @ApiOperation(value = "创建资产状态信息变更单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<ChangeOrderHeader> createChangeOrder(@PathVariable("organizationId") Long tenantId,
                                                               @RequestBody ChangeOrderHeader changeOrderHeader) {
        validObject(changeOrderHeader);
        return Results.success(changeOrderHeaderService.createChangeOrder(tenantId, changeOrderHeader));
    }

    @ApiOperation(value = "修改资产状态信息变更单")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<ChangeOrderHeader> updateChangeOrder(@PathVariable("organizationId") Long tenantId,
                                                               @RequestBody ChangeOrderHeader changeOrderHeader) {
        SecurityTokenHelper.validTokenIgnoreInsert(changeOrderHeader);
        validObject(changeOrderHeader);
        return Results.success(changeOrderHeaderService.updateChangeOrder(tenantId, changeOrderHeader));
    }

    @ApiOperation(value = "执行处理")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/execute")
    public ResponseEntity<ChangeOrderLine> executeProcess(@PathVariable("organizationId") Long tenantId,
                                                          @RequestBody ChangeOrderLine changeOrderLine) {
        SecurityTokenHelper.validToken(changeOrderLine);
        validObject(changeOrderLine);
        return Results.success(changeOrderLineService.executeProcess(tenantId, changeOrderLine));
    }

    @ApiOperation(value = "删除行")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/line")
    public ResponseEntity<ChangeOrderLine> removeLine(@PathVariable("organizationId") Long tenantId,
                                                      @RequestBody ChangeOrderLine changeOrderLine) {
        SecurityTokenHelper.validToken(changeOrderLine);
        changeOrderLineService.removeLine(tenantId, changeOrderLine);
        return Results.success();
    }

    @ApiOperation(value = "变更单提交")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/submit")
    public ResponseEntity<ChangeOrderHeader> submitChangeOrder(@PathVariable("organizationId") Long tenantId,
                                                               @RequestBody ChangeOrderHeader changeOrderHeader) {
        //提交前，先保存
        if(Objects.isNull(changeOrderHeader.getChangeHeaderId())){
            this.createChangeOrder(tenantId,changeOrderHeader);
        }
        return Results.success(changeOrderHeaderService.submitChangeOrder(tenantId, changeOrderHeader));
    }

}
