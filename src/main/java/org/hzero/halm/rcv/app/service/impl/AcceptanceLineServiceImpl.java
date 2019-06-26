package org.hzero.halm.rcv.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.rcv.app.service.AcceptanceLineService;
import org.hzero.halm.rcv.domain.entity.AcceptanceLine;
import org.hzero.halm.rcv.domain.repository.AcceptanceLineRepository;
import org.hzero.halm.rcv.infra.constant.ArcvConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 验收单行应用服务默认实现
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@Service
public class AcceptanceLineServiceImpl implements AcceptanceLineService {

	@Autowired
	private AcceptanceLineRepository acceptanceLineRepository;

	@Override
	public List<AcceptanceLine> insertAcceptanceLine(Long tenantId, List<AcceptanceLine> acceptanceLineList) {
		List<AcceptanceLine> newAcceptanceLineList = dealAcceptanceLineListData(tenantId, acceptanceLineList);
		acceptanceLineRepository.batchInsertSelective(newAcceptanceLineList);
		return newAcceptanceLineList;
	}

	@Override
	public List<AcceptanceLine> updateAcceptanceLine(Long tenantId, List<AcceptanceLine> acceptanceLineList) {
		List<AcceptanceLine> newAcceptanceLineList = dealAcceptanceLineListData(tenantId, acceptanceLineList);
		acceptanceLineRepository.batchUpdateByPrimaryKeySelective(newAcceptanceLineList);
		return newAcceptanceLineList;
	}

	@Override
	public void validAcceptanceLineFields(AcceptanceLine acceptanceLine) {
		if (acceptanceLine.getAcceptanceHeaderId() == null) {
			throw new CommonException(ArcvConstants.ArcvErrorCode.ARCV_ACCEPTANCE_HEADER_ID_IS_NULL);
		}
		if (acceptanceLine.getTenantId() == null) {
			throw new CommonException(BaseConstants.ErrorCode.NOT_NULL);
		}
	}

	private List<AcceptanceLine> dealAcceptanceLineListData(Long tenantId, List<AcceptanceLine> acceptanceLineList) {
		List<AcceptanceLine> newAcceptanceLineList = new ArrayList<>();
		for (AcceptanceLine acceptanceLine : acceptanceLineList) {
			acceptanceLine.setTenantId(tenantId);
			this.validAcceptanceLineFields(acceptanceLine);
			newAcceptanceLineList.add(acceptanceLine);
		}
		return newAcceptanceLineList;
	}
}
