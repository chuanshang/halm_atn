package org.hzero.halm.rcv.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang.StringUtils;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.rcv.domain.entity.AcceptanceAsset;
import org.hzero.halm.rcv.domain.entity.AcceptanceLine;
import org.hzero.halm.rcv.domain.entity.AcceptanceRelation;
import org.hzero.halm.rcv.domain.repository.AcceptanceRelationRepository;
import org.hzero.halm.rcv.infra.mapper.AcceptanceAssetMapper;
import org.hzero.halm.rcv.infra.mapper.AcceptanceHeaderMapper;
import org.hzero.halm.rcv.infra.mapper.AcceptanceLineMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.rcv.domain.entity.AcceptanceHeader;
import org.hzero.halm.rcv.domain.repository.AcceptanceHeaderRepository;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 验收单 资源库实现
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@Component
public class AcceptanceHeaderRepositoryImpl extends BaseRepositoryImpl<AcceptanceHeader> implements AcceptanceHeaderRepository {

	@Autowired
	AcceptanceHeaderMapper acceptanceHeaderMapper;
	@Autowired
	AcceptanceLineMapper acceptanceLineMapper;
	@Autowired
	AcceptanceAssetMapper acceptanceAssetMapper;
	@Autowired
	AcceptanceRelationRepository acceptanceRelationRepository;
	@Autowired
	private LovValueHandle lovValueHandle;

	@Override
	public Page<AcceptanceHeader> listAcceptanceHeader(PageRequest pageRequest, AcceptanceHeader acceptanceHeader) {
		return PageHelper.doPageAndSort(pageRequest, () -> acceptanceHeaderMapper.listAcceptanceHeader(acceptanceHeader));
	}

	@Override
	public Page<AcceptanceHeader> listAcceptanceHeaderByCondition(PageRequest pageRequest, Long tenantId, String condition) {
		return PageHelper.doPageAndSort(pageRequest, () -> acceptanceHeaderMapper.listAcceptanceHeaderByCondition(tenantId, condition));
	}

	@Override
	public List<AcceptanceHeader> detailAcceptance(Long tenantId, String acceptanceHeaderIds) {

		//将id集合字符串分解为数组
		List<Long> ids = new ArrayList<>();
		if (StringUtils.isNotEmpty(acceptanceHeaderIds)) {
			List<String> strings = Arrays.asList(acceptanceHeaderIds.split(BaseConstants.Symbol.COMMA));
			ids = strings.stream().map(Long::parseLong).collect(Collectors.toList());
		}

		//查询验收单头数据
		List<AcceptanceHeader> acceptanceHeaderList = acceptanceHeaderMapper.listAcceptanceHeaderByIds(tenantId, ids);
		List<AcceptanceHeader> newAcceptanceHeaderList = new ArrayList<>();
		if (acceptanceHeaderList != null && acceptanceHeaderList.size() > 0) {
			for (AcceptanceHeader acceptanceHeader : acceptanceHeaderList) {
				List<Long> acceptanceHeaderIdList = new ArrayList<>();
				acceptanceHeaderIdList.add(acceptanceHeader.getAcceptanceHeaderId());
				//查询验收单关联验收单信息
				acceptanceHeader.setAcceptanceRelationList(
						acceptanceRelationRepository.selectByCondition(
								Condition.builder(AcceptanceRelation.class)
										.andWhere(
												Sqls.custom()
														.andEqualTo(AcceptanceRelation.FIELD_TENANT_ID, tenantId)
														.andIn(AcceptanceRelation.FIELD_ACCEPTANCE_HEADER_ID, acceptanceHeaderIdList, true)
										).build()
						)
				);

				//查询验收单行信息
				List<AcceptanceLine> newAcceptanceLineList = acceptanceLineMapper.listAcceptanceLineByHeaderIds(tenantId, acceptanceHeaderIdList);
				newAcceptanceLineList = (List<AcceptanceLine>) lovValueHandle.process(null, newAcceptanceLineList);
				acceptanceHeader.setAcceptanceLineList(newAcceptanceLineList);

				//查询验收单资产明细信息
				List<AcceptanceAsset> newAcceptanceAssetList = acceptanceAssetMapper.listacceptanceAssetByAcceptanceHeaderIds(tenantId, acceptanceHeaderIdList);
				newAcceptanceAssetList = (List<AcceptanceAsset>) lovValueHandle.process(null, newAcceptanceAssetList);
				acceptanceHeader.setAcceptanceAssetList(newAcceptanceAssetList);

				newAcceptanceHeaderList.add(acceptanceHeader);
			}
		}

		return acceptanceHeaderList;
	}
}
