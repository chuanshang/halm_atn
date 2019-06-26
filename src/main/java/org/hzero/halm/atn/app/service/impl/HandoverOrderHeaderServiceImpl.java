package org.hzero.halm.atn.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.halm.atn.app.service.HandoverOrderHeaderService;
import org.hzero.halm.atn.domain.entity.HandoverOrderHeader;
import org.hzero.halm.atn.domain.entity.HandoverOrderLine;
import org.hzero.halm.atn.domain.repository.HandoverOrderHeaderRepository;
import org.hzero.halm.atn.domain.repository.HandoverOrderLineRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 资产移交归还单头应用服务默认实现
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:03
 */
@Service
public class HandoverOrderHeaderServiceImpl implements HandoverOrderHeaderService {

    @Autowired
    private HandoverOrderHeaderRepository headerRepository;
    @Autowired
    private HandoverOrderLineRepository lineRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitHandover(HandoverOrderHeader header) {
        if (!Objects.equals(header.getProcessStatus(), AatnConstans.ApproveStatus.NEW)) {
            throw new CommonException("该移交归还单不满足提交条件");
        }
        // 改为已审批状态
        header.setProcessStatus(AatnConstans.ApproveStatus.APPROVED);
        headerRepository.updateOptional(header, HandoverOrderHeader.FIELD_PROCESS_STATUS);
        List<HandoverOrderLine> lineList = lineRepository.selectByCondition(Condition.builder(HandoverOrderLine.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(HandoverOrderLine.FIELD_HANDOVER_HEADER_ID, header.getHandoverHeaderId())
                        .andEqualTo(HandoverOrderLine.FIELD_TENANT_ID, header.getTenantId())
                ).build());

        if (CollectionUtils.isEmpty(lineList)) {
            return;
        }
        // 将行的状态改为未移交
        for (HandoverOrderLine line : lineList) {
            if (Objects.equals(line.getProcessStatus(), AatnConstans.HandoverStatus.NEW)) {
                line.setProcessStatus(AatnConstans.HandoverStatus.NO_HANDED_OVER);
            }
        }
        lineRepository.batchUpdateByPrimaryKey(lineList);

    }

    @Override
    public void updateHandoverStatus(Long headerId) {
        HandoverOrderHeader header = headerRepository.selectByPrimaryKey(headerId);
        List<HandoverOrderLine> lineList = lineRepository.selectByCondition(Condition.builder(HandoverOrderLine.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(HandoverOrderLine.FIELD_HANDOVER_HEADER_ID, header.getHandoverHeaderId())
                        .andEqualTo(HandoverOrderLine.FIELD_TENANT_ID, header.getTenantId())
                ).build());
        int handNoReturnNum = 0;
        int returnNum = 0;
        for (HandoverOrderLine line : lineList) {
            switch (line.getProcessStatus()) {
                case AatnConstans.HandoverStatus.HANDED_NO_RETURN:
                    handNoReturnNum++;
                    break;
                case AatnConstans.HandoverStatus.RETURNED:
                    returnNum++;
                    break;
                default:
            }
        }

        if (handNoReturnNum > 0) {
            header.setProcessStatus(AatnConstans.ApproveStatus.PROCESSING);
        } else if (returnNum == lineList.size()) {
            header.setProcessStatus(AatnConstans.ApproveStatus.COMPLETED);
        }
        headerRepository.updateOptional(header, HandoverOrderHeader.FIELD_PROCESS_STATUS);
    }
}
