package org.hzero.halm.atn.api.controller.v1;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.base.BaseController;
import org.hzero.core.util.Results;
import org.hzero.halm.atn.config.AatnSwaggerApiConfig;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;
import org.hzero.halm.atn.domain.repository.OrderDynamicColumnRepository;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 单据动态字段查询 管理 API
 *
 * @author like.zhang@hand-china.com 2019-03-26 10:20:34
 */
@RestController("orderDynamicColumnController.v1")
@RequestMapping("/v1/{organizationId}/dynamic-column")
@Api(tags = AatnSwaggerApiConfig.AATN_DYNAMIC_COLUMN)
public class OrderDynamicColumnController extends BaseController {

    @Autowired
    private OrderDynamicColumnRepository orderDynamicColumnRepository;

    @ApiOperation(value = "单据动态字段查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<List<OrderDynamicColumn>> listDynamicColumns(@PathVariable("organizationId") Long tenantId,
                                                                       @RequestParam("orderLineId") Long orderLineId,
                                                                       @RequestParam("orderHeaderId") Long orderHeaderId,
                                                                       @RequestParam("orderTypeCode") String orderTypeCode) {
        return Results.success(orderDynamicColumnRepository.selectByCondition(Condition.builder(OrderDynamicColumn.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(OrderDynamicColumn.FIELD_TENANT_ID, tenantId)
                        .andEqualTo(OrderDynamicColumn.FIELD_ORDER_LINE_ID, orderLineId)
                        .andEqualTo(OrderDynamicColumn.FIELD_ORDER_HEADER_ID, orderHeaderId)
                        .andEqualTo(OrderDynamicColumn.FIELD_ORDER_TYPE_CODE, orderTypeCode))
                .build()));
    }


}
