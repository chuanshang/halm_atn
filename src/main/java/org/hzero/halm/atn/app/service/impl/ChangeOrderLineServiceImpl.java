package org.hzero.halm.atn.app.service.impl;

import com.alibaba.fastjson.JSON;
import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.AssetRepository;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
import org.hzero.halm.atn.app.service.OrderDynamicColumnService;
import org.hzero.halm.atn.app.service.ChangeOrderLineService;
import org.hzero.halm.atn.app.service.TransactionHistoryService;
import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;
import org.hzero.halm.atn.domain.entity.ChangeOrderLine;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;
import org.hzero.halm.atn.domain.repository.OrderDynamicColumnRepository;
import org.hzero.halm.atn.domain.repository.ChangeOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.ChangeOrderLineRepository;
import org.hzero.halm.atn.infra.constant.AatnChangeOrderConstantControlFields;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资产信息变更单行应用服务默认实现
 *
 * @author like.zhang@hand-china.com 2019-03-26 11:49:44
 */
@Service
public class ChangeOrderLineServiceImpl implements ChangeOrderLineService {
    @Autowired
    private TransactionTypeFieldsRepository transactionTypeFieldsRepository;
    @Autowired
    private CommonProcessUtils commonProcessUtils;
    @Autowired
    private ChangeOrderLineRepository changeOrderLineRepository;
    @Autowired
    private OrderDynamicColumnService orderDynamicColumnService;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private OrderDynamicColumnRepository orderDynamicColumnRepository;
    @Autowired
    private ChangeOrderHeaderRepository changeOrderHeaderRepository;
    @Autowired
    private ChangeOrderHeaderRepository headerRepository;

