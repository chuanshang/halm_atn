package org.hzero.halm.atn.app.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.redis.RedisHelper;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
import org.hzero.halm.atn.app.service.DisposeOrderHeaderService;
import org.hzero.halm.atn.app.service.DisposeOrderLineService;
import org.hzero.halm.atn.domain.entity.DisposeOrderHeader;
import org.hzero.halm.atn.domain.entity.DisposeOrderLine;
import org.hzero.halm.atn.domain.repository.DisposeOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.DisposeOrderLineRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.constant.AatnDisposeOrderConstans;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

import io.choerodon.core.exception.CommonException;

/**
 * 资产处置单头应用服务默认实现
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 11:05:34
 */
@Service
public class DisposeOrderHeaderServiceImpl implements DisposeOrderHeaderService {

    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private DisposeOrderHeaderRepository disposeOrderHeaderRepository;
    @Autowired
    private DisposeOrderLineRepository disposeOrderLineRepository;
    @Autowired
    private TransactionTypesRepository transactionTypesRepository;
    @Autowired
    private DisposeOrderLineService disposeOrderLineService;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private CommonProcessUtils commonProcessUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DisposeOrderHeader disposeOrderHeaderCreate(Long tenantId, DisposeOrderHeader disposeOrderHeader) {
        // 根据资产事务类型确定资产处置单编号
        TransactionTypes typeEntity = transactionTypesRepository.selectByPrimaryKey(disposeOrderHeader.getTransactionTypeId());
        Assert.notNull(typeEntity, BaseConstants.ErrorCode.DATA_INVALID);
        if (StringUtils.isNotEmpty(typeEntity.getCodeRule())) {
            disposeOrderHeader.setDisposeNum(codeRuleBuilder.generateCode(typeEntity.getCodeRule(), null));
        }
        Assert.notNull(disposeOrderHeader.getDisposeNum(), BaseConstants.ErrorCode.DATA_INVALID);

        // 校验编码唯一性
        int count = disposeOrderHeaderRepository.selectCountByCondition(Condition.builder(DisposeOrderHeader.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(DisposeOrderHeader.FIELD_TENANT_ID, tenantId)
                        .andEqualTo(DisposeOrderHeader.FIELD_DISPOSE_NUM, disposeOrderHeader.getDisposeNum())).build());
        if (count > 0){
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_DUPLICATE_DISPOSE_NUM, disposeOrderHeader.getDisposeNum());
        }

        // 处理类型设置
        disposeOrderHeader.setProcessStatus(AatnConstans.ApproveStatus.NEW);

        // 根据当前登陆用户ID获取员工ID，设置默认负责人
        if (Objects.isNull(disposeOrderHeader.getPrincipalPersonId())) {
            disposeOrderHeader.setPrincipalPersonId(commonProcessUtils.getDefaultEmployeeId());
        }

        // insert
        disposeOrderHeaderRepository.insertSelective(disposeOrderHeader);

        // 行统一新增/修改
        if (CollectionUtils.isNotEmpty(disposeOrderHeader.getDisposeOrderLines())) {
            List<DisposeOrderLine> disposeOrderLines = disposeOrderLineService.saveDisposeOrderLines(tenantId, disposeOrderHeader, typeEntity, disposeOrderHeader.getDisposeOrderLines());
            disposeOrderHeader.setDisposeOrderLines(disposeOrderLines);
        }

        return disposeOrderHeader;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DisposeOrderHeader disposeOrderHeaderUpdate(Long tenantId, DisposeOrderHeader disposeOrderHeader) {

        TransactionTypes typeEntity = transactionTypesRepository.selectByPrimaryKey(disposeOrderHeader.getTransactionTypeId());
        Assert.notNull(typeEntity, BaseConstants.ErrorCode.DATA_INVALID);

        disposeOrderHeader.setTenantId(tenantId);
        // 行统一新增/修改
        if (CollectionUtils.isNotEmpty(disposeOrderHeader.getDisposeOrderLines())) {
            List<DisposeOrderLine> disposeOrderLines = disposeOrderLineService.saveDisposeOrderLines(tenantId, disposeOrderHeader, typeEntity, disposeOrderHeader.getDisposeOrderLines());
            disposeOrderHeader.setDisposeOrderLines(disposeOrderLines);
        }

        disposeOrderHeaderRepository.updateOptional(disposeOrderHeader,DisposeOrderHeader.FIELD_TRANSACTION_TYPE_ID,
                DisposeOrderHeader.FIELD_PROCESS_STATUS,DisposeOrderHeader.FIELD_PRINCIPAL_PERSON_ID,DisposeOrderHeader.FIELD_DISPOSE_NUM,
                DisposeOrderHeader.FIELD_TITLE_OVERVIEW,DisposeOrderHeader.FIELD_PLAN_START_DATE,DisposeOrderHeader.FIELD_PLAN_END_DATE,
                DisposeOrderHeader.FIELD_DESCRIPTION,DisposeOrderHeader.FIELD_TENANT_ID);
        return disposeOrderHeader;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DisposeOrderHeader commitStatus(Long tenantId, DisposeOrderHeader disposeOrderHeader) {
        if((AatnConstans.ApproveStatus.NEW).equals(disposeOrderHeader.getProcessStatus())){
            disposeOrderHeader.setTenantId(tenantId);
            //为头处理状态赋值
            disposeOrderHeader.setProcessStatus(AatnConstans.ApproveStatus.APPROVED);
            //查出头关联的行
            List<DisposeOrderLine> disposeOrderLines = disposeOrderLineRepository.selectByCondition(Condition.builder(DisposeOrderLine.class)
                    .andWhere(Sqls.custom().andEqualTo(DisposeOrderLine.FIELD_DISPOSE_HEADER_ID, disposeOrderHeader.getDisposeHeaderId())).build());
            //为行处理状态赋值
            if (CollectionUtils.isNotEmpty(disposeOrderLines)) {
                disposeOrderLines.forEach(line -> line.setProcessStatus(AatnDisposeOrderConstans.DisposeLineStatus.PENDING_DISPOSAL));
            }
            //更改头处理状态
            disposeOrderHeaderRepository.updateOptional(disposeOrderHeader,DisposeOrderHeader.FIELD_PROCESS_STATUS);
            //更改头处理状态
            disposeOrderLineRepository.batchUpdateOptional(disposeOrderLines, DisposeOrderLine.FIELD_PROCESS_STATUS);
        }else{
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_DISPOSE_CURRENT_STATUS_IS_UNCOMMITTABLE);
        }

        return disposeOrderHeader;
    }
}
