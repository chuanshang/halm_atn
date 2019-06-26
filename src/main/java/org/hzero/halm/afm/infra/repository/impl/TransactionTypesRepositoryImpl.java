package org.hzero.halm.afm.infra.repository.impl;

import org.hzero.halm.afm.api.dto.TransactionTypesDTO;
import org.hzero.halm.afm.infra.mapper.TransactionTypesMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 资产事务处理类型 资源库实现
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:43:01
 */
@Component
public class TransactionTypesRepositoryImpl extends BaseRepositoryImpl<TransactionTypes> implements TransactionTypesRepository {

    @Autowired
    TransactionTypesMapper transactionTypesMapper;

    @Override
    public TransactionTypes getTransactionTypes(Long tenantId, Long transactionTypeId) {
        return transactionTypesMapper.getTransactionTypes(tenantId,transactionTypeId);
    }

    @Override
    public List<TransactionTypes> selectNodeAndChildNodeTransactionTypes(Long tenantId, TransactionTypes transactionTypes) {
        transactionTypes.setTenantId(tenantId);
        return transactionTypesMapper.selectNodeAndChildNodeTransactionTypes(transactionTypes);
    }

    @Override
    public List<TransactionTypesDTO> exportTransactionTypes(TransactionTypesDTO transactionTypesDTO) {
        List<TransactionTypesDTO> transactionTypesExportList = transactionTypesMapper.exportTransactionTypes(transactionTypesDTO);
        if (CollectionUtils.isEmpty(transactionTypesExportList)){
            transactionTypesExportList = new ArrayList<>();
        }

        return transactionTypesExportList;
    }

    @Override
    public List<TransactionTypes> listTransactionTypes(TransactionTypes transactionTypes) {
        return transactionTypesMapper.listTransactionTypes(transactionTypes);
    }
}
