package org.hzero.halm.afm.app.service;

import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.afm.domain.entity.AssetToOrg;

import java.util.List;

/**
 * 资产专业-组织分配(行)应用服务
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:39
 */
public interface AssetToOrgService {


    /**
     *  条件查询资产专业-组织分配数据
     * @param pageRequest
     * @param assetSpecialtyId
     * @return
     */
    List<AssetToOrg> pageAndSortBySpecialtyId(PageRequest pageRequest, Long assetSpecialtyId);


}
