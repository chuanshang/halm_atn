package org.hzero.halm.afm.app.service;

import org.hzero.halm.afm.domain.entity.AssetDynamicColumn;

import java.util.List;

/**
 * 资产动态字段配置应用服务
 *
 * @author wen.luo@hand-china.com 2019-04-02 16:06:20
 */
public interface AssetDynamicColumnService {

    /**
     * 批量保存资产动态字段配置
     *
     * @param tenantId 租户id
     * @param assetDynamicColumnList 新增或在修改的资产动态字段对象的集合
     * @author luowen 2019-04-02 5:48 PM
     * @return
     */
    List<AssetDynamicColumn> insertBatchAssetDynamicColumn (Long tenantId,List<AssetDynamicColumn> assetDynamicColumnList);
}
