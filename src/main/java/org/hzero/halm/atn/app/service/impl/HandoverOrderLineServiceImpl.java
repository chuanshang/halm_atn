package org.hzero.halm.atn.app.service.impl;

import com.alibaba.fastjson.JSON;
import io.choerodon.core.exception.CommonException;
import joptsimple.internal.Strings;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.helper.LanguageHelper;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.AssetRepository;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
import org.hzero.halm.atn.app.service.HandoverOrderHeaderService;
import org.hzero.halm.atn.app.service.HandoverOrderLineService;
import org.hzero.halm.atn.app.service.OrderDynamicColumnService;
import org.hzero.halm.atn.app.service.TransactionHistoryService;
import org.hzero.halm.atn.domain.entity.HandoverOrderDetail;
import org.hzero.halm.atn.domain.entity.HandoverOrderHeader;
import org.hzero.halm.atn.domain.entity.HandoverOrderLine;
import org.hzero.halm.atn.domain.repository.HandoverOrderDetailRepository;
import org.hzero.halm.atn.domain.repository.HandoverOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.HandoverOrderLineRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.util.TransactionHistoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 资产移交归还单行应用服务默认实现
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:48:48
 */
@Service
public class HandoverOrderLineServiceImpl implements HandoverOrderLineService {
    private static final Logger logger = LoggerFactory.getLogger(HandoverOrderLineServiceImpl.class);
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private HandoverOrderHeaderService headerService;
    @Autowired
    private HandoverOrderLineRepository lineRepository;
    @Autowired
    private HandoverOrderDetailRepository detailRepository;
    @Autowired
    private HandoverOrderHeaderRepository headerRepository;
    @Autowired
    private TransactionHistoryService historyService;
    @Autowired
    private LovValueHandle lovValueHandle;
    @Autowired
    private TransactionTypeFieldsRepository typeFieldsRepository;
    @Autowired
    private TransactionTypesRepository typesRepository;
    @Autowired
    private OrderDynamicColumnService dynamicColumnService;

