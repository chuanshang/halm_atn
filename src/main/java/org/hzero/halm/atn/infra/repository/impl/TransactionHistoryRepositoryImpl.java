package org.hzero.halm.atn.infra.repository.impl;

import com.alibaba.fastjson.JSON;
import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.halm.atn.domain.entity.TransactionHistory;
import org.hzero.halm.atn.domain.repository.TransactionHistoryRepository;
import org.hzero.halm.atn.infra.mapper.TransactionHistoryMapper;
import org.hzero.halm.atn.infra.util.TransactionHistoryUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 资产处理历史信息 资源库实现
 *
 * @author sen.wang@hand-china.com 2019-03-28 14:03:23
 */
@Component
public class TransactionHistoryRepositoryImpl extends BaseRepositoryImpl<TransactionHistory> implements TransactionHistoryRepository {

    @Autowired
    TransactionHistoryMapper historyMapper;

    @Autowired
    LovAdapter lovAdapter;

    @Override
    public Page<TransactionHistory> pageEnventHistory(PageRequest pageRequest, TransactionHistory history) {
        Map allProcessStatus = TransactionHistoryUtils.getTheAllProcessStatus(lovAdapter);
        Page<TransactionHistory> page = PageHelper.doPageAndSort(pageRequest, () ->
                historyMapper.selectEventHistory(history)
        );
        // 获取processStatus的meaning值
        page.getContent().forEach(h ->
                h.setProcessStatusRecordMeaning(this.getStatusMeanings(h.getProcessStatusRecord(), allProcessStatus)));
        return page;

    }

    private String getStatusMeanings(String arrayJson, Map map) {
        List<String> list = JSON.parseArray(arrayJson, String.class);
        if (CollectionUtils.isNotEmpty(list)) {
            List meaningList = list.stream().map((Function<String, Object>) map::get).collect(Collectors.toList());
            return JSON.toJSONString(meaningList);
        }
        return null;
    }

}
