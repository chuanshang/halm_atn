package org.hzero.halm.atn.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.atn.domain.entity.DisposeOrderHeader;

import java.util.List;

import io.choerodon.mybatis.common.BaseMapper;

/**
 * 资产处置单头Mapper
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 11:05:34
 */
public interface DisposeOrderHeaderMapper extends BaseMapper<DisposeOrderHeader> {

    /**
     *资产处置单头列表页查询
     * @param disposeOrderHeader 查询参数
     * @return 返回结果
     */
   List<DisposeOrderHeader> pageDisposeOrderHeaderList(DisposeOrderHeader disposeOrderHeader);

    /**
     * 资产处置单头明细页数据检索
     * @param tenantId 分页参数
     * @param condition 检索条件
     * @return List<DisposeOrderHeader> 返回结果
     */
   List<DisposeOrderHeader> selectListByCondition(@Param("tenantId") Long tenantId, @Param("condition") String condition);

    /**
     * 根据行id查询
     * @param disposeOrderHeader 查询参数
     * @return 返回值
     */
    DisposeOrderHeader getLineById(DisposeOrderHeader disposeOrderHeader);
    /**
     *资产处置单头列表页查询
     * @param disposeOrderHeader 查询参数
     * @return 返回结果
     */
    DisposeOrderHeader getDisposeOrderHeader(DisposeOrderHeader disposeOrderHeader);
}
