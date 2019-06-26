package org.hzero.halm.atn.domain.repository;

import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.atn.domain.entity.DisposeOrderLine;

import java.util.List;

/**
 * 资产处置单行资源库
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 15:48:13
 */
public interface DisposeOrderLineRepository extends BaseRepository<DisposeOrderLine> {

//    /**
//     * 新增/修改资产处置单行
//     *
//     * @param tenantId           租户ID
//     * @param disposeHeaderId   资产处置单行头ID
//     * @param transactionTypes   事务管理类型
//     * @param disposeOrderLines 操作的行数据
//     * @return List<DisposeOrderLine> 操作结果
//     */
//    List<DisposeOrderLine> saveDisposeOrderLines(Long tenantId, Long disposeHeaderId, TransactionTypes transactionTypes, List<DisposeOrderLine> disposeOrderLines);

    /**
     * 行插入
     * @param tenantId 租户ID
     * @param disposeOrderHeaderId 头ID
     * @param disposeNum 头编码
     * @param disposeOrderLine 插入行信息
     */
    void insertDisposeOrderLine(Long tenantId, Long disposeOrderHeaderId, String disposeNum, DisposeOrderLine disposeOrderLine);

}
