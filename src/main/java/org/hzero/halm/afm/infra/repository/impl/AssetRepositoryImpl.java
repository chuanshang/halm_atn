package org.hzero.halm.afm.infra.repository.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.CaseFormat;
import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.entity.AssetLinear;
import org.hzero.halm.afm.domain.repository.AssetLinearRepository;
import org.hzero.halm.afm.domain.repository.AssetRepository;
import org.hzero.halm.afm.infra.mapper.AssetMapper;
import org.hzero.halm.atn.app.service.OrderDynamicColumnService;
import org.hzero.halm.atn.domain.entity.ChangeOrderLine;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 资产/设备基本信息 资源库实现
 *
 * @author zhisheng.zhang@hand-china.com 2019-01-24 16:43:37
 */
@Component
public class AssetRepositoryImpl extends BaseRepositoryImpl<Asset> implements AssetRepository {

    @Autowired
    AssetMapper assetMapper;
    @Autowired
    private OrderDynamicColumnService orderDynamicColumnService;
    @Autowired
    AssetLinearRepository assetLinearRepository;

    @Override
    public List<Asset> selectAssetList(Asset queryAsset) {
        List<Asset> assetList = assetMapper.selectAssetList(queryAsset);

        Set<Long> assetIds = assetList.stream().map(Asset::getAssetId).collect(Collectors.toSet());

        List<AssetLinear> assetLinearList= assetLinearRepository.selectByCondition(org.hzero.mybatis.domian.Condition.builder(AssetLinear.class)
                .andWhere(
                        org.hzero.mybatis.util.Sqls.custom()
                                .andEqualTo(AssetLinear.FIELD_TENANT_ID, queryAsset.getTenantId(),true)
                                .andIn(AssetLinear.FIELD_ASSET_ID,assetIds,true)
                ).build());

        Map<Long,AssetLinear> assetLinearMap = assetLinearList.stream().collect(Collectors.toMap(e -> e.getAssetId(), e -> e));
        assetList.forEach(asset->asset.setAssetLinear(assetLinearMap.get(asset.getAssetId())));

        return assetList;
    }

    @Override
    public List<Asset> selectAssetByDetailCondition(Long organizationId,String detailCondition,String organizationScope,String statusScope,String specialtyScope) {
        List<Long> organizationIds = (List<Long>) JSONArray.parse(organizationScope);
        List<Long> statusIds = (List<Long>) JSONArray.parse(statusScope);
        List<Long> specialtyIds = (List<Long>) JSONArray.parse(specialtyScope);
        return assetMapper.selectAssetByDetailCondition(organizationId,detailCondition,organizationIds,statusIds,specialtyIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAssetByChangeOrder(ChangeOrderLine line, List<OrderDynamicColumn> orderDynamicColumns, Asset asset) {

        // 资产台账变更
        if (!Objects.isNull(line)){
            asset.setAssetStatusId(line.getTargetAssetStatusId());
            asset.setOwningPersonId(line.getTargetOwningPersonId());
            asset.setUserPersonId(line.getTargetUsingPersonId());
            asset.setAssetLocationId(line.getTargetLocationId());
        }

        updateAssetOnDynamicColumn(orderDynamicColumns,asset);

    }

    @Override
    public void updateAssetOnDynamicColumn(List<OrderDynamicColumn> orderDynamicColumns, Asset asset) {
        if (CollectionUtils.isNotEmpty(orderDynamicColumns)){
            for (OrderDynamicColumn changeDetail : orderDynamicColumns) {
                String targetColumnType = changeDetail.getTargetColumnType();
                String columnName = changeDetail.getCurrentColumnName();
                String targetValue = changeDetail.getTargetColumnValue();
                try {
                    Object targetTypeValue = orderDynamicColumnService.columnTypeConversion(targetColumnType, targetValue);
                    FieldUtils.writeDeclaredField(asset, CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,columnName), targetTypeValue, true);
                } catch (IllegalAccessException e) {
                    throw new CommonException(e);
                }
            }
        }
        this.updateByPrimaryKey(asset);
    }
}
