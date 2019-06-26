package org.hzero.halm.afm.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.halm.afm.api.dto.AttributeSetDTO;
import org.hzero.halm.afm.app.service.AttributeLineService;
import org.hzero.halm.afm.app.service.AttributeSetService;
import org.hzero.halm.afm.domain.entity.AttributeLine;
import org.hzero.halm.afm.domain.entity.AttributeSet;
import org.hzero.halm.afm.domain.repository.AttributeLineRepository;
import org.hzero.halm.afm.domain.repository.AttributeSetRepository;
import org.hzero.halm.afm.domain.vo.AttributeLineDetailExportVO;
import org.hzero.halm.afm.domain.vo.AttributeSetDetailExportVO;
import org.hzero.halm.afm.infra.mapper.AttributeLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 属性组头信息应用服务默认实现
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:24
 */
@Service
public class AttributeSetServiceImpl implements AttributeSetService {

	@Autowired
	private AttributeSetRepository attributeSetRepository;
	@Autowired
	private LovValueHandle lovValueHandle;

	@Autowired
	private AttributeLineRepository attributeLineRepository;
	@Autowired
	private AttributeLineMapper attributeLineMapper;

	@Override
	public Page<AttributeSet> pageAttributeSet(PageRequest pageRequest, Long organizationId, AttributeSetDTO queryAttributeSet) {
		queryAttributeSet.setTenantId(organizationId);
		return PageHelper.doPageAndSort(pageRequest, () -> {
			return attributeSetRepository.selectByCondition(org.hzero.mybatis.domian.Condition.builder(AttributeSet.class)
					.andWhere(
							org.hzero.mybatis.util.Sqls.custom()
									.andEqualTo(AttributeSet.FIELD_TENANT_ID, queryAttributeSet.getTenantId(), true)
									.andEqualTo(AttributeSet.FIELD_ENABLED_FLAG, queryAttributeSet.getEnabledFlag(), true)
									.andLike(AttributeSet.FIELD_ATTRIBUTE_SET_NAME, queryAttributeSet.getAttributeSetName(), true)
					).build());
		});
	}

	@Override
	public AttributeSet attributeSetDetail(PageRequest pageRequest, Long attributeSetId) {
		AttributeSet attributeSet = attributeSetRepository.selectByPrimaryKey(attributeSetId);
//		List<AttributeLine> attributeLineList = attributeLineService.pageAndSortBySetId(pageRequest, attributeSetId);
		List<AttributeLine> attributeLineList =attributeLineMapper.selectBySetId(attributeSetId);
		lovValueHandle.process(null, attributeLineList);
		attributeSet.setAttributeLinesList(attributeLineList);
		return attributeSet;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchCreateAndUpdate(Long organizationId, List<AttributeSet> attributeSets) {
		attributeSets.forEach(attributeSet -> {
			attributeSet.setTenantId(organizationId);

			//校验
			attributeSet.validateAttribute(organizationId, attributeSetRepository);

			if (attributeSet.getAttributeSetId() == null) {
				attributeSetRepository.insertSelective(attributeSet);
			} else {
				attributeSetRepository.updateOptional(attributeSet, AttributeSet.FIELD_ATTRIBUTE_SET_NAME,
						AttributeSet.FIELD_ENABLED_FLAG, AttributeSet.FIELD_DESCRIPTION);
			}

			//保存行信息
			batchCreateAndUpdateAttributeLine(organizationId,
					attributeSet.getAttributeSetId(),
					attributeSet.getAttributeLinesList());
		});
	}

	/**
	 * 保存行信息
	 *
	 * @param organizationId 租户ID
	 * @param attributeSetId 属性组ID
	 * @param attributeLines 属性list
	 */
	void batchCreateAndUpdateAttributeLine(Long organizationId, Long attributeSetId, List<AttributeLine> attributeLines) {
		if (attributeLines != null) {
			attributeLines.forEach(attributeLine -> {
				attributeLine.setTenantId(organizationId);
				attributeLine.setAttributeSetId(attributeSetId);

				//行校验
				attributeLine.validateLine(organizationId, attributeSetId, attributeLineRepository);

				if (attributeLine.getLineId() == null) {
					attributeLineRepository.insertSelective(attributeLine);
				} else {
					attributeLineRepository.updateOptional(attributeLine, AttributeLine.FIELD_ATTRIBUTE_NAME,
							AttributeLine.FIELD_ATTRIBUTE_TYPE,
							AttributeLine.FIELD_DESCRIPTION,
							AttributeLine.FIELD_ENABLED_FLAG,
							AttributeLine.FIELD_REQUIRED_FLAG,
							AttributeLine.FIELD_LINE_NUM,
							AttributeLine.FIELD_UOM_ID,
							AttributeLine.FIELD_LOV_VALUE);
				}
			});
		}
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<AttributeSetDetailExportVO> exportAttributeSetByIds(AttributeSetDTO attributeSet) {
		//查询头
		List<AttributeSetDetailExportVO> attributeSetDetailExportList = attributeSetRepository.exportAttributeSetByIds(attributeSet);
		//提取查询的头id
		Set<Long> attributeSetIdSet = attributeSetDetailExportList.stream().map(AttributeSetDetailExportVO::getAttributeSetId).collect(Collectors.toSet());
		//根据头id查行
		List<AttributeLineDetailExportVO> attributeLineDetailExportList = attributeLineRepository.exportAttributeLineBySetId(StringUtils.join(attributeSetIdSet, ','));
		//按照头id分组
		Map<Long, List<AttributeLineDetailExportVO>> groupMap = attributeLineDetailExportList.stream().collect(Collectors.groupingBy(AttributeLineDetailExportVO::getAttributeSetId));
		//将行数据放入头中
		attributeSetDetailExportList.forEach(attributeSetDetailExport -> attributeSetDetailExport.setAttributeLinesList(groupMap.get(attributeSetDetailExport.getAttributeSetId())));
		return attributeSetDetailExportList;
	}
}
