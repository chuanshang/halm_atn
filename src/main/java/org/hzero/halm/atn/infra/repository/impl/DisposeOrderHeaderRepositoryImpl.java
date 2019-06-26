package org.hzero.halm.atn.infra.repository.impl;

import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.halm.atn.domain.entity.DisposeOrderLine;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;
import org.hzero.halm.atn.infra.mapper.DisposeOrderHeaderMapper;
import org.hzero.halm.atn.infra.mapper.DisposeOrderLineMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.atn.domain.entity.DisposeOrderHeader;
import org.hzero.halm.atn.domain.repository.DisposeOrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * 资产处置单头 资源库实现
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 11:05:34
 */
@Component
public class DisposeOrderHeaderRepositoryImpl extends BaseRepositoryImpl<DisposeOrderHeader> implements DisposeOrderHeaderRepository {

    @Autowired
    private DisposeOrderHeaderMapper disposeOrderHeaderMapper;

    @Autowired
    private DisposeOrderLineMapper disposeOrderLineMapper;
    @Autowired
    private LovValueHandle lovValueHandle;
    @Override
    public DisposeOrderHeader selectDetail(Long disposeHeaderId, Long tenantId) {
        // 查询头记录
        DisposeOrderHeader disposeOrd = new DisposeOrderHeader();
        disposeOrd.setDisposeHeaderId(disposeHeaderId);
        DisposeOrderHeader disposeOrderHeader = disposeOrderHeaderMapper.getDisposeOrderHeader(disposeOrd);
        // 查询行记录
        List<DisposeOrderLine> lineList = disposeOrderLineMapper.selectLineList(disposeHeaderId, tenantId);
        for(DisposeOrderLine line:lineList){
            List<OrderDynamicColumn> dynamicList = disposeOrderLineMapper.selectDynamicList(line);
            line.setOrderDynamicColumnList(dynamicList);
        }
        lineList = (List<DisposeOrderLine>) lovValueHandle.process(null, lineList);
        //将行集合信息赋值给头
        disposeOrderHeader.setDisposeOrderLines(lineList);
        return disposeOrderHeader;
    }

    @Override
    public List<DisposeOrderLine> entryProcessDetail(DisposeOrderHeader disposeOrderHeader) {
        List<DisposeOrderLine> disposeOrderLineList = disposeOrderLineMapper.entryProcess(disposeOrderHeader);
        for(DisposeOrderLine line:disposeOrderLineList){
            List<OrderDynamicColumn> dynamicList = disposeOrderLineMapper.selectDynamicList(line);
            line.setOrderDynamicColumnList(dynamicList);
        }
        return disposeOrderLineList;
    }

    @Override
    public Page<DisposeOrderHeader> selectListByCondition(PageRequest pageRequest, Long tenantId, String condition) {
        return PageHelper.doPageAndSort(pageRequest,()->disposeOrderHeaderMapper.selectListByCondition(tenantId,condition));
    }
}