    @Override
    public String generateDes(HandoverOrderLine line) {
        HandoverOrderLine originalLine = lineRepository.selectByPrimaryKey(line.getHandoverLineId());
        // 如果资产没有改变，则描述不改变
        if (Objects.equals(originalLine.getAssetId(), line.getAssetId())) {
            return originalLine.getDescription();
        }
        Asset asset = assetRepository.selectByPrimaryKey(line.getAssetId());
        lovValueHandle.process(null, asset);
        if (BaseConstants.DEFAULT_LOCALE.equals(LanguageHelper.locale())) {

        }


        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HandoverOrderLine executeHandoverOrder(HandoverOrderLine line) throws IllegalAccessException {
        String status = line.getProcessStatus();
        HandoverOrderHeader header = headerRepository.selectByPrimaryKey(line.getHandoverHeaderId());
        Map<String, String> fieldMaps = typeFieldsRepository.getFieldType(line.getTenantId(), header.getTransactionTypeId());
        // 获取动态变化列的规则信息
        Map<String,String> dynFieldRulesMap = headerRepository.getDynamicFieldRulesMap(fieldMaps,
                TransactionHistoryUtils.getChangedFieldsMap(HandoverOrderDetail.class).keySet());

        TransactionTypes transactionType = typesRepository.selectByPrimaryKey(header.getTransactionTypeId());
        List<HandoverOrderDetail> detailList = line.getDetailList();
        Asset asset = assetRepository.selectByPrimaryKey(line.getAssetId());
        Assert.notNull(asset, AatnConstans.AatnErrorCode.AATN_HANDOVER_ASSET_NOT_EXISTS);

        // 验证明细是否满足字段规则
        if (CollectionUtils.isNotEmpty(line.getDetailList())) {
            for (HandoverOrderDetail d : line.getDetailList()) {
                this.validateControllerRules(d, transactionType, fieldMaps);
            }
        }
        switch (status) {
            // 移交确认按钮 未提交->移交未归还
            case AatnConstans.HandoverStatus.NO_HANDED_OVER:
                line.setProcessStatus(AatnConstans.HandoverStatus.HANDED_NO_RETURN);
                if (CollectionUtils.isNotEmpty(detailList) && detailList.size() == 1) {
                    HandoverOrderDetail detail = detailList.get(0);
                    // 更新固定行的 资产信息
                    this.updateAsset(detail, asset,transactionType.getInprocessAssetStatusId());
                    // 更新行的按钮状态字段
                    detail.setHandoverTypeCode(AatnConstans.HandoverStatus.HANDED_NO_RETURN);
                    detailRepository.updateOptional(detail, HandoverOrderDetail.FIELD_HANDOVER_TYPE_CODE);
                    dynamicColumnService.saveDynamicColumns(detail.getTenantId(), detail.getHandoverDetailId(), line.getHandoverLineId(),
                            transactionType.getBasicTypeCode(), detail.getDynamicColumnList(), dynFieldRulesMap);
                } else {
                    throw new CommonException(AatnConstans.AatnErrorCode.AATN_HANDOVER_DETAIL_INVALID);
                }
                break;
            //  移交归还按钮 移交未归还->已归还
            case AatnConstans.HandoverStatus.HANDED_NO_RETURN:
                line.setProcessStatus(AatnConstans.HandoverStatus.RETURNED);
                detailList.forEach(detail -> {
                    if (detail.getHandoverDetailId() == null) {
                        // 更新资产信息
                        this.updateAsset(detail, asset,transactionType.getTargetAssetStatusId());
                        // 更新详情的按钮状态
                        detail.setHandoverTypeCode(AatnConstans.HandoverStatus.RETURNED);
                        detailRepository.insert(detail);
                        dynamicColumnService.saveDynamicColumns(detail.getTenantId(), detail.getHandoverDetailId(), line.getHandoverLineId(),
                                transactionType.getBasicTypeCode(), detail.getDynamicColumnList(), dynFieldRulesMap);
                    }
                });
                break;
            default:
                throw new CommonException(AatnConstans.AatnErrorCode.AATN_HANDOVER_STATUS_INVALID);
        }
        Optional.of(detailList).ifPresent(list -> list.forEach(d -> d.setLineFields(line)));
        lineRepository.updateOptional(line, HandoverOrderLine.FIELD_PROCESS_STATUS);
        // 更新头状态
        headerService.updateHandoverStatus(line.getHandoverHeaderId());
        historyService.addTransactionHistory(line, lineRepository, headerRepository);
        return line;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLine(List<HandoverOrderLine> lineList) {
        if (CollectionUtils.isEmpty(lineList)) {
            return;
        }
        lineRepository.batchDeleteByPrimaryKey(lineList);
        lineList.forEach(line -> {
            // 只有新建和未提交状态可以删除
            if (!(Objects.equals(line.getProcessStatus(), AatnConstans.HandoverStatus.NEW)
                    || Objects.equals(line.getProcessStatus(), AatnConstans.HandoverStatus.NO_HANDED_OVER))) {
                throw new CommonException(AatnConstans.AatnErrorCode.AATN_HANDOVER_DELETE_INVALID);
            }
            List<HandoverOrderDetail> detailList = line.getDetailList();
            detailRepository.batchDeleteByPrimaryKey(detailList);
        });
    }

    @Override
    public void validateControllerRules(HandoverOrderDetail detail, TransactionTypes transactionType, Map<String, String> fieldRules) throws IllegalAccessException {
        // 验证目标资产状态是否满足状态
        List<String> statusRange = this.changJsonArray(transactionType.getTargetAssetStatusScope());
        if (!isContainValue(String.valueOf(detail.getTargetAssetStatusId()), statusRange)) {
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_ASSET_STATUS_OUT_OF_RANGE,
                    detail.getTargetAssetStatusId(), statusRange);
        }
        // 获取明细表的，固定修改字段。
        Map<String, List<String>> fieldNameMap = TransactionHistoryUtils.getChangedFieldsMap(HandoverOrderDetail.class);
        Set<String> fieldSet = fieldNameMap.keySet();
        for (String constantField : fieldSet) {
            // 获取固定字段的规则类型
            String rule = fieldRules.get(constantField);
            List<String> fields = fieldNameMap.get(constantField);
            Object current = FieldUtils.readDeclaredField(detail, fields.get(0), true);
            Object target = FieldUtils.readDeclaredField(detail, fields.get(2), true);
            setRuleValue(rule, constantField, detail, current, target);
        }

    }

    private void setRuleValue(String rule, String targetField, Object t, Object current, Object target) {
        if (rule == null) {
            return;
        }
        try {
            switch (rule) {
                case AatnConstans.AatnChangeRule.MUST_MODIFY:
                    if (Objects.equals(current, target)) {
                        throw new CommonException("该字段必须修改");
                    }
                    break;
                case AatnConstans.AatnChangeRule.CLEAN:
                    FieldUtils.writeDeclaredField(t, targetField, Strings.EMPTY, true);
                    break;
                case AatnConstans.AatnChangeRule.READ_ONLY:
                    FieldUtils.writeDeclaredField(t, targetField, current, true);
                    break;
                case AatnConstans.AatnChangeRule.ARBITRARY_MODIFY:
                    if (Objects.isNull(target)) {
                        FieldUtils.writeDeclaredField(t, targetField, current, true);
                    }
                    break;
                default:
                    break;
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void validateFieldValueInRange(Asset asset, List<HandoverOrderDetail> detailList, TransactionTypes transactionType) {

        List<String> organizationRange = this.changJsonArray(transactionType.getOrganizationScope());
        List<String> statusRange = this.changJsonArray(transactionType.getStatusScope());
        List<String> specialtyRange = this.changJsonArray(transactionType.getSpecialtyScope());

        if (!isContainValue(String.valueOf(asset.getOwningOrgId()), organizationRange)) {
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_OWNING_ORG_OUT_OF_RANGE, asset.getOwningOrgId(), organizationRange);
        }
        if (!isContainValue(String.valueOf(asset.getAssetSpecialtyId()), specialtyRange)) {
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_OWNING_MAJOR_OUT_OF_RANGE, asset.getAssetSpecialtyId(), specialtyRange);
        }
        // 判断所属特殊资产是否符合 事务类型的所属特殊资产。
        Integer specialAssetFlag = transactionType.getSpecialAssetFlag();
        if (Objects.equals(specialAssetFlag, BaseConstants.Flag.YES) && !Objects.equals(transactionType.getSpecialAsset(), asset.getSpecialAssetCode())) {
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_SPECIAL_ASSET_OUT_OF_RANGE);
        }
        if (CollectionUtils.isNotEmpty(detailList)) {
            for (HandoverOrderDetail detail : detailList) {
                // 判断资产状态是否满足范围
                if (!isContainValue(String.valueOf(detail.getCurrentAssetStatusId()), statusRange)) {
                    throw new CommonException(AatnConstans.AatnErrorCode.AATN_ASSET_STATUS_OUT_OF_RANGE, detail.getCurrentAssetStatusId(), statusRange);
                }
            }
        }
    }

    private List<String> changJsonArray(String jsonStr) {
        if (Objects.nonNull(jsonStr)) {
            return JSON.parseArray(jsonStr,String.class);
        }
        return new ArrayList<>();
    }

    /**
     * 判断一个集合中，是否包含某元素。
     *
     * @param value value
     * @param list  list
     * @return 结果
     */
    private Boolean isContainValue(String value, List<String> list) {
        if (CollectionUtils.isNotEmpty(list)) {
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


    private void updateAsset(HandoverOrderDetail detail, Asset asset,String status) {
        asset.setAssetStatusId(new Long(status));
        asset.setOwningPersonId(detail.getTargetOwningPersonId());
        asset.setUserPersonId((detail.getTargetUsingPersonId()));
        assetRepository.updateAssetOnDynamicColumn(detail.getDynamicColumnList(), asset);
    }

}
