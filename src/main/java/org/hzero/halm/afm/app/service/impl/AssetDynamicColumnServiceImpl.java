package org.hzero.halm.afm.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.hzero.halm.afm.app.service.AssetDynamicColumnService;
import org.hzero.halm.afm.domain.entity.AssetDynamicColumn;
import org.hzero.halm.afm.domain.repository.AssetDynamicColumnRepository;
import org.hzero.halm.afm.infra.constant.Constants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资产动态字段配置应用服务默认实现
 *
 * @author wen.luo@hand-china.com 2019-04-02 16:06:20
 */
@Service
public class AssetDynamicColumnServiceImpl implements AssetDynamicColumnService {

    @Autowired
    private AssetDynamicColumnRepository assetDynamicColumnRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<AssetDynamicColumn> insertBatchAssetDynamicColumn(Long tenantId, List<AssetDynamicColumn> assetDynamicColumnList) {
        for (AssetDynamicColumn assetDynamicColumn : assetDynamicColumnList) {
            assetDynamicColumn.setTenantId(tenantId);
            //字段唯一性校验
            this.validUniqueFields(assetDynamicColumn);
            if (assetDynamicColumn.getColumnId() == null) {
                assetDynamicColumnRepository.insert(assetDynamicColumn);
            } else {
                assetDynamicColumnRepository.updateOptional(assetDynamicColumn,
                        AssetDynamicColumn.FIELD_COLUMN_CLASS,
                        AssetDynamicColumn.FIELD_COLUMN_CODE,
                        AssetDynamicColumn.FIELD_COLUMN_NAME,
                        AssetDynamicColumn.FIELD_DESC_CODE,
                        AssetDynamicColumn.FIELD_DESC_SOURCE,
                        AssetDynamicColumn.FIELD_DESC_SOURCE_TYPE,
                        AssetDynamicColumn.FIELD_LOV_NAME,
                        AssetDynamicColumn.FIELD_LOV_TYPE);
            }
        }

        return assetDynamicColumnList;
    }

    /**
     * 字段唯一性校验
     *
     * @param assetDynamicColumn 资产动态字段
     * @author luowen 2019-04-02 5:54 PM
     */
    private void validUniqueFields(AssetDynamicColumn assetDynamicColumn) {

        //组装条件语句
        Sqls listAssetDynamicColumnSql = Sqls.custom()
                .andEqualTo(AssetDynamicColumn.FIELD_LOV_TYPE, assetDynamicColumn.getTenantId())
                .andEqualTo(AssetDynamicColumn.FIELD_COLUMN_CODE, assetDynamicColumn.getColumnCode());

        if (assetDynamicColumn.getColumnId() != null) {
            listAssetDynamicColumnSql = listAssetDynamicColumnSql.andNotEqualTo(AssetDynamicColumn.FIELD_COLUMN_ID, assetDynamicColumn.getColumnId());
        }

        if (assetDynamicColumnRepository.selectByCondition(Condition.builder(AssetDynamicColumn.class)
                .andWhere(listAssetDynamicColumnSql)
                .build()).size() > 0) {
            throw new CommonException(Constants.AafmErrorCode.ASSET_DYNAMIC_COLUMN_DUPLICATED, assetDynamicColumn.getColumnCode());
        }
    }


}
