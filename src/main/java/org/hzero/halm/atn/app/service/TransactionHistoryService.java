package org.hzero.halm.atn.app.service;

import org.hzero.halm.atn.infra.constant.TransactionLine;
import org.hzero.mybatis.base.BaseRepository;

/**
 * 资产处理历史信息应用服务
 *
 * @author sen.wang@hand-china.com 2019-03-28 14:03:23
 */
public interface TransactionHistoryService {

    public static final String FIELD_ASSET_ID = "assetId";
    public static final String FIELD_TRANSACTION_TYPE_ID = "transactionTypeId";
    public static final String FIELD_PROCESS_STATUS = "processStatus";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String DEFAULT_STATUS = "NEW";


    /**
     * 添加事务处理的历史信息
     *
     * 使用反射，查出事务行对应的，headerId,transactionTypeId,lineId,assetId
     * 和对应的processStatus插入到对应的transaction_history表中。
     * 》》 向transaction_history表插入数据之后，再向transaction_history_detail表添加字段明细数据。
     *
     * @param transactionLine transactionLine
     * @param lineRepository lineRepository
     * @param headerRepository headerRepository
     */
    void addTransactionHistory(TransactionLine transactionLine, BaseRepository lineRepository, BaseRepository headerRepository);
}
