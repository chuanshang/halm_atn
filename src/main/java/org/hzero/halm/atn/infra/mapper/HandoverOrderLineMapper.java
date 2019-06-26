package org.hzero.halm.atn.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.atn.domain.entity.HandoverOrderLine;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 资产移交归还单行Mapper
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:48:48
 */
public interface HandoverOrderLineMapper extends BaseMapper<HandoverOrderLine> {

    /**
     * 根据headerId查询行的数据。（关联了资产表）
     * @param handoverHeaderId 头id
     * @param tenantId tenantId
     * @return 行数据
     */
    List<HandoverOrderLine> selectLineList(@Param("handoverHeaderId")Long handoverHeaderId, @Param("tenantId")Long tenantId);

    /**
     * 查询某 headId下的行号最大值。
     * @param line line
     * @return 行号最大值
     */
    Long selectMaxNum(HandoverOrderLine line);

    /**
     * 查询行数据，并关联头表
     * @param line line
     * @return 结果
     */
    List<HandoverOrderLine> selectLineAndHeader(HandoverOrderLine line);
}
