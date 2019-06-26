package org.hzero.halm.atn.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.boot.platform.code.constant.CodeConstants;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
import org.hzero.halm.atn.app.service.ChangeOrderHeaderService;
import org.hzero.halm.atn.app.service.ChangeOrderLineService;
import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;
import org.hzero.halm.atn.domain.entity.ChangeOrderLine;
import org.hzero.halm.atn.domain.repository.ChangeOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.ChangeOrderLineRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * 资产信息变更单头应用服务默认实现
 *
 * @author like.zhang@hand-china.com 2019-03-26 10:20:34
 */
@Service
public class ChangeOrderHeaderServiceImpl implements ChangeOrderHeaderService {

    @Autowired
    private TransactionTypesRepository transactionTypesRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private ChangeOrderHeaderRepository changeOrderHeaderRepository;
    @Autowired
    private CommonProcessUtils commonProcessUtils;
    @Autowired
    private ChangeOrderLineService changeOrderLineService;
    @Autowired
    private ChangeOrderLineRepository changeOrderLineRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChangeOrderHeader updateChangeOrder(Long tenantId, ChangeOrderHeader changeOrderHeader) {
        ChangeOrderHeader headerEntity = changeOrderHeaderRepository.selectByPrimaryKey(changeOrderHeader.getChangeHeaderId());
        Assert.notNull(headerEntity, BaseConstants.ErrorCode.DATA_INVALID);
        if (!StringUtils.equals(headerEntity.getProcessStatus(), AatnConstans.ApproveStatus.NEW)) {
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_PROCESS_STATUS_INVALID);
        }

        TransactionTypes typeEntity = transactionTypesRepository.selectByPrimaryKey(changeOrderHeader.getTransactionTypeId());
        Assert.notNull(typeEntity, BaseConstants.ErrorCode.DATA_INVALID);

        // 行统一新增/修改
        if (CollectionUtils.isNotEmpty(changeOrderHeader.getChangeOrderLines())) {
            List<ChangeOrderLine> changeOrderLines = changeOrderLineService.saveChangeOrderLine(tenantId, headerEntity, typeEntity, changeOrderHeader.getChangeOrderLines());
            changeOrderHeader.setChangeOrderLines(changeOrderLines);
        }

        // update
        changeOrderHeaderRepository.updateOptional(changeOrderHeader,
                ChangeOrderHeader.FIELD_DESCRIPTION,
                ChangeOrderHeader.FIELD_TRANSACTION_TYPE_ID,
                ChangeOrderHeader.FIELD_PRINCIPAL_PERSON_ID,
                ChangeOrderHeader.FIELD_PLAN_END_DATE,
                ChangeOrderHeader.FIELD_PLAN_START_DATE);

        return changeOrderHeader;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChangeOrderHeader createChangeOrder(Long tenantId, ChangeOrderHeader changeOrderHeader) {
        // 根据资产事务类型确定资产变更单编码
        TransactionTypes typeEntity = transactionTypesRepository.selectByPrimaryKey(changeOrderHeader.getTransactionTypeId());
        Assert.notNull(typeEntity, BaseConstants.ErrorCode.DATA_INVALID);
        if (StringUtils.isNotEmpty(typeEntity.getCodeRule())) {
            // changeOrderHeader.setChangeNum(codeRuleBuilder.generateCode(typeEntity.getCodeRule(), null));
            String changeNum = codeRuleBuilder.generateCode(tenantId, typeEntity.getCodeRule(), CodeConstants.CodeRuleLevelCode.GLOBAL, CodeConstants.CodeRuleLevelCode.GLOBAL, null);
            changeOrderHeader.setChangeNum(changeNum);
        }
        Assert.notNull(changeOrderHeader.getChangeNum(), AatnConstans.AatnErrorCode.AATN_NUM_IS_EMPTY);

        // 校验编码唯一性
        int count = changeOrderHeaderRepository.selectCountByCondition(Condition.builder(ChangeOrderHeader.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(ChangeOrderHeader.FIELD_TENANT_ID, tenantId)
                        .andEqualTo(ChangeOrderHeader.FIELD_CHANGE_NUM, changeOrderHeader.getChangeNum())).build());
        if (count > 0) {
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_DUPLICATE_CHANGE_NUM, changeOrderHeader.getChangeNum());
        }

        // 处理类型设置
        changeOrderHeader.setProcessStatus(AatnConstans.ApproveStatus.NEW);

        // 根据当前登陆用户ID获取员工ID，设置默认负责人
        if (Objects.isNull(changeOrderHeader.getPrincipalPersonId())) {
            changeOrderHeader.setPrincipalPersonId(commonProcessUtils.getDefaultEmployeeId());
        }

        // insert
        changeOrderHeaderRepository.insertSelective(changeOrderHeader);

        // 行统一新增/修改
        if (CollectionUtils.isNotEmpty(changeOrderHeader.getChangeOrderLines())) {
            List<ChangeOrderLine> changeOrderLines = changeOrderLineService.saveChangeOrderLine(tenantId, changeOrderHeader, typeEntity, changeOrderHeader.getChangeOrderLines());
            changeOrderHeader.setChangeOrderLines(changeOrderLines);
        }

        return changeOrderHeader;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChangeOrderHeader submitChangeOrder(Long tenantId, ChangeOrderHeader changeOrderHeader) {

        ChangeOrderHeader entity = changeOrderHeaderRepository.selectByPrimaryKey(changeOrderHeader.getChangeHeaderId());
        Assert.notNull(entity, BaseConstants.ErrorCode.DATA_INVALID);
        Assert.isTrue(StringUtils.equals(entity.getProcessStatus(), AatnConstans.ApproveStatus.NEW), AatnConstans.AatnErrorCode.AATN_PROCESS_STATUS_INVALID);

        List<ChangeOrderLine> lineEntities = changeOrderLineRepository.selectByCondition(Condition.builder(ChangeOrderLine.class)
                .andWhere(Sqls.custom().andEqualTo(ChangeOrderLine.FIELD_CHANGE_HEADER_ID, changeOrderHeader.getChangeHeaderId())).build());

        // 提交直接审批 TODO
        entity.setProcessStatus(AatnConstans.ApproveStatus.APPROVED);

        if (CollectionUtils.isNotEmpty(lineEntities)) {
            lineEntities.forEach(line -> line.setProcessStatus(AatnConstans.TransferOrderLineStatus.UNPROCESSED));
        }

        changeOrderLineRepository.batchUpdateOptional(lineEntities, ChangeOrderLine.FIELD_PROCESS_STATUS);
        changeOrderHeaderRepository.updateOptional(entity, ChangeOrderHeader.FIELD_PROCESS_STATUS);
        entity.setChangeOrderLines(lineEntities);
        return entity;
    }
}
