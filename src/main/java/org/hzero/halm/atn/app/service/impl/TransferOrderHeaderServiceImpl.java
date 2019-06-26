package org.hzero.halm.atn.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.boot.platform.code.constant.CodeConstants;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
import org.hzero.halm.atn.app.service.TransferOrderHeaderService;
import org.hzero.halm.atn.app.service.TransferOrderLineService;
import org.hzero.halm.atn.domain.entity.TransferOrderHeader;
import org.hzero.halm.atn.domain.entity.TransferOrderLine;
import org.hzero.halm.atn.domain.repository.TransferOrderHeaderRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.common.Criteria;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * 调拨转移单头应用服务默认实现
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:34
 */
@Service
public class TransferOrderHeaderServiceImpl implements TransferOrderHeaderService {

    @Autowired
    private TransferOrderHeaderRepository transferOrderHeaderRepository;
    @Autowired
    private TransactionTypesRepository transactionTypesRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private CommonProcessUtils commonProcessUtils;
    @Autowired
    private TransferOrderLineService transferOrderLineService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransferOrderHeader createTransferOrder(Long tenantId, TransferOrderHeader transferOrderHeader) {
        // 根据资产事务类型确定调拨转单编码
        TransactionTypes typeEntity = transactionTypesRepository.selectByPrimaryKey(transferOrderHeader.getTransactionTypeId());
        Assert.notNull(typeEntity, BaseConstants.ErrorCode.DATA_INVALID);
        if (StringUtils.isNotEmpty(typeEntity.getCodeRule())) {
            // transferOrderHeader.setTransferNum(codeRuleBuilder.generateCode(typeEntity.getCodeRule(), null));
            String transferNum = codeRuleBuilder.generateCode(tenantId, typeEntity.getCodeRule(), CodeConstants.CodeRuleLevelCode.GLOBAL, CodeConstants.CodeRuleLevelCode.GLOBAL, null);
            transferOrderHeader.setTransferNum(transferNum);
        }
        Assert.notNull(transferOrderHeader.getTransferNum(), AatnConstans.AatnErrorCode.AATN_NUM_IS_EMPTY);

        // 校验编码唯一性
        int count = transferOrderHeaderRepository.selectCountByCondition(Condition.builder(TransferOrderHeader.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(TransferOrderHeader.FIELD_TENANT_ID, tenantId)
                        .andEqualTo(TransferOrderHeader.FIELD_TRANSFER_NUM, transferOrderHeader.getTransferNum())).build());
        if (count > 0) {
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_DUPLICATE_TRANSFER_NUM, transferOrderHeader.getTransferNum());
        }

        // 处理类型设置
        transferOrderHeader.setProcessStatus(AatnConstans.ApproveStatus.NEW);

        // 根据当前登陆用户ID获取员工ID，设置默认负责人
        if (Objects.isNull(transferOrderHeader.getPrincipalPersonId())) {
            transferOrderHeader.setPrincipalPersonId(commonProcessUtils.getDefaultEmployeeId());
        }

        // insert
        transferOrderHeaderRepository.insertSelective(transferOrderHeader);

        // 行统一新增/修改
        if (CollectionUtils.isNotEmpty(transferOrderHeader.getTransferOrderLines())) {
            List<TransferOrderLine> transferOrderLines = transferOrderLineService.saveTransferOrderLines(tenantId, transferOrderHeader.getTransferHeaderId(), typeEntity, transferOrderHeader.getTransferOrderLines());
            transferOrderHeader.setTransferOrderLines(transferOrderLines);
        }

        return transferOrderHeader;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransferOrderHeader updateTransferOrder(Long tenantId, TransferOrderHeader transferOrderHeader) {
        // 取当前单据状态
        TransferOrderHeader headerEntity = transferOrderHeaderRepository.selectOneOptional(transferOrderHeader, new Criteria().select(TransferOrderHeader.FIELD_PROCESS_STATUS));
        Assert.notNull(headerEntity, BaseConstants.ErrorCode.DATA_INVALID);
        if(!StringUtils.equals(headerEntity.getProcessStatus(), AatnConstans.ApproveStatus.NEW)){
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_PROCESS_STATUS_INVALID);
        }

        TransactionTypes typeEntity = transactionTypesRepository.selectByPrimaryKey(transferOrderHeader.getTransactionTypeId());
        Assert.notNull(typeEntity, BaseConstants.ErrorCode.DATA_INVALID);

        // 行统一新增/修改
        if (CollectionUtils.isNotEmpty(transferOrderHeader.getTransferOrderLines())) {
            List<TransferOrderLine> transferOrderLines = transferOrderLineService.saveTransferOrderLines(tenantId, transferOrderHeader.getTransferHeaderId(), typeEntity, transferOrderHeader.getTransferOrderLines());
            transferOrderHeader.setTransferOrderLines(transferOrderLines);
        }

        // update
        transferOrderHeaderRepository.updateOptional(transferOrderHeader,
                TransferOrderHeader.FIELD_DESCRIPTION,
                TransferOrderHeader.FIELD_TRANSACTION_TYPE_ID,
                TransferOrderHeader.FIELD_PRINCIPAL_PERSON_ID,
                TransferOrderHeader.FIELD_PLAN_END_DATE,
                TransferOrderHeader.FIELD_PLAN_START_DATE);

        return transferOrderHeader;
    }
}
