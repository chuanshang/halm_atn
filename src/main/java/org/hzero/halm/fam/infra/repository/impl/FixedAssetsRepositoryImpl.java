package org.hzero.halm.fam.infra.repository.impl;

import org.hzero.halm.fam.api.dto.FixedAssetsDTO;
import org.hzero.halm.fam.infra.mapper.FixedAssetsMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.fam.domain.entity.FixedAssets;
import org.hzero.halm.fam.domain.repository.FixedAssetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 固定资产 资源库实现
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:22:24
 */
@Component
public class FixedAssetsRepositoryImpl extends BaseRepositoryImpl<FixedAssets> implements FixedAssetsRepository {

    @Autowired
    FixedAssetsMapper fixedAssetsMapper;

    @Override
    public List<FixedAssetsDTO> listFixedAssets(Long tenantId,FixedAssets fixedAssets) {
        fixedAssets.setTenantId(tenantId);
        return fixedAssetsMapper.listFixedAssets(fixedAssets);
    }

    @Override
    public FixedAssetsDTO getFixedAssets(Long tenantId, Long fixedAssetId) {
        return fixedAssetsMapper.getFixedAssets(tenantId,fixedAssetId);
    }

    @Override
    public List<FixedAssetsDTO> exportFixedAssets(FixedAssetsDTO fixedAssets) {
        return fixedAssetsMapper.exportFixedAssets(fixedAssets);
    }
}
