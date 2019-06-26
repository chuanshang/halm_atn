package org.hzero.halm.afm.infra.repository.impl;

import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
import org.hzero.halm.afm.infra.mapper.TransactionTypeFieldsMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资产事务处理类型扩展控制字段 资源库实现
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:42:58
 */
@Component
public class TransactionTypeFieldsRepositoryImpl extends BaseRepositoryImpl<TransactionTypeFields> implements TransactionTypeFieldsRepository {

    @Autowired
    TransactionTypeFieldsMapper transactionTypeFieldsMapper;

    @Override
    public List<TransactionTypeFields> listTransactionTypeFields(Long tenantId, Long transactionTypesId) {
        return transactionTypeFieldsMapper.listTransactionTypeFields(tenantId,transactionTypesId);
    }

    @Override
    public Map<String, String> getFieldType(Long tenantId, Long transactionTypesId) {
        Map<String, String> fieldRulesMap = this.selectByCondition(Condition.builder(TransactionTypeFields.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(TransactionTypeFields.FIELD_TRANSATION_TYPE_ID,transactionTypesId)
                        .andEqualTo(TransactionTypeFields.FIELD_TENANT_ID, tenantId))
                .build())
                .stream().collect(Collectors.toMap(TransactionTypeFields::getFieldColumn, TransactionTypeFields::getFieldType, (k1, k2) -> k1));
        return fieldRulesMap;
    }
}
