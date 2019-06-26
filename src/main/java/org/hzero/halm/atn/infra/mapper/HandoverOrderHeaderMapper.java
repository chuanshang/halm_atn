package org.hzero.halm.atn.infra.mapper;

import feign.Param;
import org.hzero.halm.atn.domain.entity.HandoverOrderHeader;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 资产移交归还单头Mapper
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:03
 */
public interface HandoverOrderHeaderMapper extends BaseMapper<HandoverOrderHeader> {

    /**
     * 根据条件查询移交归还单头数据。
     * @param handoverOrderHeader handoverOrderHeader
     * @return 结果
     */
    List<HandoverOrderHeader> selectHeaderList(HandoverOrderHeader handoverOrderHeader);

    /**
     * 查询移交归还头数据
     * @param handoverHeaderId header
     * @return 结果
     */
    HandoverOrderHeader selectDetailById(@Param("handoverHeaderId") Long handoverHeaderId);

}
