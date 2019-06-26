package org.hzero.halm.atn.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.atn.domain.entity.TransferOrderHeader;

/**
 * 调拨转移单头资源库
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:34
 */
public interface TransferOrderHeaderRepository extends BaseRepository<TransferOrderHeader> {

    /**
     * 分页查询调拨转移单
     *
     * @param pageRequest         分页参数
     * @param transferOrderHeader 查询参数
     * @return 查询结果
     */
    Page<TransferOrderHeader> pageTransferOrder(PageRequest pageRequest, TransferOrderHeader transferOrderHeader);

}
