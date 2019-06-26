package org.hzero.halm.afm.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.afm.domain.entity.AssetSpecialty;
import org.hzero.halm.afm.domain.vo.AssetSpecialtyDetailExportVO;

import java.util.List;

/**
 * 资产专业定义(头)应用服务
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:37
 */
public interface AssetSpecialtyService {

    /**
     * 条件查询资产专业信息
     * @param pageRequest
     * @param organizationId 租户Id
     * @param assetSpecialty
     * @return
     */
    Page<AssetSpecialty> pageAssetSpecialty(PageRequest pageRequest, Long organizationId, AssetSpecialty assetSpecialty);

    /**
     * 条件查询资产专业信息
     * @param pageRequest pageRequest
     * @param assetSpecialtyId 资产专业ID
     * @return
     */
    AssetSpecialty assetSpecialtyDetail(PageRequest pageRequest, Long assetSpecialtyId);

    /**
     * 资产专业新增或更新
     * @param organizationId 租户Id
     * @param assetSpecialty 资产专业
     */
    void createAndUpdate(Long organizationId, AssetSpecialty assetSpecialty);

    /**
     * 头行导出资产专业数据
     * @param assetSpecialty
     * @return
     */
    List<AssetSpecialtyDetailExportVO> exportAssetSpecialtyByIds(AssetSpecialty assetSpecialty);

}
