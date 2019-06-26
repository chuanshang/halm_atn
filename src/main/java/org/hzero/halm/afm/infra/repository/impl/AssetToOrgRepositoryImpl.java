package org.hzero.halm.afm.infra.repository.impl;

import org.hzero.halm.afm.domain.entity.AssetToOrg;
import org.hzero.halm.afm.domain.repository.AssetToOrgRepository;
import org.hzero.halm.afm.domain.vo.AssetToOrgDetailExportVO;
import org.hzero.halm.afm.infra.mapper.AssetToOrgMapper;
import org.hzero.halm.afm.infra.utils.IdsExportConvert;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资产专业-组织分配(行) 资源库实现
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:39
 */
@Component
public class AssetToOrgRepositoryImpl extends BaseRepositoryImpl<AssetToOrg> implements AssetToOrgRepository {

    @Autowired
    private AssetToOrgMapper assetToOrgMapper;

    @Override
    public List<AssetToOrgDetailExportVO> exportAssetToOrgBySpecialtyId(String assetSpecialtyIds) {
        List<AssetToOrgDetailExportVO> assetToOrgDetailExportList = assetToOrgMapper.exportAssetToOrgBySpecialtyId(IdsExportConvert.stringToList(assetSpecialtyIds));
        return assetToOrgDetailExportList;
    }
}
