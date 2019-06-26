package org.hzero.halm.rcv.app.service;

import org.hzero.halm.rcv.domain.entity.AcceptanceType;

import java.util.List;

/**
 * 验收单类型应用服务
 *
 * @author zhiguang.guo@hand-china.com 2019-04-17 17:55:24
 */
public interface AcceptanceTypeService {

    /**
     * 新建和编辑
     * @param tenantId 租户id
     * @param acceptanceType 新建/编辑数据
     * @return 返回值
     */
    AcceptanceType acceptanceTypeCreateAndEdit(Long tenantId,AcceptanceType acceptanceType);

    /**
     * 明细查询
     * @param tenantId 租户id
     * @param acceptanceTypeId 明细id
     * @return 返回值
     */
    AcceptanceType acceptanceTypeDetail(Long tenantId,Long acceptanceTypeId);

    /**
     * 根据文字转编码
     * @param tenantId 租户id
     * @param condition 查询条件
     * @param lovCode 值集编码
     * @return 返回值
     */
    List<String> getListByCondition(Long tenantId, String condition ,String lovCode);

    /**
     * 验收单类型启用/禁用标记
     * @param tenantId 租户ID
     * @param acceptanceTypeId 验收单类型id
     * @param enabledFlag 启用/禁用标记
     * @return 返回值
     */
    AcceptanceType changeAssetCatalogEnabledFlag(Long tenantId,Long acceptanceTypeId,Integer enabledFlag);

    /**
     * 根据accTypeCondition为否为空得到accTypeCodeList
     * @param accTypeCondition 查询条件
     * @return 返回值
     */
    List<String> getListByAccTypeCondition(String accTypeCondition);

}
