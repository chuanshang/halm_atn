package org.hzero.halm.atn.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.halm.atn.domain.entity.ScrapOrderLine;
import org.hzero.halm.atn.infra.mapper.ScrapOrderHeaderMapper;
import org.hzero.halm.atn.infra.mapper.ScrapOrderLineMapper;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;
import org.hzero.halm.atn.domain.repository.ScrapOrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资产报废单头 资源库实现
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:50
 */
@Component
public class ScrapOrderHeaderRepositoryImpl extends BaseRepositoryImpl<ScrapOrderHeader> implements ScrapOrderHeaderRepository {


	@Autowired
	private ScrapOrderHeaderMapper scrapOrderHeaderMapper;
	@Autowired
	private ScrapOrderLineMapper scrapOrderLineMapper;
	@Autowired
	private CommonProcessUtils commonProcessUtils;
	@Autowired
	private LovValueHandle lovValueHandle;

	@Override
	public Page<ScrapOrderHeader> pageScrapOrder(PageRequest pageRequest, ScrapOrderHeader scrapOrderHeader) {
		return PageHelper.doPageAndSort(pageRequest, () -> {
			Long tenantId = scrapOrderHeader.getTenantId();
			String condition = scrapOrderHeader.getCondition();
			scrapOrderHeader.setProcessStatusCodeList(commonProcessUtils.processStatusCondition(tenantId, condition));
			return scrapOrderHeaderMapper.selectScrapOrderHeader(scrapOrderHeader);
		});
	}

	@Override
	public ScrapOrderHeader detailScrapOrder(Long tenantId, Long scrapOrderHeaderId) {
		ScrapOrderHeader queryParam=new ScrapOrderHeader();
		queryParam.setTenantId(tenantId);
		queryParam.setScrapHeaderId(scrapOrderHeaderId);
		ScrapOrderHeader scrapOrderHeader =scrapOrderHeaderMapper.selectScrapOrderHeader(queryParam).get(0);
		scrapOrderHeader.setScrapOrderLines((List<ScrapOrderLine>) lovValueHandle.process(null, scrapOrderLineMapper.selectOrderLines(tenantId,scrapOrderHeaderId)));
		return scrapOrderHeader;

	}

	@Override
	public Page<ScrapOrderHeader> listScrapOrderHeader(PageRequest pageRequest,Long tenantId, String content) {
		return PageHelper.doPageAndSort(pageRequest, () -> {
			return scrapOrderHeaderMapper.selectScrapOrderHeaderByContent(tenantId, content);
		});
	}
}
