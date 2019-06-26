package org.hzero.halm.afm.app.service.impl;

import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.afm.app.service.AssetToOrgService;
import org.hzero.halm.afm.domain.entity.AssetToOrg;
import org.hzero.halm.afm.infra.mapper.AssetToOrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资产专业-组织分配(行)应用服务默认实现
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:39
 */
@Service
public class AssetToOrgServiceImpl implements AssetToOrgService {
    @Autowired
    private AssetToOrgMapper assetToOrgMapper;

    @Override
    public List<AssetToOrg> pageAndSortBySpecialtyId(PageRequest pageRequest, Long assetSpecialtyId) {

        return PageHelper.doPageAndSort(pageRequest, () -> {
            return assetToOrgMapper.selectBySpecialtyId(assetSpecialtyId);
        });
    }
}
