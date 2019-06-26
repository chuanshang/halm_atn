package org.hzero.halm.afm.domain.repository;

import org.hzero.halm.afm.api.dto.TransactionTypesDTO;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.afm.domain.entity.TransactionTypes;

import java.util.List;

/**
 * 资产事务处理类型资源库
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:43:01
 */
public interface TransactionTypesRepository extends BaseRepository<TransactionTypes> {
    /**
     * 根据ID获取事件类型详细信息
     *
     * @param tenantId          租户ID
     * @param transactionTypeId 事件类型ID
     * @return
     */
    TransactionTypes getTransactionTypes(Long tenantId, Long transactionTypeId);

    /**
     * 根据ID查询事件类型
     *
     * @param tenantId         租户ID
     * @param transactionTypes 事件类型
     * @return
     */
    List<TransactionTypes> selectNodeAndChildNodeTransactionTypes(Long tenantId, TransactionTypes transactionTypes);

    /**
     * 查询可导出的事件类型
     *
     * @param transactionTypesDTO 查询条件
     * @return List<TransactionTypesDTO>
     */
    List<TransactionTypesDTO> exportTransactionTypes(TransactionTypesDTO transactionTypesDTO);

    /**
     * 查询事件类型list
     *
     * @param transactionTypes 查询条件
     * @return List<TransactionTypesDTO>
     */
    List<TransactionTypes> listTransactionTypes(TransactionTypes transactionTypes);

}
