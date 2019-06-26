package org.hzero.halm.afm.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.afm.api.dto.TransactionTypesDTO;
import org.hzero.halm.afm.app.service.TransactionTypesService;
import org.hzero.halm.afm.config.AafmSwaggerApiConfig;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
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
 * 资产事务处理类型 管理 API
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:43:01
 */
@RestController("transactionTypesController.v1")
@RequestMapping("/v1/{organizationId}/transaction-type")
@Api(tags = AafmSwaggerApiConfig.TRANSACTION_TYPES)
public class TransactionTypesController extends BaseController {

    @Autowired
    private TransactionTypesRepository transactionTypesRepository;

    @Autowired
    private TransactionTypesService transactionTypesService;


    @ApiOperation(value = "资产事务处理类型树形列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping("/tree")
    public ResponseEntity<List<TransactionTypesDTO>> treeList(TransactionTypes transactionTypes,
                                                              @PathVariable("organizationId") @ApiParam(value = "租户Id", required = true) Long tenantId) {
        transactionTypes.setTenantId(tenantId);
        return Results.success(transactionTypesService.treeTransactionTypes(transactionTypes, tenantId));
    }

    @ApiOperation(value = "资产事务处理类型列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping("/list")
    public ResponseEntity<Page<TransactionTypes>> list(TransactionTypes transactionTypes,
                                                       @PathVariable("organizationId") @ApiParam(value = "租户Id", required = true) Long tenantId,
                                                       @ApiIgnore @SortDefault(value = TransactionTypes.FIELD_TRANSACTION_TYPE_ID,
                                                               direction = Sort.Direction.DESC) PageRequest pageRequest) {
        transactionTypes.setTenantId(tenantId);
        //Page<TransactionTypes> list = transactionTypesRepository.pageAndSort(pageRequest, transactionTypes);
        Page<TransactionTypes> list = transactionTypesService.pageTransactionTypes(pageRequest, transactionTypes);
        return Results.success(list);
    }

    @ApiOperation(value = "资产事务处理类型明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping("/{transactionTypeId}")
    public ResponseEntity<TransactionTypes> detail(@PathVariable("organizationId") @ApiParam(value = "租户Id", required = true) Long tenantId,
                                                   @PathVariable("transactionTypeId") @ApiParam(value = "事件类型Id", required = true) Long transactionTypeId) {
        TransactionTypes transactionTypes = transactionTypesService.getTransactionTypes(tenantId, transactionTypeId);
        return Results.success(transactionTypes);
    }

    @ApiOperation(value = "创建或修改资产事务处理类型")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<TransactionTypes> saveTransactionTypes(@PathVariable("organizationId") @ApiParam(value = "租户Id", required = true) Long tenantId,
                                                                 @RequestBody TransactionTypes transactionTypes) {
        transactionTypes.setTenantId(tenantId);

        transactionTypes.validateTransactionTypes(tenantId, transactionTypesRepository);

        validObject(transactionTypes);
        SecurityTokenHelper.validTokenIgnoreInsert(transactionTypes);

        return Results.success(transactionTypesService.saveTransactionTypes(tenantId, transactionTypes));
    }

    @ApiOperation(value = "事物类型失效API")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping("/{transactionTypeId}/disabled")
    public ResponseEntity<TransactionTypes> disabledTransactionTypes(@ApiParam(value = "ID", required = true) @PathVariable Long transactionTypeId,
                                                                     @ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
                                                                     @RequestBody TransactionTypes transactionTypes) {
        //验证
        SecurityTokenHelper.validToken(transactionTypes);
        transactionTypes.setTransactionTypeId(transactionTypeId);
        return Results.success(transactionTypesService.disabledTransactionTypes(organizationId, transactionTypes));
    }

    @ApiOperation(value = "事物类型生效API")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping("/{transactionTypeId}/enabled")
    public ResponseEntity<TransactionTypes> enabledTransactionTypes(@ApiParam(value = "ID", required = true) @PathVariable Long transactionTypeId,
                                                                    @ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
                                                                    @RequestBody TransactionTypes transactionTypes) {
        //验证
        SecurityTokenHelper.validToken(transactionTypes);
        transactionTypes.setTransactionTypeId(transactionTypeId);
        return Results.success(transactionTypesService.enabledTransactionTypes(organizationId, transactionTypes));
    }

    /**
     * 属性组导出\勾选导出—事件类型
     *
     * @param organizationId
     * @param exportParam    excel参数
     * @param response       response
     * @return
     */
    @ApiOperation(value = "属性组导出\\勾选导出—属性组")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ExcelExport(TransactionTypesDTO.class)
    public ResponseEntity<List<TransactionTypesDTO>> exportTransactionTypes(@PathVariable("organizationId") Long organizationId,
                                                                            TransactionTypesDTO transactionTypesDTO,
                                                                            ExportParam exportParam,
                                                                            HttpServletResponse response
    ) {
        transactionTypesDTO.setTenantId(organizationId);
        return Results.success(transactionTypesService.exportTransactionTypes(transactionTypesDTO));
    }
}
