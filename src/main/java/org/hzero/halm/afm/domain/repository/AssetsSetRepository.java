package org.hzero.halm.afm.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.afm.domain.entity.AssetsSet;
import org.hzero.mybatis.base.BaseRepository;

import java.util.List;

/**
 * 资产组资源库
 *
 * @author sen.wang@hand-china.com 2019-01-11 13:58:49
 */
public interface AssetsSetRepository extends BaseRepository<AssetsSet> {
    /**
     * 资产列表的查询
     * @param tenantId
     * @param pageRequest
     * @param assetsSet
     * @return
     */
    Page<AssetsSet> selectAssetList(Long tenantId, PageRequest pageRequest, AssetsSet assetsSet);

    /**
     * 导出接口用到的查询
     * @param tenantId
     * @param assetsSet
     * @return
     */
    List<AssetsSet> selectAllAssetList(Long tenantId, AssetsSet assetsSet);

    /**
     * 资产详情页面的列表查询
     * @param tenantId
     * @param pageRequest
     * @param detailCondition
     * @return
     */
    Page<AssetsSet> selectListByDetailCondition(Long tenantId, PageRequest pageRequest, String detailCondition);

    /**
     * 明细信息的查询
     * @param tenantId
     * @param assetsSetId
     * @return
     */
    AssetsSet selectDeatilAsset(Long tenantId, Long assetsSetId);
}
