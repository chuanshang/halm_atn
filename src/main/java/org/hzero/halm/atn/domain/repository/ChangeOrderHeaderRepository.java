package org.hzero.halm.atn.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;

/**
 * 资产信息变更单头资源库
 *
 * @author like.zhang@hand-china.com 2019-03-26 10:20:34
 */
public interface ChangeOrderHeaderRepository extends BaseRepository<ChangeOrderHeader> {

    /**
     * 分页查询资产信息变更单
     *
     * @param pageRequest       分页参数
     * @param changeOrderHeader 查询参数
     * @return 查询结果
     */
    Page<ChangeOrderHeader> pageChangeOrder(PageRequest pageRequest, ChangeOrderHeader changeOrderHeader);
}
