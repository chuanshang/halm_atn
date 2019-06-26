package org.hzero.halm.atn.infra.mapper;

import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 资产信息变更单头Mapper
 *
 * @author like.zhang@hand-china.com 2019-03-26 10:20:34
 */
public interface ChangeOrderHeaderMapper extends BaseMapper<ChangeOrderHeader> {

    /**
     * 查询资产信息变更头列表
     *
     * @param changeOrderHeader 查询参数
     * @return 查询结果
     */
    List<ChangeOrderHeader> selectChangeOrderHeader(ChangeOrderHeader changeOrderHeader);

}
