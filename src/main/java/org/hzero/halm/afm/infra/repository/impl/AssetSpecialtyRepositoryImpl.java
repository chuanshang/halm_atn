package org.hzero.halm.afm.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.halm.afm.domain.entity.AssetSpecialty;
import org.hzero.halm.afm.domain.repository.AssetSpecialtyRepository;
import org.hzero.halm.afm.domain.vo.AssetSpecialtyDetailExportVO;
import org.hzero.halm.afm.infra.mapper.AssetSpecialtyMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 资产专业定义(头) 资源库实现
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:37
 */
@Component
public class AssetSpecialtyRepositoryImpl extends BaseRepositoryImpl<AssetSpecialty> implements AssetSpecialtyRepository {

    @Autowired
    private AssetSpecialtyMapper assetSpecialtyMapper;

    @Override
    public List<AssetSpecialtyDetailExportVO> exportAssetSpecialtyByIds(AssetSpecialty assetSpecialty) {
        List<AssetSpecialtyDetailExportVO> assetSpecialtyDetailExportVOList = assetSpecialtyMapper.exportAttributeSetByIds(assetSpecialty);
        if (CollectionUtils.isEmpty(assetSpecialtyDetailExportVOList)){
            assetSpecialtyDetailExportVOList = new ArrayList<>();
        }
        return assetSpecialtyDetailExportVOList;
    }

    @Override
    /*@ProcessLovValue*/
    public Page<AssetSpecialty> selectListByCondition(PageRequest pageRequest, Long tenantId, String condition) {
        return PageHelper.doPageAndSort(pageRequest,()->assetSpecialtyMapper.selectListByCondition(tenantId,condition));
    }
}
