package org.hzero.halm.afm.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.afm.api.dto.TransactionTypesDTO;
import org.hzero.halm.afm.domain.entity.TransactionTypes;

import java.util.List;

/**
 * 资产事务处理类型应用服务
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:43:01
 */
public interface TransactionTypesService {
    /**
     * 获取树列表的数据
     *
     * @param transactionTypes 查询条件
     * @param tenantId         租户ID
     * @return
     */
    List<TransactionTypesDTO> treeTransactionTypes(TransactionTypes transactionTypes, Long tenantId);

    /**
     * 新增或者编辑保存单条记录
     *
     * @param tenantId         租户ID
     * @param transactionTypes 详细信息
     * @return
     */
    TransactionTypes saveTransactionTypes(Long tenantId, TransactionTypes transactionTypes);

    /**
     * 根据ID获取事件类型详细信息
     *
     * @param tenantId          租户ID
     * @param transactionTypeId 事件类型ID
     * @return
     */
    TransactionTypes getTransactionTypes(Long tenantId, Long transactionTypeId);

    /**
     * 事物类型失效
     *
     * @param organizationId   租户ID
     * @param transactionTypes 项目模板
     * @return
     */
    TransactionTypes disabledTransactionTypes(Long organizationId, TransactionTypes transactionTypes);

    /**
     * 事物类型生效
     *
     * @param organizationId   租户ID
     * @param transactionTypes 项目模板
     * @return
     */
    TransactionTypes enabledTransactionTypes(Long organizationId, TransactionTypes transactionTypes);

    /**
     * 导出事件类型数据
     *
     * @param transactionTypesDto 查询条件
     * @return List<AttributeSetDetailExportVO>
     */
    List<TransactionTypesDTO> exportTransactionTypes(TransactionTypesDTO transactionTypesDto);

    /**
     * 获取列表的数据
     *
     * @param pageRequest pageRequest
     * @param transactionTypes  查询条件
     * @return Page<TransactionTypes>
     */
    Page<TransactionTypes> pageTransactionTypes(PageRequest pageRequest,TransactionTypes transactionTypes);
}
