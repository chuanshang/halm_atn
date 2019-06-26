package org.hzero.halm.atn.infra.repository.impl;

import org.hzero.halm.atn.domain.entity.HandoverOrderLine;
import org.hzero.halm.atn.domain.repository.HandoverOrderLineRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.mapper.HandoverOrderLineMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 资产移交归还单行 资源库实现
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:48:48
 */
@Component
public class HandoverOrderLineRepositoryImpl extends BaseRepositoryImpl<HandoverOrderLine> implements HandoverOrderLineRepository {

    @Autowired
    HandoverOrderLineMapper lineMapper;

    @Override
    public HandoverOrderLine saveLine(HandoverOrderLine line,Long tenantId) {
        if(line.getHandoverLineId()==null){
            line.setTenantId(tenantId);
            // 创建时
            line.setProcessStatus(AatnConstans.HandoverStatus.NEW);
            // 设置行号
            Long theMaxNum = lineMapper.selectMaxNum(line);
            line.setLineNum(theMaxNum == null ? 1 : theMaxNum + 1);
            this.insert(line);
        }else {
            this.updateOptional(line,
                    HandoverOrderLine.FIELD_ASSET_ID,
                    HandoverOrderLine.FIELD_DESCRIPTION);
        }
        return line;
    }
}
