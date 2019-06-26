package org.hzero.halm.atn.infra.repository.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.boot.platform.code.constant.CodeConstants;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.AssetRepository;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
import org.hzero.halm.atn.app.service.HandoverOrderDetailService;
import org.hzero.halm.atn.app.service.HandoverOrderLineService;
import org.hzero.halm.atn.app.service.OrderDynamicColumnService;
import org.hzero.halm.atn.domain.entity.HandoverOrderDetail;
import org.hzero.halm.atn.domain.entity.HandoverOrderHeader;
import org.hzero.halm.atn.domain.entity.HandoverOrderLine;
import org.hzero.halm.atn.domain.repository.HandoverOrderDetailRepository;
import org.hzero.halm.atn.domain.repository.HandoverOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.HandoverOrderLineRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.mapper.HandoverOrderHeaderMapper;
import org.hzero.halm.atn.infra.mapper.HandoverOrderLineMapper;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.halm.atn.infra.util.TransactionHistoryUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 资产移交归还单头 资源库实现
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:03
 */
@Component
public class HandoverOrderHeaderRepositoryImpl extends BaseRepositoryImpl<HandoverOrderHeader> implements HandoverOrderHeaderRepository {

    @Autowired
    private HandoverOrderLineService lineService;
    @Autowired
    HandoverOrderHeaderMapper headerMapper;
    @Autowired
    HandoverOrderLineMapper lineMapper;
    @Autowired
    HandoverOrderLineRepository lineRepository;
    @Autowired
    HandoverOrderDetailRepository detailRepository;
    @Autowired
    private TransactionTypesRepository transactionTypesRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    HandoverOrderDetailService detailService;
    @Autowired
    private LovValueHandle lovValueHandle;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private TransactionTypeFieldsRepository typeFieldsRepository;
    @Autowired
    private OrderDynamicColumnService dynamicColumnService;
    @Autowired
    private CommonProcessUtils commonProcessUtils;

    @Override
    @ProcessLovValue
    public HandoverOrderHeader selectDetail(Long id, Long tenantId) {
        HandoverOrderHeader header = headerMapper.selectDetailById(id);
        lovValueHandle.process(null, header);
        if (Objects.isNull(header)) {
            return null;
        }
        List<HandoverOrderLine> lineList = lineMapper.selectLineList(id, tenantId);
        lovValueHandle.process(null, lineList);
        if (CollectionUtils.isNotEmpty(lineList)) {
            lineList.forEach(line -> {
                List<HandoverOrderDetail> detailList = detailService.selectDetailList(line);
                line.setDetailList(detailList);
            });
        }
        header.setLineList(lineList);
        return header;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HandoverOrderHeader saveHandoverOrder(HandoverOrderHeader header, Long tenantId) throws IllegalAccessException {
        // 根据资产事务类型确定编码
        TransactionTypes typeEntity = transactionTypesRepository.selectByPrimaryKey(header.getTransactionTypeId());
        Assert.notNull(typeEntity, BaseConstants.ErrorCode.DATA_INVALID);
        Map<String, String> fieldRulesMap = typeFieldsRepository.getFieldType(header.getTenantId(), header.getHandoverHeaderId());
        // 获取动态变化列的规则信息
        Map<String, String> dynFieldRulesMap = this.getDynamicFieldRulesMap(fieldRulesMap,
                TransactionHistoryUtils.getChangedFieldsMap(HandoverOrderDetail.class).keySet());
        if (header.getHandoverHeaderId() == null) {
            // 若是创建操作，则设置编码值。
            if (StringUtils.isNotEmpty(typeEntity.getCodeRule())) {
                String ruleCode = codeRuleBuilder.generateCode(tenantId, typeEntity.getCodeRule(), CodeConstants.CodeRuleLevelCode.GLOBAL, CodeConstants.CodeRuleLevelCode.GLOBAL, null);
                header.setHandoverNum(ruleCode);
            } else {
                Assert.notNull(header.getHandoverNum(), BaseConstants.ErrorCode.DATA_INVALID);
            }
            // 根据当前登陆用户ID获取员工ID，设置默认负责人
            if (Objects.isNull(header.getPrincipalPersonId())) {
                header.setPrincipalPersonId(commonProcessUtils.getDefaultEmployeeId());
            }
            header.setTenantId(tenantId);
            this.insert(header);
        } else {
            this.updateOptional(header,
                    HandoverOrderHeader.FIELD_TRANSACTION_TYPE_ID,
                    HandoverOrderHeader.FIELD_PROCESS_STATUS,
                    HandoverOrderHeader.FIELD_PRINCIPAL_PERSON_ID,
                    HandoverOrderHeader.FIELD_PLAN_START_DATE,
                    HandoverOrderHeader.FIELD_PLAN_END_DATE,
                    HandoverOrderHeader.FIELD_DESCRIPTION);
        }

        List<HandoverOrderLine> lineList = header.getLineList();
        if (CollectionUtils.isNotEmpty(lineList)) {
            for (HandoverOrderLine line : lineList) {
                //验证选择资产是否满足事务类型的设置范围。
                Asset asset = assetRepository.selectByPrimaryKey(line.getAssetId());
                lineService.validateFieldValueInRange(asset, line.getDetailList(), typeEntity);

                line.setHandoverHeaderId(header.getHandoverHeaderId());
                lineRepository.saveLine(line, tenantId);
                List<HandoverOrderDetail> detailList = line.getDetailList();
                for (HandoverOrderDetail detail : detailList) {
                    // 设置基本状态
                    detail.setHandoverLineId(line.getHandoverLineId());
                    detail.setCurrentAssetStatusId(asset.getAssetStatusId());
                    detail.setCurrentOwningPersonId(asset.getOwningPersonId());
                    detail.setCurrentUsingPersonId(asset.getUserPersonId());
                    Assert.notNull(typeEntity.getTargetAssetStatusId(), AatnConstans.AatnErrorCode.TRANSACTION_TYPE_NO_TARGET_STATUS);
                    detail.setTargetAssetStatusId(Long.parseLong(typeEntity.getTargetAssetStatusId()));
                    // 验证是否需要规则
                    lineService.validateControllerRules(detail, typeEntity, fieldRulesMap);
                    detailRepository.saveDetail(detail, tenantId);
                    // 更新动态字段的数据
                    dynamicColumnService.saveDynamicColumns(detail.getTenantId(), detail.getHandoverDetailId(), line.getHandoverLineId(),
                            typeEntity.getBasicTypeCode(), detail.getDynamicColumnList(), dynFieldRulesMap);
                }
            }
        }
        return header;
    }

    @Override
    public Map<String, String> getDynamicFieldRulesMap(Map<String, String> allFieldRules, Set constantSet) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> field : allFieldRules.entrySet()) {
            if (!constantSet.contains(field.getKey())) {
                map.put(field.getKey(), allFieldRules.get(field.getValue()));
            }
        }
        return map;
    }
}
