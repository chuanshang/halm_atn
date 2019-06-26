package org.hzero.halm.atn.infra.mapper;

import org.hzero.halm.atn.domain.entity.TransferOrderHeader;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 调拨转移单头Mapper
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:34
 */
public interface TransferOrderHeaderMapper extends BaseMapper<TransferOrderHeader> {

    /**
     * 查询调拨转移单头列表
     *
     * @param transferOrderHeader 查询参数
     * @return 查询结果
     */
    List<TransferOrderHeader> selectTransferOrderHeader(TransferOrderHeader transferOrderHeader);
}
