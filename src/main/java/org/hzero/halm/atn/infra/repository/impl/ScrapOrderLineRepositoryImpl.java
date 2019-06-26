package org.hzero.halm.atn.infra.repository.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.domain.entity.AssetLinear;
import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;
import org.hzero.halm.atn.domain.entity.ScrapOrderLine;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.mapper.ScrapOrderLineMapper;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.atn.domain.repository.ScrapOrderLineRepository;
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

/**
 * 资产报废单行 资源库实现
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:10
 */
@Component
public class ScrapOrderLineRepositoryImpl extends BaseRepositoryImpl<ScrapOrderLine> implements ScrapOrderLineRepository {

    @Autowired
    private ScrapOrderLineMapper scrapOrderLineMapper;

    @Override
    public List<ScrapOrderLine> listScrapOrderLine(ScrapOrderHeader scrapOrderHeader) {
        return this.selectByCondition(Condition.builder(ScrapOrderLine.class)
                .andWhere(
                        Sqls.custom()
                                .andEqualTo(ScrapOrderLine.FIELD_SCRAP_HEADER_ID, scrapOrderHeader.getScrapHeaderId())
                                .andEqualTo(ScrapOrderLine.FIELD_TENANT_ID, scrapOrderHeader.getTenantId())
                ).build());
    }

    @Override
    public ScrapOrderLine selectScrapLineById(Long scrapOrderLineId) {
        return this.selectByPrimaryKey(scrapOrderLineId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertScrapOrderLine(Long tenantId, Long scrapHeaderId, ScrapOrderLine scrapOrderLine) {
        scrapOrderLine.setTenantId(tenantId);
        scrapOrderLine.setScrapHeaderId(scrapHeaderId);
        scrapOrderLine.setProcessStatus(AatnConstans.ScrapStatus.NEW);
        if (StringUtils.isEmpty(scrapOrderLine.getDescription())){
            scrapOrderLine.setDescription(StringUtils.EMPTY);
        }

        Integer maxLineNum = scrapOrderLineMapper.selectMaxLineNum(scrapHeaderId);
        scrapOrderLine.setLineNum((Integer) ObjectUtils.defaultIfNull(maxLineNum, AatnConstans.DEFAULT_LINE_NUMBER) + 1);
        if (StringUtils.isEmpty(scrapOrderLine.getDescription())) {
            scrapOrderLine.setDescription(StringUtils.EMPTY);
        }

        this.insertSelective(scrapOrderLine);
    }

    @Override
    public List<ScrapOrderLine> entryProcessDetail(ScrapOrderHeader scrapOrderHeader) {
        return scrapOrderLineMapper.entryProcess(scrapOrderHeader);
    }
}
