package org.hzero.halm.afm.api.controller.v1;

import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
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
 * 资产事务处理类型扩展控制字段 管理 API
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:42:58
 */
@RestController("transactionTypeFieldsController.v1")
@RequestMapping("/v1/transaction-type-fieldss")
public class TransactionTypeFieldsController extends BaseController {

    @Autowired
    private TransactionTypeFieldsRepository transactionTypeFieldsRepository;

    @ApiOperation(value = "资产事务处理类型扩展控制字段列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<?> list(TransactionTypeFields transactionTypeFields, @ApiIgnore @SortDefault(value = TransactionTypeFields.FIELD_FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<TransactionTypeFields> list = transactionTypeFieldsRepository.pageAndSort(pageRequest, transactionTypeFields);
        return Results.success(list);
    }

    @ApiOperation(value = "资产事务处理类型扩展控制字段明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{fieldId}")
    public ResponseEntity<?> detail(@PathVariable Long fieldId) {
        TransactionTypeFields transactionTypeFields = transactionTypeFieldsRepository.selectByPrimaryKey(fieldId);
        return Results.success(transactionTypeFields);
    }

    @ApiOperation(value = "创建资产事务处理类型扩展控制字段")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionTypeFields transactionTypeFields) {
        transactionTypeFieldsRepository.insertSelective(transactionTypeFields);
        return Results.success(transactionTypeFields);
    }

    @ApiOperation(value = "修改资产事务处理类型扩展控制字段")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<?> update(@RequestBody TransactionTypeFields transactionTypeFields) {
        SecurityTokenHelper.validToken(transactionTypeFields);
        transactionTypeFieldsRepository.updateByPrimaryKeySelective(transactionTypeFields);
        return Results.success(transactionTypeFields);
    }

    @ApiOperation(value = "删除资产事务处理类型扩展控制字段")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody TransactionTypeFields transactionTypeFields) {
        SecurityTokenHelper.validToken(transactionTypeFields);
        transactionTypeFieldsRepository.deleteByPrimaryKey(transactionTypeFields);
        return Results.success();
    }

}
