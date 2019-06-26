package org.hzero.halm.atn.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;
import org.hzero.halm.atn.domain.entity.ChangeOrderLine;

import java.util.List;

/**
 * 资产信息变更单行Mapper
 *
 * @author like.zhang@hand-china.com 2019-03-26 11:49:44
 */
public interface ChangeOrderLineMapper extends BaseMapper<ChangeOrderLine> {

    /**
     * 资产信息变更单行列表查询
     *
     * @param changeOrderHeader 查询参数
     * @return 查询结果
     */
    List<ChangeOrderLine> selectChangeOrderLine(ChangeOrderHeader changeOrderHeader);

    /**
     * 查询本资产信息变更单的最大行号
     *
     * @param changeOrderHeaderId 头ID
     * @return 最大行号
     */
    Integer selectMaxLineNum(@Param("changeOrderHeaderId") Long changeOrderHeaderId);
}
