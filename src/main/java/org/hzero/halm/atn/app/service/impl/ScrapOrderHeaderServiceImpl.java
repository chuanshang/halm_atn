package org.hzero.halm.atn.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
import org.hzero.halm.atn.app.service.ScrapOrderHeaderService;
import org.hzero.halm.atn.app.service.ScrapOrderLineService;
import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;
import org.hzero.halm.atn.domain.entity.ScrapOrderLine;
import org.hzero.halm.atn.domain.repository.ScrapOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.ScrapOrderLineRepository;
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
 * 资产报废单头应用服务默认实现
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:50
 */
@Service
public class ScrapOrderHeaderServiceImpl implements ScrapOrderHeaderService {
    @Autowired
    private ScrapOrderHeaderRepository scrapOrderHeaderRepository;
    @Autowired
    private TransactionTypesRepository transactionTypesRepository;
    @Autowired
    private CodeRuleBuilder codeRuleBuilder;
    @Autowired
    private CommonProcessUtils commonProcessUtils;
    @Autowired
    private ScrapOrderLineService scrapOrderLineService;
    @Autowired
    private ScrapOrderLineRepository scrapOrderLineRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScrapOrderHeader createScrapOrder(Long tenantId, ScrapOrderHeader scrapOrderHeader) {
        // 根据资产事务类型确定报废单编码
        TransactionTypes typeEntity = transactionTypesRepository.selectByPrimaryKey(scrapOrderHeader.getTransactionTypeId());
        Assert.notNull(typeEntity, BaseConstants.ErrorCode.DATA_INVALID);
        if (StringUtils.isNotEmpty(typeEntity.getCodeRule())) {
            scrapOrderHeader.setScrapNum(codeRuleBuilder.generateCode(typeEntity.getCodeRule(), null));
        }
        Assert.notNull(scrapOrderHeader.getScrapNum(), BaseConstants.ErrorCode.DATA_INVALID);

        // 校验编码唯一性
        int count = scrapOrderHeaderRepository.selectCountByCondition(Condition.builder(ScrapOrderHeader.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(ScrapOrderHeader.FIELD_TENANT_ID, tenantId)
                        .andEqualTo(ScrapOrderHeader.FIELD_SCRAP_NUM, scrapOrderHeader.getScrapNum())).build());
        if (count > 0){
            throw new CommonException(AatnConstans.AatnErrorCode.AATN_DUPLICATE_TRANSFER_NUM, scrapOrderHeader.getScrapNum());
        }

        // 处理类型设置
        scrapOrderHeader.setProcessStatus(AatnConstans.ApproveStatus.NEW);

        // 根据当前登陆用户ID获取员工ID，设置默认负责人
        if (Objects.isNull(scrapOrderHeader.getPrincipalPersonId())) {
            scrapOrderHeader.setPrincipalPersonId(commonProcessUtils.getDefaultEmployeeId());
        }

        // insert
        scrapOrderHeaderRepository.insertSelective(scrapOrderHeader);

        // 行统一新增/修改
        if (CollectionUtils.isNotEmpty(scrapOrderHeader.getScrapOrderLines())) {
            List<ScrapOrderLine> scrapOrderLines = scrapOrderLineService.saveScrapOrderLines(tenantId, scrapOrderHeader.getScrapHeaderId(), typeEntity, scrapOrderHeader.getScrapOrderLines());
            scrapOrderHeader.setScrapOrderLines(scrapOrderLines);
        }

        return scrapOrderHeader;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScrapOrderHeader updateScrapOrder(Long tenantId, ScrapOrderHeader scrapOrderHeader) {

        TransactionTypes typeEntity = transactionTypesRepository.selectByPrimaryKey(scrapOrderHeader.getTransactionTypeId());
        Assert.notNull(typeEntity, BaseConstants.ErrorCode.DATA_INVALID);

        // 行统一新增/修改
        if (CollectionUtils.isNotEmpty(scrapOrderHeader.getScrapOrderLines())) {
            List<ScrapOrderLine> scrapOrderLines = scrapOrderLineService.saveScrapOrderLines(tenantId, scrapOrderHeader.getScrapHeaderId(), typeEntity, scrapOrderHeader.getScrapOrderLines());
            scrapOrderHeader.setScrapOrderLines(scrapOrderLines);
        }

        // update
        scrapOrderHeaderRepository.updateOptional(scrapOrderHeader,
                ScrapOrderHeader.FIELD_TITLE_OVERVIEW,
                ScrapOrderHeader.FIELD_DESCRIPTION,
                ScrapOrderHeader.FIELD_TRANSACTION_TYPE_ID,
                ScrapOrderHeader.FIELD_PROCESS_STATUS,
                ScrapOrderHeader.FIELD_PRINCIPAL_PERSON_ID,
                ScrapOrderHeader.FIELD_PLAN_END_DATE,
                ScrapOrderHeader.FIELD_PLAN_START_DATE);

        return scrapOrderHeader;
    }
    @Override
    public void validateNotEmpty(List<ScrapOrderLine> scrapOrderLines){
        for(ScrapOrderLine line:scrapOrderLines){
            if (line.getRemainDepreciationMouth()==null) {
                throw new CommonException(AatnConstans.AatnErrorCode.AATN_SCRAP_ORDER_LINE_REMAIN_DEPRECIATION_MOUTH_IS_NULL);
            }
            if (line.getDescription()==null) {
                throw new CommonException(AatnConstans.AatnErrorCode.AATN_SCRAP_ORDER_LINE_DESCRIPTION_IS_NULL);
            }
            if (line.getProcessStatus()==null) {
                throw new CommonException(AatnConstans.AatnErrorCode.AATN_SCRAP_ORDER_LINE_PROCESS_STATUS_IS_NULL);
            }
            if (line.getRemainCost().toString()==null) {
                throw new CommonException(AatnConstans.AatnErrorCode.AATN_SCRAP_ORDER_LINE_REMAIN_COST_IS_NULL);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScrapOrderHeader submitApprovalRequest(Long tenantId, ScrapOrderHeader scrapOrderHeader) {
        scrapOrderHeader.setTenantId(tenantId);
        if(scrapOrderHeader ==null){
            throw new CommonException(BaseConstants.ErrorCode.NOT_FOUND);
        }
        if(scrapOrderHeader.getProcessStatus().equals(AatnConstans.ApproveStatus.NEW)){
            //资产事务处理单提交审批后，状态为“待报废”,此处由于暂时没有提交审批的逻辑，直接改为待报废
            List<ScrapOrderLine> scrapOrderLineList=scrapOrderLineRepository.selectByCondition(Condition.builder(ScrapOrderLine.class)
                    .andWhere(Sqls.custom()
                            .andEqualTo(ScrapOrderLine.FIELD_TENANT_ID, tenantId)
                            .andEqualTo(ScrapOrderLine.FIELD_SCRAP_HEADER_ID, scrapOrderHeader.getScrapHeaderId())).build());
            for (ScrapOrderLine scrapOrderLine : scrapOrderLineList) {
                if(scrapOrderLine.getProcessStatus().equals(AatnConstans.ScrapStatus.NEW)){
                    scrapOrderLine.setProcessStatus(AatnConstans.ScrapStatus.WAIT_FOR_SCRAP);
                    scrapOrderLineRepository.updateByPrimaryKeySelective(scrapOrderLine);
                }
            }
            scrapOrderHeader.setProcessStatus(AatnConstans.ApproveStatus.APPROVED);
            scrapOrderHeaderRepository.updateByPrimaryKeySelective(scrapOrderHeader);
        }

        return scrapOrderHeader;
    }
}
