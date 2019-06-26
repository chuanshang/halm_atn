package org.hzero.halm.atn.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.atn.domain.entity.DisposeOrderHeader;
import org.hzero.halm.atn.domain.entity.DisposeOrderLine;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;

import java.util.List;

import io.choerodon.mybatis.common.BaseMapper;

/**
 * 资产处置单行Mapper
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 15:48:13
 */
public interface DisposeOrderLineMapper extends BaseMapper<DisposeOrderLine> {

    /**
     * 资产处置单行查询
     * @param disposeHeaderId 资产处置单头id
     * @param tenantId 租户id
     * @return List<DisposeOrderLine> 返回数据
     */
    List<DisposeOrderLine> selectLineList(@Param("disposeHeaderId") Long disposeHeaderId, @Param("tenantId") Long tenantId);

    /**
     *  入口&处理页面详情查询
     * @param disposeOrderHeader 查询条件
     * @return 返回值
     */
    List<DisposeOrderLine> entryProcess(DisposeOrderHeader disposeOrderHeader);

    /**
     * 查询最大行号
     * @param disposeOrderHeaderId 查询条件
     * @return 最大行号
     */
    Integer selectMaxLineNum(@Param("disposeOrderHeaderId") Long disposeOrderHeaderId);

    /**
     * 查询每行的动态字段集合
     * @param disposeOrderLine 查询参数
     * @return 返回值
     */
    List<OrderDynamicColumn> selectDynamicList(DisposeOrderLine disposeOrderLine);

    /**
     * 查询资产状态是‘已处置’的id
     * @param tenantId 租户id
     * @return 返回值
     */
    Long getTargetAssetStatusId(@Param("tenantId")Long tenantId);
}
