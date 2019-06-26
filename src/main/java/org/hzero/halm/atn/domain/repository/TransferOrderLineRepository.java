package org.hzero.halm.atn.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.atn.domain.entity.TransferOrderHeader;
import org.hzero.halm.atn.domain.entity.TransferOrderLine;
import org.hzero.mybatis.base.BaseRepository;

/**
 * 调拨转移单行资源库
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:33
 */
public interface TransferOrderLineRepository extends BaseRepository<TransferOrderLine> {

    /**
     * 条件查询调拨转移单行列表
     *
     * @param transferOrderHeader 查询条件
     * @param pageRequest         分页参数
     * @return 查询结果
     */
    Page<TransferOrderLine> listTransferOrderLine(TransferOrderHeader transferOrderHeader, PageRequest pageRequest);

    /**
     * 联表查询调拨转移单行（根据行ID）
     *
     * @param transferOrderLineId 行ID
     * @return 调拨转移单行
     */
    TransferOrderLine selectTransferLineById(Long transferOrderLineId);
}
