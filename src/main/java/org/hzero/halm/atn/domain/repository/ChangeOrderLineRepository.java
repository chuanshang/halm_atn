package org.hzero.halm.atn.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;
import org.hzero.halm.atn.domain.entity.ChangeOrderLine;
import org.hzero.mybatis.base.BaseRepository;

import java.util.List;

/**
 * 资产信息变更单行资源库
 *
 * @author like.zhang@hand-china.com 2019-03-26 11:49:44
 */
public interface ChangeOrderLineRepository extends BaseRepository<ChangeOrderLine> {

    /**
     * 资产信息变更单行列表查询
     *
     * @param changeOrderHeader 查询参数
     * @return 查询结果
     */
    Page<ChangeOrderLine> listChangeOrderLine(ChangeOrderHeader changeOrderHeader, PageRequest pageRequest);

    /**
     * 行插入
     *
     * @param tenantId 租户ID
     * @param changeHeaderId 头ID
     * @param changeNum 头编码
     * @param changeOrderLine 插入行信息
     */
    void insertChangeOrderLine(Long tenantId, Long changeHeaderId, String changeNum, ChangeOrderLine changeOrderLine);

}
