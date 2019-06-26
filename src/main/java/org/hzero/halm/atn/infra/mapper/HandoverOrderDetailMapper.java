package org.hzero.halm.atn.infra.mapper;

import org.hzero.halm.atn.domain.entity.HandoverOrderDetail;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 资产移交归还明细Mapper
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:11
 */
public interface HandoverOrderDetailMapper extends BaseMapper<HandoverOrderDetail> {

    /**
     * 查询明细数据，并关联值集表，查询值集值。
     * @param detail detail
     * @return 明细数据
     */
    List<HandoverOrderDetail> selectDetail(HandoverOrderDetail detail);
}
