package org.hzero.halm.atn.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.atn.domain.entity.TransferOrderHeader;
import org.hzero.halm.atn.domain.repository.TransferOrderHeaderRepository;
import org.hzero.halm.atn.infra.mapper.TransferOrderHeaderMapper;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 调拨转移单头 资源库实现
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:34
 */
@Component
public class TransferOrderHeaderRepositoryImpl extends BaseRepositoryImpl<TransferOrderHeader> implements TransferOrderHeaderRepository {

    @Autowired
    private TransferOrderHeaderMapper transferOrderHeaderMapper;
    @Autowired
    private CommonProcessUtils commonProcessUtils;

    @Override
    public Page<TransferOrderHeader> pageTransferOrder(PageRequest pageRequest, TransferOrderHeader transferOrderHeader) {
        return PageHelper.doPageAndSort(pageRequest, () -> {
            Long tenantId = transferOrderHeader.getTenantId();
            String condition = transferOrderHeader.getCondition();
            transferOrderHeader.setProcessStatusCodeList(commonProcessUtils.processStatusCondition(tenantId, condition));

            return transferOrderHeaderMapper.selectTransferOrderHeader(transferOrderHeader);
        });
    }
}
