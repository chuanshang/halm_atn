package org.hzero.halm.atn.app.service;

import org.hzero.halm.atn.domain.entity.TransactionHistory;
import org.hzero.halm.atn.domain.entity.TransactionHistoryDetail;
import org.hzero.halm.atn.infra.constant.TransactionLine;

import java.util.List;
import java.util.Map;

/**
 * 资产字段历史明细应用服务
 *
 * @author sen.wang@hand-china.com 2019-03-30 14:14:11
 */
public interface TransactionHistoryDetailService {

    /**
     * 添加一条资产字段的历史明细
     * 》 查询该对象是否有current字段
     * 》 判断是否有target字段
     * 》 查询该字段下，对应的current和target值。
     * 》 去historyDetail表，查询该资产id下，field_name=该字段 的时候，是否有数据。
     * 》 如果没有数据，则将current字段值和target字段值都插入 detail表，否则只插入target字段。（将插入方法封装）
     * @param transactionLine 事务处理行
     */
   void addHistoryDetail(TransactionLine transactionLine, TransactionHistory headerHistory) throws IllegalAccessException;

    /**
     * 查询资产字段的历史明细
     * --> 查询获得一个List<TransactionHistoryDetail>对象，然后根据fieldName进行分组，组合成map形式：<fieldName,List<TransactionHistoryDetail>
     * --> 根据fieldName，进行值集翻译。（可以穿 list数据进去，返回list集合）
     * --> 返回前台。
     * @param historyDetail historyDetail
     * @return 结果
     */
    List<Map> selectFieldHistory(TransactionHistoryDetail historyDetail) throws NoSuchFieldException;
}
