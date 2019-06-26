package org.hzero.halm.atn.app.service;

import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;

import java.util.List;
import java.util.Map;

/**
 * 单据动态字段应用服务
 *
 * @author like.zhang@hand-china.com 2019-03-26 11:49:44
 */
public interface OrderDynamicColumnService {

    /**
     * 新增/修改行明细
     *
     * @param tenantId       租户ID
     * @param orderLineId    单据行ID
     * @param orderHeaderId  单据头ID
     * @param orderTypeCode  单据类型 值集AAFM.ASSET_TRANSACTION_TYPE
     * @param dynamicColumns 明细列表数据
     * @param fieldRulesMap  规则map (key, value) -> (字段名，规则)
     * @return 操作结果
     */
    List<OrderDynamicColumn> saveDynamicColumns(Long tenantId, Long orderLineId, Long orderHeaderId, String orderTypeCode, List<OrderDynamicColumn> dynamicColumns, Map<String, String> fieldRulesMap);

    /**
     * 将String类型的值根据targetColumnType转换成其他类型
     * @param targetColumnType 目标字段类型
     * @param targetColumnValue 目标字段的值
     * @return 放回的数据
     */
    Object columnTypeConversion(String targetColumnType,String targetColumnValue);
}
