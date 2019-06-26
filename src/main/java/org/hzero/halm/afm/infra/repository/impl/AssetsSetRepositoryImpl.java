package org.hzero.halm.afm.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.halm.afm.domain.entity.AssetsSet;
import org.hzero.halm.afm.domain.repository.AssetsSetRepository;
import org.hzero.halm.afm.infra.mapper.AssetsSetMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资产组 资源库实现
 *
 * @author sen.wang@hand-china.com 2019-01-11 13:58:49
 */
@Component
public class AssetsSetRepositoryImpl extends BaseRepositoryImpl<AssetsSet> implements AssetsSetRepository {

    @Autowired
    AssetsSetMapper assetsSetMapper;
    @Autowired
    LovValueHandle lovValueHandle;

    @Override
    @ProcessLovValue
    public Page<AssetsSet> selectAssetList(Long tenantId, PageRequest pageRequest, AssetsSet assetsSet) {
        assetsSet.setTenantId(tenantId);
        return PageHelper.doPageAndSort(pageRequest, () -> assetsSetMapper.selectAssetList(assetsSet));
    }

    @Override
    @ProcessLovValue
    public List<AssetsSet> selectAllAssetList(Long tenantId, AssetsSet assetsSet) {
        assetsSet.setTenantId(tenantId);
        return assetsSetMapper.selectAssetList(assetsSet);
    }

    @Override
    @ProcessLovValue
    public Page<AssetsSet> selectListByDetailCondition(Long tenantId, PageRequest pageRequest, String detailCondition) {
        return PageHelper.doPageAndSort(pageRequest, () -> assetsSetMapper.selectListByDetailCondition(tenantId,detailCondition));
    }

    @Override
    public AssetsSet selectDeatilAsset(Long tenantId, Long assetsSetId) {
        AssetsSet set = new AssetsSet();
        set.setAssetsSetId(assetsSetId);
        set.setTenantId(tenantId);
        List<AssetsSet> list = assetsSetMapper.selectAssetList(set);

        if(CollectionUtils.isNotEmpty(list)){
            return (AssetsSet) lovValueHandle.process(null,list.get(0));
        }else {
            return new AssetsSet();
        }
    }
}
