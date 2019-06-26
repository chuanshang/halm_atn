package org.hzero.halm.atn.app.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.atn.app.service.TransactionHistoryDetailService;
import org.hzero.halm.atn.domain.entity.*;
import org.hzero.halm.atn.infra.constant.TransactionLine;
import org.hzero.halm.atn.infra.mapper.TransactionHistoryDetailMapper;
import org.hzero.halm.atn.infra.util.TransactionHistoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资产字段历史明细应用服务默认实现
 *
 * @author sen.wang@hand-china.com 2019-03-30 14:14:11
 */
@Service
public class TransactionHistoryDetailServiceImpl implements TransactionHistoryDetailService {
    @Autowired
    TransactionHistoryDetailMapper detailMapper;

    @Override
    public void addHistoryDetail(TransactionLine transactionLine, TransactionHistory headerHistory) throws IllegalAccessException {

        if (transactionLine instanceof HandoverOrderLine) {
            // 移交归还单存在明细表
            HandoverOrderLine handoverOrderLine = (HandoverOrderLine) transactionLine;
            List<HandoverOrderDetail> handoverOrderDetails = handoverOrderLine.getDetailList();
            if (CollectionUtils.isNotEmpty(handoverOrderDetails)) {
                for (HandoverOrderDetail orderDetail : handoverOrderDetails) {
                    this.addObjectHistoryDetail(orderDetail, headerHistory);
                    this.addDynamicColumnHistory(orderDetail.getDynamicColumnList(), headerHistory);
                }
            }
        } else {
            List<OrderDynamicColumn> orderDynamicColumns = transactionLine.getOrderDynamicColumnList();
            this.addObjectHistoryDetail(transactionLine, headerHistory);
            this.addDynamicColumnHistory(orderDynamicColumns, headerHistory);
        }

    }

    @Override
    public List<Map> selectFieldHistory(TransactionHistoryDetail historyDetail) throws NoSuchFieldException {
        List<TransactionHistoryDetail> detailList = detailMapper.selectFieldHistory(historyDetail);
        if (CollectionUtils.isEmpty(historyDetail.getFieldSet()) || CollectionUtils.isEmpty(detailList)) {
            return new ArrayList<>();
        }
        Map<String, List<TransactionHistoryDetail>> fieldMap = detailList.stream().collect(Collectors.groupingBy(TransactionHistoryDetail::getFieldName));
        List<Map> list = new ArrayList<>();
        for (Map.Entry<String, List<TransactionHistoryDetail>> entry : fieldMap.entrySet()) {
            Map<String, Object> map = new HashMap<>(3);
            map.put(TransactionHistory.EVENT_MAP_KEY_FIELD_MEANING, Asset.getFieldMeaning(entry.getKey()));
            map.put(TransactionHistory.EVENT_MAP_KEY_FIELD, entry.getKey());
            map.put(TransactionHistory.EVENT_MAP_KEY_LIST, entry.getValue());
            list.add(map);
        }
        return list;
    }

    /**
     * 事务信息动态字段信息数据的插入
     *
     * @param orderDynamicColumns orderDynamicColumns
     * @param headerHistory       headerHistory
     */
    private void addDynamicColumnHistory(List<OrderDynamicColumn> orderDynamicColumns, TransactionHistory headerHistory) {
        if (CollectionUtils.isEmpty(orderDynamicColumns)) {
            return;
        }
        Map<String, List> fieldValuesMap = new HashMap<>();
        for (OrderDynamicColumn orderDynamicColumn : orderDynamicColumns) {
            List<String> valueList = new ArrayList<>(4);
            valueList.add(orderDynamicColumn.getCurrentColumnValue());
            valueList.add(orderDynamicColumn.getCurrentColumnDesc());
            valueList.add(orderDynamicColumn.getTargetColumnValue());
            valueList.add(orderDynamicColumn.getTargetColumnDesc());
            fieldValuesMap.put(orderDynamicColumn.getCurrentTableName(), valueList);
        }
        this.insertHistoryRecord(fieldValuesMap, headerHistory);
    }

    /**
     * 插入对应对象的字段历史记录
     *
     * @param transactionObject 可以是事务行对象，也可以是事务明细对象
     * @param headerHistory     headerHistory
     */
    private void addObjectHistoryDetail(Object transactionObject, TransactionHistory headerHistory) throws IllegalAccessException {
        // 获取发生改变的字段名称
        Map<String, List<String>> fieldsMap = TransactionHistoryUtils.getChangedFieldsMap(transactionObject.getClass());
        // 获取发生改变字段的值
        Map<String, List> fieldValuesMap = TransactionHistoryUtils.getFieldsValue(transactionObject, fieldsMap);
        this.insertHistoryRecord(fieldValuesMap, headerHistory);
    }

    /**
     * 去historyDetail表，查询该资产id下，field_name=该字段 的时候，是否有数据。
     * 》 如果没有数据，则将current字段值和target字段值都插入 detail表，否则只插入target字段。
     *
     * @param fieldValuesMap fieldValuesMap
     * @param headerHistory  headerHistory
     */
    private void insertHistoryRecord(Map<String, List> fieldValuesMap, TransactionHistory headerHistory) {
        TransactionHistoryDetail detail = new TransactionHistoryDetail();
        detail.setAssetId(headerHistory.getAssetId());
        detail.setTenantId(headerHistory.getTenantId());
        Set<String> keySet = fieldValuesMap.keySet();
        keySet.forEach(key -> {
            detail.setFieldName(key);
            TransactionHistoryDetail insertDetail = new TransactionHistoryDetail();
            insertDetail.setTenantId(headerHistory.getTenantId());
            insertDetail.setOccurTime(new Date());
            insertDetail.setFieldName(key);
            insertDetail.setTransactionHistoryId(headerHistory.getTransactionHistoryId());
            // 如果该资产下的该字段，已经有了对应的字段数据，那么直插入target字段数据。否则把current和target都插入。
            String latestValue = detailMapper.selectLatestValue(detail);
            if (latestValue == null) {
                // 插入current字段
                insertDetail.setFieldValue((String.valueOf(fieldValuesMap.get(key).get(0))));
                // 如果meaning值为null，则赋值为value值。
                insertDetail.setFieldMeaning(
                        Optional.of((String.valueOf(fieldValuesMap.get(key).get(1))))
                                .orElse(String.valueOf(fieldValuesMap.get(key).get(0))));
                detailMapper.insert(insertDetail);
            }
            // 如果要target的数据和表里记录的最新的数据相同，那么就不插入。
            if (latestValue == null || !Objects.equals(latestValue, String.valueOf(fieldValuesMap.get(key).get(2)))) {
                // 插入target字段
                insertDetail.setHistoryDetailId(null);
                insertDetail.setObjectVersionNumber(null);
                insertDetail.setFieldValue((String.valueOf(fieldValuesMap.get(key).get(2))));
                insertDetail.setFieldMeaning(
                        Optional.of((String.valueOf(fieldValuesMap.get(key).get(3))))
                                .orElse(String.valueOf(fieldValuesMap.get(key).get(2))));
                detailMapper.insert(insertDetail);
            }


        });
    }


}