    @Autowired
    private TransactionHistoryService historyService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChangeOrderLine executeProcess(Long tenantId, ChangeOrderLine changeOrderLine) {

        ChangeOrderHeader headerEntity = changeOrderHeaderRepository.selectByPrimaryKey(changeOrderLine.getChangeHeaderId());
        Assert.notNull(headerEntity, BaseConstants.ErrorCode.DATA_INVALID);

        // 数据库中该变更单的所有行
        Map<Long, ChangeOrderLine> lineMap = changeOrderLineRepository.selectByCondition(Condition.builder(ChangeOrderLine.class)
                .andWhere(Sqls.custom().andEqualTo(ChangeOrderLine.FIELD_CHANGE_HEADER_ID, changeOrderLine.getChangeHeaderId()))
                .build())
                .stream().collect(Collectors.toMap(ChangeOrderLine::getChangeLineId, a -> a, (k1, k2) -> k1));
        // 当前行在数据库中的内容
        ChangeOrderLine lineEntity = lineMap.get(changeOrderLine.getChangeLineId());
        Assert.notNull(lineEntity, BaseConstants.ErrorCode.DATA_NOT_EXISTS);
        // 数据库中该行字段明细
        List<OrderDynamicColumn> orderDynamicColumns = orderDynamicColumnRepository.selectByCondition(Condition.builder(OrderDynamicColumn.class)
                .andWhere(Sqls.custom().andEqualTo(OrderDynamicColumn.FIELD_ORDER_LINE_ID, changeOrderLine.getChangeLineId())).build());

        Asset asset = assetRepository.selectByPrimaryKey(changeOrderLine.getAssetId());
        Assert.notNull(asset, BaseConstants.ErrorCode.DATA_INVALID);

        // 资产事务处理行状态变为“已处理”
        changeOrderLine.setProcessStatus(AatnConstans.ChangeOrderLineStatus.PROCESSED);
        assetRepository.updateAssetByChangeOrder(lineEntity, orderDynamicColumns, asset);

        // 用当前变更行替换lineMap中的对应行
        lineMap.put(changeOrderLine.getChangeLineId(), changeOrderLine);
        // 统计当前变更所有的行中不是“已处理”状态的行数量
        long unprocessedLine = lineMap.values().stream().filter(line -> !StringUtils.equals(line.getProcessStatus(), AatnConstans.ChangeOrderLineStatus.PROCESSED)).count();
        if (unprocessedLine == 0) {
            // 都为已处理，则将头和行的状态都变更为已完成
            lineMap.values().forEach(line -> line.setProcessStatus(AatnConstans.ChangeOrderLineStatus.COMPLETED));
            headerEntity.setProcessStatus(AatnConstans.ApproveStatus.COMPLETED);
            List<ChangeOrderLine> updateLines = new ArrayList<>(lineMap.values());
            changeOrderLineRepository.batchUpdateOptional(updateLines, ChangeOrderLine.FIELD_PROCESS_STATUS);
            changeOrderHeaderRepository.updateOptional(headerEntity, ChangeOrderHeader.FIELD_PROCESS_STATUS);
        } else {
            headerEntity.setProcessStatus(AatnConstans.ApproveStatus.PROCESSING);
            // 只对当前行进行更新状态为 “已处理”
            changeOrderLineRepository.updateOptional(changeOrderLine, ChangeOrderLine.FIELD_PROCESS_STATUS);
            changeOrderHeaderRepository.updateOptional(headerEntity, ChangeOrderHeader.FIELD_PROCESS_STATUS);
        }
        // 记录状态变更日志
        historyService.addTransactionHistory(changeOrderLine, changeOrderLineRepository, headerRepository);
        return changeOrderLine;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ChangeOrderLine> saveChangeOrderLine(Long tenantId, ChangeOrderHeader changeOrderHeader, TransactionTypes transactionTypes, List<ChangeOrderLine> changeOrderLines) {
        Long changeHeaderId = changeOrderHeader.getChangeHeaderId();
        String changeNum = changeOrderHeader.getChangeNum();
        if (CollectionUtils.isEmpty(changeOrderLines)) {
            return changeOrderLines;
        }

        // 资产状态变更范围
        List<String> statusScope = JSON.parseArray(transactionTypes.getStatusScope(), String.class);
        // 所属组织变更范围
        List<String> orgScope = JSON.parseArray(transactionTypes.getOrganizationScope(), String.class);
        // 专业资产变更范围
        List<String> specialScope = JSON.parseArray(transactionTypes.getSpecialtyScope(), String.class);
        // 获取字段控制规则
        Map<String, String> fieldRulesMap = transactionTypeFieldsRepository.selectByCondition(Condition.builder(TransactionTypeFields.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(TransactionTypeFields.FIELD_TRANSATION_TYPE_ID, transactionTypes.getTransactionTypeId())
                        .andEqualTo(TransactionTypeFields.FIELD_TENANT_ID, tenantId))
                .build())
                .stream().collect(Collectors.toMap(TransactionTypeFields::getFieldColumn, TransactionTypeFields::getFieldType, (k1, k2) -> k1));
        // 校验事务处理类型中定义的字段修改规则
        commonProcessUtils.validTransactionTypeRules(changeOrderLines, fieldRulesMap, AatnChangeOrderConstantControlFields.values());

        // 分组
        List<ChangeOrderLine> insertLines = changeOrderLines.stream().filter(line -> Objects.isNull(line.getChangeLineId())).collect(Collectors.toList());
        List<ChangeOrderLine> updateLines = changeOrderLines.stream().filter(line -> !Objects.isNull(line.getChangeLineId())).collect(Collectors.toList());

        changeOrderLines.forEach(line -> {
            // 目标资产状态是否在选择范围
            if (!Objects.isNull(line.getCurrentAssetStatusId()) && CollectionUtils.isNotEmpty(statusScope) && !statusScope.contains(line.getCurrentAssetStatusId().toString())) {
                throw new CommonException(AatnConstans.AatnErrorCode.AATN_ASSET_STATUS_OUT_OF_RANGE, line.getCurrentAssetStatusId(), transactionTypes.getStatusScope());
            }
        });

        // 数据库中行已选资产
        Set<Long> entitiesAssetIdSet = changeOrderLineRepository.selectByCondition(Condition.builder(ChangeOrderLine.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(ChangeOrderLine.FIELD_CHANGE_HEADER_ID, changeHeaderId))
                .build())
                .stream().map(ChangeOrderLine::getAssetId).collect(Collectors.toSet());

        if (CollectionUtils.isNotEmpty(insertLines)) {
            Set<Long> insertAssetIdSet = insertLines.stream().map(ChangeOrderLine::getAssetId).collect(Collectors.toSet());

            // 校验资产是否重复选择
            CommonProcessUtils.validAsset(insertAssetIdSet, entitiesAssetIdSet, insertLines.size());
            // 写入租户ID和头ID
            insertLines.forEach(line -> {
                changeOrderLineRepository.insertChangeOrderLine(tenantId, changeHeaderId, changeNum, line);
                // 行明细操作
                OrderDynamicColumn.validScope(orgScope, specialScope, line.getOrderDynamicColumnList());
                orderDynamicColumnService.saveDynamicColumns(tenantId, line.getChangeLineId(), changeHeaderId, AatnConstans.TransactionType.UPDATE, line.getOrderDynamicColumnList(), fieldRulesMap);
            });

            entitiesAssetIdSet.addAll(insertAssetIdSet);
        }

        if (CollectionUtils.isNotEmpty(updateLines)) {
            updateLines.forEach(line -> {
                changeOrderLineRepository.updateOptional(line,
                        ChangeOrderLine.FIELD_TARGET_ASSET_STATUS_ID,
                        ChangeOrderLine.FIELD_TARGET_OWNING_PERSON_ID,
                        ChangeOrderLine.FIELD_TARGET_LOCATION_ID,
                        ChangeOrderLine.FIELD_TARGET_USING_PERSON_ID,
                        ChangeOrderLine.FIELD_DESCRIPTION);
                OrderDynamicColumn.validScope(orgScope, specialScope, line.getOrderDynamicColumnList());
                orderDynamicColumnService.saveDynamicColumns(tenantId, line.getChangeLineId(), changeHeaderId, AatnConstans.TransactionType.UPDATE, line.getOrderDynamicColumnList(), fieldRulesMap);
            });
        }

        insertLines.addAll(updateLines);
        return insertLines;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeLine(Long tenantId, ChangeOrderLine changeOrderLine) {

        ChangeOrderHeader headerEntity = changeOrderHeaderRepository.selectByPrimaryKey(changeOrderLine.getChangeHeaderId());
        Assert.notNull(headerEntity, BaseConstants.ErrorCode.DATA_INVALID);
        if (!StringUtils.equals(headerEntity.getProcessStatus(), AatnConstans.ApproveStatus.NEW)) {
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_PROCESS_STATUS_INVALID);
        }

        List<OrderDynamicColumn> orderDynamicColumns = orderDynamicColumnRepository.selectByCondition(Condition.builder(OrderDynamicColumn.class)
                .andWhere(Sqls.custom().andEqualTo(OrderDynamicColumn.FIELD_ORDER_LINE_ID, changeOrderLine.getChangeLineId())).build());

        // 删除行以及行下所有明细字段
        if (CollectionUtils.isNotEmpty(orderDynamicColumns)) {
            orderDynamicColumnRepository.batchDeleteByPrimaryKey(orderDynamicColumns);
        }
        changeOrderLineRepository.deleteByPrimaryKey(changeOrderLine);
    }
}
