package org.hzero.halm.atn.app.service;

import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.atn.domain.entity.HandoverOrderDetail;
import org.hzero.halm.atn.domain.entity.HandoverOrderLine;

import java.util.List;
import java.util.Map;

/**
 * 资产移交归还单行应用服务
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:48:48
 */
public interface HandoverOrderLineService {
    /**
     * 生成描述字段的信息
     * @return 描述信息
     */
    String generateDes(HandoverOrderLine line);

    /**
     * 执行处理的逻辑
     * @param line line
     * @return line
     */
    HandoverOrderLine executeHandoverOrder(HandoverOrderLine line) throws IllegalAccessException;



    /**
     * 删除行数据
     * @param lineList lineList
     */
    void deleteLine(List<HandoverOrderLine> lineList);

    /**
     * 验证控制规则，是否符合事务处理类型设置的 规则。（包含动态和固定字段）
     * @param detail 移交归还明细对象
     * @param transactionType 事务处理类型
     * @param fieldRules 事务类型对应的扩展字段对应的规则类型。
     */
    void validateControllerRules(HandoverOrderDetail detail, TransactionTypes transactionType, Map<String, String> fieldRules) throws IllegalAccessException;

    /**
     * 判断选择的资产是否在 事务处理类型所设置的范围之内。
     * @param asset            asset
     * @param transactionType transactionType
     * @return 是否满足范围条件
     */
    void validateFieldValueInRange(Asset asset, List<HandoverOrderDetail> detailList, TransactionTypes transactionType);
}
