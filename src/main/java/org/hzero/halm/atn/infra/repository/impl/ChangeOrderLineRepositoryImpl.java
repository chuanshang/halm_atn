package org.hzero.halm.atn.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;
import org.hzero.halm.atn.domain.entity.ChangeOrderLine;
import org.hzero.halm.atn.domain.repository.ChangeOrderLineRepository;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.mapper.ChangeOrderLineMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 资产信息变更单行 资源库实现
 *
 * @author like.zhang@hand-china.com 2019-03-26 11:49:44
 */
@Component
public class ChangeOrderLineRepositoryImpl extends BaseRepositoryImpl<ChangeOrderLine> implements ChangeOrderLineRepository {
    @Autowired
    private ChangeOrderLineMapper changeOrderLineMapper;

    @Override
    public Page<ChangeOrderLine> listChangeOrderLine(ChangeOrderHeader changeOrderHeader, PageRequest pageRequest) {
        return PageHelper.doPageAndSort(pageRequest, () -> changeOrderLineMapper.selectChangeOrderLine(changeOrderHeader));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertChangeOrderLine(Long tenantId, Long changeHeaderId, String changeNum, ChangeOrderLine changeOrderLine) {
        changeOrderLine.setTenantId(tenantId);
        changeOrderLine.setChangeHeaderId(changeHeaderId);
        changeOrderLine.setProcessStatus(AatnConstans.ChangeOrderLineStatus.NEW);

        Integer maxLineNum = changeOrderLineMapper.selectMaxLineNum(changeHeaderId);
        changeOrderLine.setLineNum((Integer) ObjectUtils.defaultIfNull(maxLineNum, AatnConstans.DEFAULT_LINE_NUMBER) + 1);
        if (StringUtils.isEmpty(changeOrderLine.getDescription())) {
            changeOrderLine.setDescription(StringUtils.EMPTY);
        }

        this.insertSelective(changeOrderLine);
    }
}
