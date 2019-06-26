package org.hzero.halm.afm.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.app.service.AssetSpecialtyService;
import org.hzero.halm.afm.app.service.AssetToOrgService;
import org.hzero.halm.afm.domain.entity.AssetSpecialty;
import org.hzero.halm.afm.domain.entity.AssetToOrg;
import org.hzero.halm.afm.domain.repository.AssetSpecialtyRepository;
import org.hzero.halm.afm.domain.repository.AssetToOrgRepository;
import org.hzero.halm.afm.domain.vo.AssetSpecialtyDetailExportVO;
import org.hzero.halm.afm.domain.vo.AssetToOrgDetailExportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 资产专业定义(头)应用服务默认实现
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:37
 */
@Service
public class AssetSpecialtyServiceImpl implements AssetSpecialtyService {
    @Autowired
    private AssetSpecialtyRepository assetSpecialtyRepository;

    @Autowired
    private AssetToOrgService assetToOrgService;

    @Autowired
    private AssetToOrgRepository assetToOrgRepository;

    @Autowired
    private LovValueHandle lovValueHandle;

    @Override
    public Page<AssetSpecialty> pageAssetSpecialty(PageRequest pageRequest, Long organizationId,
                                                   AssetSpecialty queryAssetSpecialty) {
        queryAssetSpecialty.setTenantId(organizationId);
        return PageHelper.doPageAndSort(pageRequest, () -> {
            return assetSpecialtyRepository.selectByCondition(org.hzero.mybatis.domian.Condition
                            .builder(AssetSpecialty.class)
                            .andWhere(org.hzero.mybatis.util.Sqls.custom()
                                            .andEqualTo(AssetSpecialty.FIELD_TENANT_ID,
                                                            queryAssetSpecialty.getTenantId(), true)
                                            .andEqualTo(AssetSpecialty.FIELD_ENABLED_FLAG,
                                                            queryAssetSpecialty.getEnabledFlag(), true)
                                            .andLike(AssetSpecialty.FIELD_ASSET_SPECIALTY_NAME,
                                                            queryAssetSpecialty.getAssetSpecialtyName(), true))
                            .build());
        });

    }

    @Override
    public AssetSpecialty assetSpecialtyDetail(PageRequest pageRequest, Long assetSpecialtyId) {
        // 查询头记录
        AssetSpecialty assetSpecialty = assetSpecialtyRepository.selectByPrimaryKey(assetSpecialtyId);
        if (assetSpecialty == null) {
            throw new CommonException(BaseConstants.ErrorCode.DATA_NOT_EXISTS);
        }
        // 查询行记录
        List<AssetToOrg> assetToOrgList = assetToOrgService.pageAndSortBySpecialtyId(pageRequest, assetSpecialtyId);
        assetSpecialty.setAssetToOrgList(assetToOrgList);
        return assetSpecialty;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createAndUpdate(Long organizationId, AssetSpecialty assetSpecialty) {
        assetSpecialty.setTenantId(organizationId);
        // 校验唯一性
        assetSpecialty.validateAssetSpecialty(organizationId, assetSpecialtyRepository);

        // 保存或更新数据
        if (assetSpecialty.getAssetSpecialtyId() == null) {
            assetSpecialtyRepository.insertSelective(assetSpecialty);
        } else {
            assetSpecialtyRepository.updateOptional(assetSpecialty, AssetSpecialty.FIELD_ASSET_SPECIALTY_NAME,
                            AssetSpecialty.FIELD_DESCRIPTION, AssetSpecialty.FIELD_ENABLED_FLAG);
        }

        // 保存或更新行数据
        batchCreateAndUpdateAssetToOrg(organizationId, assetSpecialty.getAssetSpecialtyId(),
                        assetSpecialty.getAssetToOrgList());

    }

    void batchCreateAndUpdateAssetToOrg(Long organizationId, Long assetSpecialtyId, List<AssetToOrg> assetToOrgList) {
        assetToOrgList.forEach(assetToOrg -> {
            assetToOrg.setTenantId(organizationId);
            assetToOrg.setAssetSpecialtyId(assetSpecialtyId);
            // 行校验
            assetToOrg.validateAssetToOrg(organizationId, assetSpecialtyId, assetToOrgRepository);

            // 判断新增还是更新数据
            if (assetToOrg.getAssetOrgId() == null) {
                assetToOrgRepository.insertSelective(assetToOrg);
            } else {
                assetToOrgRepository.updateOptional(assetToOrg, AssetToOrg.FIELD_MAINT_SITES_ID,
                                AssetToOrg.FIELD_MAJOR_DEPARTMENT_ID, AssetToOrg.FIELD_MANAGE_DEPARTMENT_ID,
                                AssetToOrg.FIELD_PRIORITY, AssetToOrg.FIELD_ENABLED_FLAG);
            }

        });
    }

    @Override
    @ProcessLovValue(targetField = {"","assetToOrgDetailExportVOList"})
    public List<AssetSpecialtyDetailExportVO> exportAssetSpecialtyByIds(AssetSpecialty assetSpecialty) {
        // 查询头
        List<AssetSpecialtyDetailExportVO> assetSpecialtyDetailExportList =
                        assetSpecialtyRepository.exportAssetSpecialtyByIds(assetSpecialty);
        // 提取查询的头id
        Set<Long> assetSpecialtyIdSet = assetSpecialtyDetailExportList.stream()
                        .map(AssetSpecialtyDetailExportVO::getAssetSpecialtyId).collect(Collectors.toSet());
        // 根据头id查询行
        List<AssetToOrgDetailExportVO> assetToOrgDetailExportList =
                        assetToOrgRepository.exportAssetToOrgBySpecialtyId(StringUtils.join(assetSpecialtyIdSet, ','));
        // 根据头id进行分组
        Map<Long, List<AssetToOrgDetailExportVO>> groupMap = assetToOrgDetailExportList.stream()
                        .collect(Collectors.groupingBy(AssetToOrgDetailExportVO::getAssetSpecialtyId));
        // 将行数据放入到头中
        assetSpecialtyDetailExportList.forEach(
                        assetSpecialtyDetailExport -> assetSpecialtyDetailExport.setAssetToOrgDetailExportVOList(
                                        (groupMap.get(assetSpecialtyDetailExport.getAssetSpecialtyId()))));

        // 值集翻译
        return assetSpecialtyDetailExportList;
    }
}
