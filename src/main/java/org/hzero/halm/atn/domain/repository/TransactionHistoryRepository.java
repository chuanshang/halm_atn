package org.hzero.halm.atn.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.atn.domain.entity.TransactionHistory;

/**
 * 资产处理历史信息资源库
 *
 * @author sen.wang@hand-china.com 2019-03-28 14:03:23
 */
public interface TransactionHistoryRepository extends BaseRepository<TransactionHistory> {

    /**
     * 查询资产时间的履历
     * @param pageRequest pageRequest
     * @param history history
     * @return
     */
    Page<TransactionHistory> pageEnventHistory(PageRequest pageRequest,TransactionHistory history);
}
