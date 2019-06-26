package org.hzero.halm.atn.infra.constant;

import io.choerodon.mybatis.domain.AuditDomain;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;

import javax.persistence.Transient;
import javax.validation.Valid;
import java.util.List;

/**
 * @author WangSen
 * @Description 事务处理行父类。
 * @date: 2019/4/2 0002 15:47
 */
public class TransactionLine extends AuditDomain {


    @Transient
    @Valid
    private List<OrderDynamicColumn> orderDynamicColumnList;

    public List<OrderDynamicColumn> getOrderDynamicColumnList() {
        return orderDynamicColumnList;
    }

    public void setOrderDynamicColumnList(List<OrderDynamicColumn> orderDynamicColumnList) {
        this.orderDynamicColumnList = orderDynamicColumnList;
    }
}
