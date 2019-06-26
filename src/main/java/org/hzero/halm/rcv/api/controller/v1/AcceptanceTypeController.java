package org.hzero.halm.rcv.api.controller.v1;

import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.rcv.app.service.AcceptanceTypeService;
import org.hzero.halm.rcv.config.ArcvSwaggerApiConfig;
import org.hzero.halm.rcv.domain.entity.AcceptanceType;
import org.hzero.halm.rcv.domain.repository.AcceptanceTypeRepository;
import org.hzero.halm.rcv.infra.constant.ArcvConstants;
import org.hzero.halm.rcv.infra.mapper.AcceptanceTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.hzero.mybatis.helper.SecurityTokenHelper;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 验收单类型 管理 API
 *
 * @author zhiguang.guo@hand-china.com 2019-04-17 17:55:24
 */
@RestController("acceptanceTypeController.v1")
@RequestMapping("/v1/{organizationId}/acceptance-type")
@Api(tags = ArcvSwaggerApiConfig.ARCV_ACCEPTANCE_TYPE)
public class AcceptanceTypeController extends BaseController {

    @Autowired
    private AcceptanceTypeRepository acceptanceTypeRepository;
    @Autowired
    private AcceptanceTypeService acceptanceTypeService;
    @Autowired
    private AcceptanceTypeMapper acceptanceTypeMapper;
    @Autowired
    private LovValueHandle lovValueHandle;

    @ApiOperation(value = "验收单类型列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping
    public ResponseEntity<Page<AcceptanceType>> acceptanceTypeList(@PathVariable("organizationId") Long tenantId, AcceptanceType acceptanceType, @ApiIgnore @SortDefault(value = AcceptanceType.FIELD_ACCEPTANCE_TYPE_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        acceptanceType.setTenantId(tenantId);
        acceptanceType.setAcceptanceTypeCodeList(acceptanceTypeService.getListByCondition(tenantId, acceptanceType.getCondition(), ArcvConstants.ArcvLovCode.ACCEPTANCE_TYPE));
        acceptanceType.setEnabledFlagList(acceptanceTypeService.getListByCondition(tenantId, acceptanceType.getCondition(), ArcvConstants.ArcvLovCode.ENABLED_FLAG));
        acceptanceType.setAccTypeCodeList(acceptanceTypeService.getListByAccTypeCondition(acceptanceType.getAccTypeCondition()));
        Page<AcceptanceType> list = PageHelper.doPageAndSort(pageRequest, () ->acceptanceTypeMapper.acceptanceTypeList(acceptanceType));
        return Results.success(list);
    }

    @ApiOperation(value = "验收单类型创建/编辑")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<AcceptanceType> acceptanceTypeCreateAndEdit(@PathVariable("organizationId") Long tenantId, @RequestBody AcceptanceType acceptanceType) {
        validObject(acceptanceType);
        SecurityTokenHelper.validTokenIgnoreInsert(acceptanceType);
        acceptanceTypeService.acceptanceTypeCreateAndEdit(tenantId,acceptanceType);
        return Results.success(acceptanceType);
    }

    @ApiOperation(value = "验收单类型明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping("/{acceptanceTypeId}")
    public ResponseEntity<AcceptanceType> acceptanceTypeDetail(@PathVariable("organizationId") Long tenantId, @PathVariable Long acceptanceTypeId) {
        AcceptanceType acceptanceType = acceptanceTypeService.acceptanceTypeDetail(tenantId,acceptanceTypeId);
        return Results.success(acceptanceType);
    }

    @ApiOperation(value = "验收单类型导出")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @ExcelExport(AcceptanceType.class)
    @GetMapping("/export")
    public ResponseEntity<List<AcceptanceType>> acceptanceTypeExport(@PathVariable("organizationId") Long tenantId,
                                                                   AcceptanceType acceptanceType,
                                                                    ExportParam exportParam,
                                                                    HttpServletResponse response) {
        acceptanceType.setTenantId(tenantId);
        List<AcceptanceType> acceptanceTypes = acceptanceTypeMapper.acceptanceTypeList(acceptanceType);
        acceptanceTypes = (List<AcceptanceType>) lovValueHandle.process(null, acceptanceTypes);
        return Results.success(acceptanceTypes);
    }

    @ApiOperation(value = "验收单类型禁用")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/disabled")
    public ResponseEntity<AcceptanceType> acceptanceTypeDisabled(@PathVariable("organizationId") Long tenantId, Long acceptanceTypeId) {
        return Results.success(acceptanceTypeService.changeAssetCatalogEnabledFlag(tenantId,acceptanceTypeId,BaseConstants.Flag.NO));
    }

    @ApiOperation(value = "验收单类型启用")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/enabled")
    public ResponseEntity<AcceptanceType> acceptanceTypeEnabled(@PathVariable("organizationId") Long tenantId,Long acceptanceTypeId) {
        return Results.success(acceptanceTypeService.changeAssetCatalogEnabledFlag(tenantId,acceptanceTypeId,BaseConstants.Flag.YES));
    }
}
