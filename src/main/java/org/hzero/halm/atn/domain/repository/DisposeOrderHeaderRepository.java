package org.hzero.halm.atn.domain.repository;

import org.hzero.halm.atn.domain.entity.DisposeOrderLine;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.atn.domain.entity.DisposeOrderHeader;

import java.util.List;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * 资产处置单头资源库
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 11:05:34
 */
public interface DisposeOrderHeaderRepository extends BaseRepository<DisposeOrderHeader> {


    /**
     * 资产处置单头明细页
     * @param disposeHeaderId 资产处置单头id
     * @param tenantId 租户id
     * @return DisposeOrderHeader 返回结果
     */
    DisposeOrderHeader selectDetail(Long disposeHeaderId, Long tenantId);
    /**
     * 入口处理细页
     * @param disposeOrderHeader 查询条件
     * @return  返回结果
     */
    List<DisposeOrderLine> entryProcessDetail(DisposeOrderHeader disposeOrderHeader);
    /**
     *  资产处置单头明细页数据检索
     * @param pageRequest 分页参数
     * @param tenantId 租户id
     * @param condition 查询条件
     * @return Page<DisposeOrderHeader> 返回数据
     */
    Page<DisposeOrderHeader> selectListByCondition(PageRequest pageRequest, Long tenantId, String condition);
}
