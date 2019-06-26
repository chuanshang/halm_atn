package org.hzero.halm.atn.app.service.impl;

import com.alibaba.fastjson.JSONArray;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.domain.AuditDomain;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.halm.atn.app.service.TransactionHistoryDetailService;
import org.hzero.halm.atn.app.service.TransactionHistoryService;
import org.hzero.halm.atn.domain.entity.TransactionHistory;
import org.hzero.halm.atn.domain.repository.TransactionHistoryRepository;
import org.hzero.halm.atn.infra.constant.TransactionLine;
import org.hzero.halm.atn.infra.constant.TransactionLineEnum;
import org.hzero.halm.atn.infra.util.TransactionHistoryUtils;
import org.hzero.mybatis.base.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 资产处理历史信息应用服务默认实现
 *
 * @author sen.wang@hand-china.com 2019-03-28 14:03:23
 */
@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionHistoryServiceImpl.class);
    @Autowired
    TransactionHistoryRepository historyRepository;
    @Autowired
    TransactionHistoryDetailService detailService;

    @Autowired
    LovAdapter adapter;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void addTransactionHistory(TransactionLine transactionLine, BaseRepository lineRepository, BaseRepository headerRepository) {

        String headerIdName = TransactionLineEnum.getTheLineEnum(transactionLine.getClass()).getHeaderIdFieldName();
        String lineIdName = TransactionLineEnum.getTheLineEnum(transactionLine.getClass()).getLineIdFieldName();
        try {
            Long lineId = (Long) FieldUtils.readDeclaredField(transactionLine, lineIdName, true);
            Long headerId = (Long) FieldUtils.readDeclaredField(transactionLine, headerIdName, true);
            Long assetId = (Long) FieldUtils.readDeclaredField(transactionLine, TransactionHistoryService.FIELD_ASSET_ID, true);
            // 查询头数据
            Object headerObj = headerRepository.selectByPrimaryKey(headerId);
            Long transactionTypeId = (Long) FieldUtils.readDeclaredField(headerObj, TransactionHistoryService.FIELD_TRANSACTION_TYPE_ID, true);
            String processStatus = (String) FieldUtils.readDeclaredField(transactionLine, TransactionHistoryService.FIELD_PROCESS_STATUS, true);
            Long tenantId = (Long) FieldUtils.readDeclaredField(transactionLine, TransactionHistoryService.FIELD_TENANT_ID, true);
            if (lineId == null || headerId == null || assetId == null || transactionTypeId == null) {
                throw new CommonException("该事务处理行数据不完整");
            }

            // 首先判断此资产对应的事务处理的历史信息，有没有时间记录。如果没有，查询事务处理行的创建信息为初始时间记录。
            TransactionHistory history = new TransactionHistory();
            history.setAssetId(assetId);
            history.setTransactionTypeId(transactionTypeId);
            history.setTransactionLineId(lineId);
            history.setTenantId(tenantId);
            history.setTransactionHeaderId(headerId);

            List<TransactionHistory> list = historyRepository.select(history);
            if (CollectionUtils.isEmpty(list)) {
                // 获取事务处理行的创建时间，设置为历史信息的起始信息
                Object lineObj = lineRepository.selectByPrimaryKey(lineId);
                Date createDate = (Date) FieldUtils.readField(lineObj, AuditDomain.FIELD_CREATION_DATE, true);
                // 设置事务处理的默认状态是 NEW。
                history.setProcessStatusRecord(TransactionHistoryUtils.getStringJson(
                        JSONArray.toJSONString(Collections.singletonList(TransactionHistoryService.DEFAULT_STATUS)),
                        processStatus));
                // 设置事务处理的默认时间是，事务创建时间。
                history.setProcessTimeRecord(TransactionHistoryUtils.getStringJson(
                        JSONArray.toJSONString(Collections.singletonList(createDate.getTime())),
                        String.valueOf(System.currentTimeMillis())));
                historyRepository.insert(history);
                // 添加明细记录
                detailService.addHistoryDetail(transactionLine, history);
            } else if (list.size() == 1) {
                TransactionHistory selectHistory = list.get(0);
                selectHistory.setProcessStatusRecord(TransactionHistoryUtils.getStringJson(selectHistory.getProcessStatusRecord(), processStatus));
                selectHistory.setProcessTimeRecord(TransactionHistoryUtils.getStringJson(selectHistory.getProcessTimeRecord(), String.valueOf(System.currentTimeMillis())));
                historyRepository.updateOptional(selectHistory, TransactionHistory.FIELD_PROCESS_STATUS_RECORD, TransactionHistory.FIELD_PROCESS_TIME_RECORD);
                // 添加明细记录
                detailService.addHistoryDetail(transactionLine, selectHistory);
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
        }
    }


}







