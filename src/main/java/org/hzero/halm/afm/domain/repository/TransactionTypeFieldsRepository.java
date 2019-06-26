package org.hzero.halm.afm.domain.repository;

import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.mybatis.base.BaseRepository;

import java.util.List;
import java.util.Map;

/**
 * 资产事务处理类型扩展控制字段资源库
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:42:58
 */
public interface TransactionTypeFieldsRepository extends BaseRepository<TransactionTypeFields> {
    /**
     * 根据ID查询事件类型字段
     *
     * @param tenantId         租户ID
     * @param transactionTypesId 事件类型Id
     * @return 结果
     */
    List<TransactionTypeFields> listTransactionTypeFields(Long tenantId, Long transactionTypesId);

    /**
     * 获取类型字段
     * @param tenantId tenantId
     * @param transactionTypesId transactionTypesId
     * @return 结果
     */
    Map<String,String> getFieldType(Long tenantId, Long transactionTypesId);
}
