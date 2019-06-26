package org.hzero.halm.fam.app.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.fam.api.dto.AssetCatalogDTO;
import org.hzero.halm.fam.app.service.AssetCatalogService;
import org.hzero.halm.fam.domain.entity.AssetCatalog;
import org.hzero.halm.fam.domain.repository.AssetCatalogRepository;
import org.hzero.halm.fam.infra.constant.Constants;
import org.hzero.halm.fam.infra.mapper.AssetCatalogMapper;
import org.hzero.halm.fam.infra.utils.TreeDataUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.choerodon.core.exception.CommonException;

/**
 * 资产目录应用服务默认实现
 *
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
@Service
public class AssetCatalogServiceImpl implements AssetCatalogService {

    @Autowired
    private AssetCatalogRepository assetCatalogRepository;
    @Autowired
    private AssetCatalogMapper assetCatalogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<AssetCatalogDTO> assetCatalogTreeList(AssetCatalog assetCatalog) {
        return assetCatalogRepository.assetCatalogTreeList(assetCatalog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<AssetCatalog> assetCatalogCreateAndEdit(Long tenantId, List<AssetCatalog> assetCatalogList) {
        for (AssetCatalog assetCatalog : assetCatalogList) {
            validateField(assetCatalog);
            assetCatalog.setTenantId(tenantId);
            //查询父节点数据
            if(assetCatalog.getParentCatalogId() !=null){
                AssetCatalog assetCatalogParent = assetCatalogRepository.selectByPrimaryKey(assetCatalog.getParentCatalogId());
                if(assetCatalogParent == null){
                    throw new CommonException(Constants.AafmErrorCode.PARENT_ID_HAS_NO_LEVEL);
                }
                assetCatalog.setLevelPath(String.join(BaseConstants.Symbol.SLASH,assetCatalogParent.getLevelPath(),assetCatalogParent.getAssetCatalogId().toString()));
                assetCatalog.setLevelNumber(assetCatalogParent.getLevelNumber()+1);
            }
            else {
                assetCatalog.setLevelPath(StringUtils.EMPTY);
                assetCatalog.setLevelNumber(Constants.DEFAULT_LEVEL_NUMBER);
            }

            if(Objects.isNull(assetCatalog.getAssetCatalogId())){
                assetCatalogRepository.insert(assetCatalog);
            }else{
                assetCatalogRepository.updateOptional(assetCatalog,AssetCatalog.FIELD_CATALOG_NAME,AssetCatalog.FIELD_PRODUCT_CATEGORY_ID,AssetCatalog.FIELD_CATALOG_CODE,
                        AssetCatalog.FIELD_RESIDUAL_VALUE_RATE,AssetCatalog.FIELD_DEPRECIATION_MONTH,AssetCatalog.FIELD_DEPRECIATION_TYPE_CODE,AssetCatalog.FIELD_ACCOUNT_TYPE_CODE,
                        AssetCatalog.FIELD_PARENT_CATALOG_ID,AssetCatalog.FIELD_LEVEL_PATH,AssetCatalog.FIELD_LEVEL_NUMBER,
                        AssetCatalog.FIELD_ENABLED_FLAG,AssetCatalog.FIELD_DESCRIPTION,AssetCatalog.FIELD_TENANT_ID);
            }
        }
        return assetCatalogList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<AssetCatalog> changeAssetCatalogEnabledFlag(Long tenantId, Long assetCatalogId, Integer enabledFlag) {
        AssetCatalog assetCatalog = assetCatalogRepository.selectByCondition(
                Condition.builder(AssetCatalog.class)
                        .andWhere(
                                Sqls.custom()
                                        .andEqualTo(AssetCatalog.FIELD_TENANT_ID, tenantId)
                                        .andEqualTo(AssetCatalog.FIELD_ASSET_CATALOG_ID, assetCatalogId)
                        )
                        .build()
        ).get(0);
        // 如果要启用操作，先判断父节点是不是启用状态。
        if (BaseConstants.Flag.YES.equals(enabledFlag) && assetCatalog.getParentCatalogId() != null) {
            AssetCatalog parentAssetCatalog = assetCatalogRepository.selectByPrimaryKey(assetCatalog.getParentCatalogId());
            if (!BaseConstants.Flag.YES.equals(parentAssetCatalog.getEnabledFlag())) {
                throw new CommonException(Constants.AafmErrorCode.PARENT_NOT_ENABLE);
            }
        }
        //获得树状结构。
        List<AssetCatalogDTO> result = new ArrayList<>();
        AssetCatalogDTO assetCatalogDTO = new AssetCatalogDTO();
        assetCatalogDTO.setAssetCatalogId(assetCatalogId);
        assetCatalogDTO.setObjectVersionNumber(assetCatalog.getObjectVersionNumber());
        result.add(assetCatalogDTO);
        if(BaseConstants.Flag.NO.equals(enabledFlag)) {
            //禁用,查询所有下级
            List<AssetCatalogDTO> childList = assetCatalogMapper.selectChildList(assetCatalog);
            result.addAll(childList);
        }
        result.forEach((child) -> AssetCatalog.operateAssetCatalog(child, enabledFlag));
        //转换成list对象，批量更新。
        TreeDataUtils<AssetCatalogDTO> treeDataUtil = new TreeDataUtils<>();
        List<AssetCatalog> updateList = new ArrayList<>();
        treeDataUtil.treeDataToList(result, AssetCatalog.class, updateList);
        assetCatalogRepository.batchUpdateOptional(updateList, AssetCatalog.FIELD_ENABLED_FLAG);
        return updateList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assetCatalogRemove(Long tenantId, List<AssetCatalog> assetCatalogList) {
        if (CollectionUtils.isEmpty(assetCatalogList)) {
            return;
        }
        assetCatalogList.forEach(assetCatalog -> {
            assetCatalog.setTenantId(tenantId);
            assetCatalogRepository.deleteByPrimaryKey(assetCatalog);
            //查询当前记录下，是否有子记录
//            AssetCatalog dbAssetCatalog = assetCatalogRepository.selectByPrimaryKey(assetCatalog);
//            if(Objects.isNull(assetCatalog.getAssetCatalogId())){
//                assetCatalogRepository.deleteByPrimaryKey(assetCatalog);
//            }else{
//                throw new CommonException(ArcvConstants.AafmErrorCode.THE_RECORD_CANNOT_BE_DELETED);
//            }
        });
    }

    private void validateField(AssetCatalog assetCatalog){
        if(assetCatalog.getResidualValueRate() != null){
            //校验残值率
            BigDecimal big1 = new BigDecimal(0);
            BigDecimal big2 = new BigDecimal(100);
            if((assetCatalog.getResidualValueRate().compareTo(big1) == -1) || (assetCatalog.getResidualValueRate().compareTo(big2) == 1)){
                throw new CommonException(Constants.AafmErrorCode.RESIDUAL_VALUE_RATE_NOT_IN_SCOPE,assetCatalog.getResidualValueRate());
            }
            //校验折旧月份
            if(assetCatalog.getDepreciationMonth() < Constants.ONE || assetCatalog.getDepreciationMonth() > Constants.TWELVE){
                throw new CommonException(Constants.AafmErrorCode.DEPRECIATION_MONTH_NOT_IN_SCOPE,assetCatalog.getDepreciationMonth());
            }
        }
    }
}
