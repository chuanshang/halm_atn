package org.hzero.halm.atn.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.hzero.halm.atn.domain.entity.TransactionHistoryDetail;

import java.util.List;

/**
 * 资产字段历史明细Mapper
 *
 * @author sen.wang@hand-china.com 2019-03-30 14:14:11
 */
public interface TransactionHistoryDetailMapper extends BaseMapper<TransactionHistoryDetail> {

    /**
     * 查询 某资产 下的 某字段 的最新值数据。
     * @param detail detail
     * @return 数据的数量
     */
    String selectLatestValue(TransactionHistoryDetail detail);

    /**
     * 查询 某资产下的 对应字段的数据
     * @param detail detail (assetId和tenantId必须有值)
     * @return 结果
     */
    List<TransactionHistoryDetail>  selectFieldHistory(TransactionHistoryDetail detail);
}
