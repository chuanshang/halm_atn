package org.hzero.halm.afm.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 资产事务处理类型扩展控制字段Mapper
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:42:58
 */
public interface TransactionTypeFieldsMapper extends BaseMapper<TransactionTypeFields> {

    /**
     * 根据ID查询事件类型字段
     *
     * @param tenantId         租户ID
     * @param transactionTypesId 事件类型Id
     * @return
     */
    List<TransactionTypeFields> listTransactionTypeFields(@Param("tenantId") Long tenantId,@Param("transactionTypesId")  Long transactionTypesId);

}
