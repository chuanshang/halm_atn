package org.hzero.halm.atn.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.atn.domain.entity.ChangeOrderHeader;
import org.hzero.halm.atn.domain.repository.ChangeOrderHeaderRepository;
import org.hzero.halm.atn.infra.mapper.ChangeOrderHeaderMapper;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 资产信息变更单头 资源库实现
 *
 * @author like.zhang@hand-china.com 2019-03-26 10:20:34
 */
@Component
public class ChangeOrderHeaderRepositoryImpl extends BaseRepositoryImpl<ChangeOrderHeader> implements ChangeOrderHeaderRepository {

    @Autowired
    private ChangeOrderHeaderMapper changeOrderHeaderMapper;
    @Autowired
    private CommonProcessUtils commonProcessUtils;

    @Override
    public Page<ChangeOrderHeader> pageChangeOrder(PageRequest pageRequest, ChangeOrderHeader changeOrderHeader) {
        return PageHelper.doPageAndSort(pageRequest, () -> {
            Long tenantId = changeOrderHeader.getTenantId();
            String condition = changeOrderHeader.getCondition();
            changeOrderHeader.setProcessStatusCodeList(commonProcessUtils.processStatusCondition(tenantId, condition));

            return changeOrderHeaderMapper.selectChangeOrderHeader(changeOrderHeader);
        });
    }


}
