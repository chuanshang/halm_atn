package org.hzero.halm.afm.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.afm.domain.entity.AssetSpecialty;
import org.hzero.halm.afm.domain.vo.AssetSpecialtyDetailExportVO;
import org.hzero.mybatis.base.BaseRepository;

import java.util.List;

/**
 * 资产专业定义(头)资源库
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:37
 */
public interface AssetSpecialtyRepository extends BaseRepository<AssetSpecialty> {

    /**
     *  查询可导出的资产专业数据
     * @param assetSpecialty 查询条件
     * @return List<AssetSpecialtyDetailExportVO>
     */
    List<AssetSpecialtyDetailExportVO> exportAssetSpecialtyByIds(AssetSpecialty assetSpecialty);

    /**
     *  条件查询资产专业头数据
     * @param pageRequest
     * @param tenantId 租户id
     * @param condition 查询条件
     * @return Page<AssetSpecialty>
     */
    Page<AssetSpecialty> selectListByCondition(PageRequest pageRequest, Long tenantId, String condition);
}
