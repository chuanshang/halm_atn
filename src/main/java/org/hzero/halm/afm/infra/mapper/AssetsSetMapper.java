package org.hzero.halm.afm.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hzero.halm.afm.domain.entity.AssetsSet;

import java.util.List;

/**
 * 资产组Mapper
 *
 * @author sen.wang@hand-china.com 2019-01-11 13:58:49
 */
public interface AssetsSetMapper extends BaseMapper<AssetsSet> {

    /**
     * 资产组列表页的查询
     * @param assetsSet
     * @return
     */
    List<AssetsSet> selectAssetList(AssetsSet assetsSet);

    /**
     * 资产组详情页的列表查询
     * @param
     * @return
     */
    List<AssetsSet> selectListByDetailCondition(@Param("tenantId") Long tenantId, @Param("detailCondition") String detailCondition);
}
