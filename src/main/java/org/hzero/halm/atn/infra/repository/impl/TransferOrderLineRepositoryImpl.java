package org.hzero.halm.atn.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.atn.domain.entity.TransferOrderHeader;
import org.hzero.halm.atn.domain.entity.TransferOrderLine;
import org.hzero.halm.atn.domain.repository.TransferOrderLineRepository;
import org.hzero.halm.atn.infra.mapper.TransferOrderLineMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 调拨转移单行 资源库实现
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:33
 */
@Component
public class TransferOrderLineRepositoryImpl extends BaseRepositoryImpl<TransferOrderLine> implements TransferOrderLineRepository {

    @Autowired
    private TransferOrderLineMapper transferOrderLineMapper;

    @Override
    public Page<TransferOrderLine> listTransferOrderLine(TransferOrderHeader transferOrderHeader, PageRequest pageRequest) {
        return PageHelper.doPageAndSort(pageRequest, () -> transferOrderLineMapper.selectTransferOrderLine(transferOrderHeader));
    }

    @Override
    public TransferOrderLine selectTransferLineById(Long transferOrderLineId) {
        TransferOrderHeader transferOrderHeader = new TransferOrderHeader();
        transferOrderHeader.setTransferLineId(transferOrderLineId);
        return transferOrderLineMapper.selectTransferOrderLine(transferOrderHeader).get(0);
    }
}
