package org.hzero.halm.atn.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.halm.atn.domain.entity.TransactionHistory;

import java.util.List;

/**
 * 资产处理历史信息Mapper
 *
 * @author sen.wang@hand-china.com 2019-03-28 14:03:23
 */
public interface TransactionHistoryMapper extends BaseMapper<TransactionHistory> {

    /**
     * 查询资产事件历史信息。关联移交归还单，调拨转移单，资产报废单，资产处置单，
     * @param history history
     * @return 资产实践信息列表
     */
    List<TransactionHistory> selectEventHistory(TransactionHistory history);

}
