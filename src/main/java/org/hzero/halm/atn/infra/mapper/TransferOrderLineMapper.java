package org.hzero.halm.atn.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.halm.atn.domain.entity.TransferOrderHeader;
import org.hzero.halm.atn.domain.entity.TransferOrderLine;

import java.util.List;

/**
 * 调拨转移单行Mapper
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:33
 */
public interface TransferOrderLineMapper extends BaseMapper<TransferOrderLine> {

    /**
     * 条件查询调拨转移单行列表
     *
     * @param transferOrderHeader 查询条件
     * @return 查询结果
     */
    List<TransferOrderLine> selectTransferOrderLine(TransferOrderHeader transferOrderHeader);
}
