package org.hzero.halm.atn.api.controller.v1;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.atn.app.service.TransactionHistoryDetailService;
import org.hzero.halm.atn.config.AatnSwaggerApiConfig;
import org.hzero.halm.atn.domain.entity.TransactionHistory;
import org.hzero.halm.atn.domain.entity.TransactionHistoryDetail;
import org.hzero.halm.atn.domain.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 资产处理历史信息 管理 API
 *
 * @author sen.wang@hand-china.com 2019-03-28 14:03:23
 */
@RestController("transactionHistoryController.v1")
@RequestMapping("/v1/{organizationId}/transaction-historys")
@Api(tags = AatnSwaggerApiConfig.AATN_TRANSACTION_HISTORY)
public class TransactionHistoryController extends BaseController {
    @Autowired
    private TransactionHistoryDetailService detailService;
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @ApiOperation(value = "资产事件履历信息列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/event")
    public ResponseEntity<Page<TransactionHistory>> eventList(@PathVariable("organizationId") Long tenantId,TransactionHistory transactionHistory, @ApiIgnore PageRequest pageRequest) {
        transactionHistory.setTenantId(tenantId);
        Page<TransactionHistory> list = transactionHistoryRepository.pageEnventHistory(pageRequest, transactionHistory);
        return Results.success(list);
    }



    @ApiOperation(value = "资产事件履历字段列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/field")
    public ResponseEntity<List<Map>> fieldList(@PathVariable("organizationId") Long tenantId, TransactionHistoryDetail transactionHistoryDetail) throws NoSuchFieldException {
        transactionHistoryDetail.setTenantId(tenantId);
        return Results.success(detailService.selectFieldHistory(transactionHistoryDetail));
    }

    /**
     * 查询资产字段列表
     * @param tenantId 租户
     * @return 字段数据
     */
    @ApiOperation(value = "资产字段列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/asset/fields")
    public ResponseEntity<Map<String, List>> assetFields(@PathVariable("organizationId") Long tenantId){
        return Results.success(Asset.getClassifyFields());
    }

}
