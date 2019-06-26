package org.hzero.halm.afm.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.afm.api.dto.TransactionTypesDTO;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 资产事务处理类型Mapper
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:43:01
 */
public interface TransactionTypesMapper extends BaseMapper<TransactionTypes> {

    /**
     * 根据条件查询事件类型，并查询出和它相关的所有父节点和子节点。
     * @param transactionTypes 查询条件
     * @return 查询结果
     */
    List<TransactionTypesDTO> selectAllNodesTransactionTypes(TransactionTypes transactionTypes);

    /**
     * 根据ID获取事件类型详细信息
     * @param tenantId 租户ID
     * @param transactionTypeId 事件类型ID
     * @return
     */
    TransactionTypes getTransactionTypes(@Param("tenantId") Long tenantId,@Param("transactionTypeId") Long transactionTypeId);

    /**
     * 根据条件查询事件类型，并查询出和它相关的所有子节点。
     * @param transactionTypes 查询条件
     * @return 查询结果
     */
    List<TransactionTypes> selectNodeAndChildNodeTransactionTypes(TransactionTypes transactionTypes);

    /**
     * 查询可导出的事件类型
     * @param transactionTypesDTO 查询条件
     * @return List<AttributeSetDetailExportVO>
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
