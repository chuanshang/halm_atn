package org.hzero.halm.atn.app.service.impl;

import com.google.common.base.CaseFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.AssetRepository;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
import org.hzero.halm.atn.app.service.DisposeOrderLineService;
import org.hzero.halm.atn.app.service.OrderDynamicColumnService;
import org.hzero.halm.atn.app.service.TransactionHistoryService;
import org.hzero.halm.atn.domain.entity.DisposeOrderHeader;
import org.hzero.halm.atn.domain.entity.DisposeOrderLine;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;
import org.hzero.halm.atn.domain.repository.DisposeOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.DisposeOrderLineRepository;
import org.hzero.halm.atn.domain.repository.OrderDynamicColumnRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.constant.AatnDisposeOrderConstans;
import org.hzero.halm.atn.infra.mapper.DisposeOrderHeaderMapper;
import org.hzero.halm.atn.infra.mapper.DisposeOrderLineMapper;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 资产处置单行应用服务默认实现
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 15:48:13
 */
@Service
public class DisposeOrderLineServiceImpl implements DisposeOrderLineService {

    @Autowired
    private TransactionTypeFieldsRepository transactionTypeFieldsRepository;
    @Autowired
    private CommonProcessUtils commonProcessUtils;
    @Autowired
    private DisposeOrderLineRepository disposeOrderLineRepository;
    @Autowired
    private DisposeOrderHeaderRepository disposeOrderHeaderRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private TransactionTypesRepository transactionTypesRepository;
    @Autowired
    private DisposeOrderLineMapper disposeOrderLineMapper;
    @Autowired
    private DisposeOrderHeaderMapper disposeOrderHeaderMapper;
    @Autowired
    private OrderDynamicColumnService orderDynamicColumnService;
    @Autowired
    private OrderDynamicColumnRepository orderDynamicColumnRepository;
    @Autowired
    private TransactionHistoryService historyService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DisposeOrderLine> saveDisposeOrderLines(Long tenantId, DisposeOrderHeader disposeOrderHeader, TransactionTypes transactionTypes, List<DisposeOrderLine> disposeOrderLines) {
        Long disposeHeaderId = disposeOrderHeader.getDisposeHeaderId();
        String disposeNum = disposeOrderHeader.getDisposeNum();
        if (CollectionUtils.isEmpty(disposeOrderLines)) {
            return disposeOrderLines;
        }

        // 获取字段控制规则
        Map<String, String> fieldRulesMap = transactionTypeFieldsRepository.selectByCondition(Condition.builder(TransactionTypeFields.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(TransactionTypeFields.FIELD_TRANSATION_TYPE_ID, transactionTypes.getTransactionTypeId())
                        .andEqualTo(TransactionTypeFields.FIELD_TENANT_ID, tenantId))
                .build())
                .stream().collect(Collectors.toMap(TransactionTypeFields::getFieldColumn, TransactionTypeFields::getFieldType, (k1, k2) -> k1));

        // 校验事务处理类型中定义的字段修改规则
        // commonProcessUtils.validTransactionTypeRules(disposeOrderLines, fieldRulesMap);

        // 分组
        List<DisposeOrderLine> insertLines = disposeOrderLines.stream().filter(line -> Objects.isNull(line.getDisposeLineId())).collect(Collectors.toList());
        List<DisposeOrderLine> updateLines = disposeOrderLines.stream().filter(line -> !Objects.isNull(line.getDisposeLineId())).collect(Collectors.toList());

        //验证三个范围
        this.validateFieldRange(tenantId, disposeOrderLines, transactionTypes);

        if (CollectionUtils.isNotEmpty(insertLines)) {
            // 写入租户ID和头ID
            insertLines.forEach(line -> {
                disposeOrderLineRepository.insertDisposeOrderLine(tenantId, disposeHeaderId, disposeNum, line);
                // 行明细操作
            });
        }

        if (CollectionUtils.isNotEmpty(updateLines)) {
            updateLines.forEach(line -> {
                disposeOrderLineRepository.updateOptional(line,
                        DisposeOrderLine.FIELD_ASSET_ID,
                        DisposeOrderLine.FIELD_PROCESS_STATUS,
                        DisposeOrderLine.FIELD_CURRENT_ASSET_STATUS_ID,
                        DisposeOrderLine.FIELD_DISPOSE_PERSON_ID,
                        DisposeOrderLine.FIELD_REMARK,
                        DisposeOrderLine.FIELD_DISPOSE_TYPE_CODE,
                        DisposeOrderLine.FIELD_TENANT_ID,
                        DisposeOrderLine.FIELD_DESCRIPTION);
            });
        }

        insertLines.addAll(updateLines);
        // 插入/新增动态字段
        for (DisposeOrderLine lineData:insertLines) {
            //动态字段
            List<OrderDynamicColumn> orderDynamicColumnList = lineData.getOrderDynamicColumnList();
            if (CollectionUtils.isNotEmpty(orderDynamicColumnList)) {
                orderDynamicColumnService.saveDynamicColumns(tenantId, lineData.getDisposeLineId(), disposeHeaderId, AatnConstans.TransactionType.DISPOSE, orderDynamicColumnList, fieldRulesMap);
            }
        }
        return insertLines;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeLine(DisposeOrderLine disposeOrderLine) {
        if((AatnDisposeOrderConstans.DisposeLineStatus.NEW).equals(disposeOrderLine.getProcessStatus())){
            //删除行
            disposeOrderLineRepository.deleteByPrimaryKey(disposeOrderLine);
            //查询行所关联的动态字段，也要删除
            List<OrderDynamicColumn> orderDynamicColumns = orderDynamicColumnRepository.selectByCondition(Condition.builder(OrderDynamicColumn.class)
                    .andWhere(Sqls.custom().andEqualTo(OrderDynamicColumn.FIELD_ORDER_LINE_ID, disposeOrderLine.getDisposeLineId())).build());
            // 删除行以及行下所有明细字段
            if (CollectionUtils.isNotEmpty(orderDynamicColumns)) {
                orderDynamicColumnRepository.batchDeleteByPrimaryKey(orderDynamicColumns);
            }
        }else{
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_DISPOSE_LINE_PROCESS_STATUS_INVALID);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DisposeOrderLine processConfirm(Long tenantId, DisposeOrderLine disposeOrderLine) {
        //根据主键查找对象
        DisposeOrderLine dbDisposeOrderLine = disposeOrderLineRepository.selectByPrimaryKey(disposeOrderLine.getDisposeLineId());
        Assert.notNull(dbDisposeOrderLine, BaseConstants.ErrorCode.DATA_NOT_EXISTS);
        //找到对应的资产对象
        Asset asset = assetRepository.selectByPrimaryKey(dbDisposeOrderLine.getAssetId());
        Assert.notNull(asset, BaseConstants.ErrorCode.DATA_NOT_EXISTS);

        //查询动态字段
        List<OrderDynamicColumn> dynamicFieldList =orderDynamicColumnRepository.selectByCondition(
                Condition.builder(OrderDynamicColumn.class)
                        .andWhere(Sqls.custom()
                                .andEqualTo(OrderDynamicColumn.FIELD_ORDER_LINE_ID, dbDisposeOrderLine.getDisposeLineId())
                                .andEqualTo(OrderDynamicColumn.FIELD_ORDER_HEADER_ID, dbDisposeOrderLine.getDisposeHeaderId())
                        ).build());

        if((AatnDisposeOrderConstans.DisposeLineStatus.PENDING_DISPOSAL).equals(disposeOrderLine.getProcessStatus())){
            //设置为已处置
            disposeOrderLine.setProcessStatus(AatnDisposeOrderConstans.DisposeLineStatus.DISPOSED);
            //更新资产状态
            asset.setAssetStatusId(dbDisposeOrderLine.getTargetAssetStatusId());
            if (CollectionUtils.isNotEmpty(dynamicFieldList)){
                for (OrderDynamicColumn dynamicField :dynamicFieldList){
                    String columnName = dynamicField.getCurrentColumnName();
                    Object targetValue=orderDynamicColumnService.columnTypeConversion(dynamicField.getTargetColumnType(),dynamicField.getTargetColumnValue());
                    try {
                        FieldUtils.writeDeclaredField(asset, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName), targetValue, true);
                    }catch (Exception e){
                        throw new CommonException(e);
                    }
                }
            }
            //改变头处理状态为'进行中'
            DisposeOrderHeader header = disposeOrderHeaderRepository.selectByPrimaryKey(dbDisposeOrderLine.getDisposeHeaderId());
            if(!(AatnConstans.ApproveStatus.PROCESSING).equals(header.getProcessStatus())){
                //更改头处理状态
                header.setProcessStatus(AatnConstans.ApproveStatus.PROCESSING);
                disposeOrderHeaderRepository.updateOptional(header,DisposeOrderHeader.FIELD_PROCESS_STATUS);
            }
            assetRepository.updateByPrimaryKeySelective(asset);
            disposeOrderLineRepository.updateOptional(disposeOrderLine,DisposeOrderLine.FIELD_PROCESS_STATUS);
        }else{
            throw new CommonException(AatnDisposeOrderConstans.AatnErrorCode.CURRENT_STATUS_CANNOT_CONFIRMED);
        }
        /**
         * 查询相同头id的行，判断每个行的状态ProcessStatus，当所有行状态都为'已处置'时，
         * 资产事务处理单的状态变为”已完成“，同时该资产事务处理单下的所有资产事务处理行的状态变为”已完成”
         */
        List<DisposeOrderLine> lineList = disposeOrderLineMapper.selectLineList(disposeOrderLine.getDisposeHeaderId(),tenantId);
        boolean flag = true;
        for(DisposeOrderLine line:lineList){
            if(!(AatnDisposeOrderConstans.DisposeLineStatus.DISPOSED).equals(line.getProcessStatus())){
                flag = false;
                break;
            }
        }
        if(flag){
            //该资产事务处理单下的所有资产事务处理行的状态变为”已完成”
            for(DisposeOrderLine line:lineList){
                line.setProcessStatus(AatnDisposeOrderConstans.DisposeLineStatus.COMPLETED);
                disposeOrderLineRepository.updateOptional(line,DisposeOrderLine.FIELD_PROCESS_STATUS);
            }
            DisposeOrderHeader disposeOrderHeader= new DisposeOrderHeader();
            disposeOrderHeader.setTenantId(tenantId);
            disposeOrderHeader.setDisposeHeaderId(disposeOrderLine.getDisposeHeaderId());
            DisposeOrderHeader getHead = disposeOrderHeaderMapper.getLineById(disposeOrderHeader);
            //资产事务处理单的状态变为”已完成“
            getHead.setProcessStatus(AatnConstans.ApproveStatus.COMPLETED);
            disposeOrderHeaderRepository.updateOptional(getHead,DisposeOrderHeader.FIELD_PROCESS_STATUS);
        }
        // 记录状态变更日志
        historyService.addTransactionHistory(disposeOrderLine, disposeOrderLineRepository, disposeOrderHeaderRepository);
        return disposeOrderLine;
    }

    private void validateFieldRange(Long tenantId, List<DisposeOrderLine> disposeOrderLines, TransactionTypes transactionType) {

        if (CollectionUtils.isNotEmpty(disposeOrderLines)) {

            //判断当前资产状态、组织、专业是否在范围内
            disposeOrderLines.forEach(line -> {
                Asset getAsset = assetRepository.selectByPrimaryKey(line.getAssetId());

                List<String> organizationRange = this.getJsonArray(transactionType.getOrganizationScope());

                List<String> specialtyRange = this.getJsonArray(transactionType.getSpecialtyScope());

                List<String> statusRange = this.getJsonArray(transactionType.getStatusScope());

                if (!Objects.isNull(getAsset.getAssetStatusId()) && (statusRange.size() != 0) && !isContainValue(String.valueOf(getAsset.getAssetStatusId()), statusRange)) {
                    throw new CommonException(AatnConstans.AatnErrorCode.AATN_DISPOSE_CURRENT_ASSET_STATUS_OUT_OF_RANGE, getAsset.getAssetStatusId(),statusRange);
                }
                if (!Objects.isNull(getAsset.getOwningOrgId()) && (organizationRange.size() != 0) && !isContainValue(String.valueOf(getAsset.getOwningOrgId()), organizationRange)) {
                    throw new CommonException(AatnConstans.AatnErrorCode.AATN_OWNING_ORG_OUT_OF_RANGE, getAsset.getOwningOrgId(), organizationRange);
                }
                if (!Objects.isNull(getAsset.getAssetSpecialtyId()) && (specialtyRange.size() != 0) && !isContainValue(String.valueOf(getAsset.getAssetSpecialtyId()), specialtyRange)) {
                    throw new CommonException(AatnConstans.AatnErrorCode.AATN_OWNING_MAJOR_OUT_OF_RANGE, getAsset.getAssetSpecialtyId(), specialtyRange);
                }
            });
        }
    }

    private List<String> getJsonArray(String jsonStr) {
        if (Objects.nonNull(jsonStr)) {
            return (List<String>) JSONArray.parse(jsonStr);
        }
        return new ArrayList<>();
    }

    private Boolean isContainValue(String value, List<String> list) {
        if (list.size() != 0) {
            for (String v : list) {
                if (StringUtils.equals(v, value)) {
                    return true;
                }
            }
            return false;
        }else {
            // 如果事务处理类型里面没有设置范围限制，那么就不做效验了。
            return true;
        }
    }
}
