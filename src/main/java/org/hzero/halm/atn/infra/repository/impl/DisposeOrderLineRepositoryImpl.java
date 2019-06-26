package org.hzero.halm.atn.infra.repository.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
import org.hzero.halm.atn.infra.constant.AatnDisposeOrderConstans;
import org.hzero.halm.atn.infra.mapper.DisposeOrderLineMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.atn.domain.entity.DisposeOrderLine;
import org.hzero.halm.atn.domain.repository.DisposeOrderLineRepository;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import io.choerodon.core.exception.CommonException;

/**
 * 资产处置单行 资源库实现
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 15:48:13
 */
@Component
public class DisposeOrderLineRepositoryImpl extends BaseRepositoryImpl<DisposeOrderLine> implements DisposeOrderLineRepository {

    @Autowired
    private DisposeOrderLineMapper disposeOrderLineMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDisposeOrderLine(Long tenantId, Long disposeOrderHeaderId, String disposeNum, DisposeOrderLine disposeOrderLine) {

        disposeOrderLine.setTenantId(tenantId);
        disposeOrderLine.setDisposeHeaderId(disposeOrderHeaderId);
        disposeOrderLine.setProcessStatus(AatnDisposeOrderConstans.DisposeLineStatus.NEW);
        //给目标资产状态id 赋固定值，“已处置”，需要查询
        Long targetAssetStatusId = disposeOrderLineMapper.getTargetAssetStatusId(tenantId);
        if(targetAssetStatusId != null){
            disposeOrderLine.setTargetAssetStatusId(targetAssetStatusId);
        }

        Integer maxLineNum = disposeOrderLineMapper.selectMaxLineNum(disposeOrderHeaderId);
        disposeOrderLine.setLineNum((Integer) ObjectUtils.defaultIfNull(maxLineNum, AatnConstans.DEFAULT_LINE_NUMBER) + 1);
        if (StringUtils.isEmpty(disposeOrderLine.getDescription())) {
            disposeOrderLine.setDescription(StringUtils.EMPTY);
        }

        this.insertSelective(disposeOrderLine);
    }
}
