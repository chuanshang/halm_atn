package org.hzero.halm.atn.app.service.impl;

import io.choerodon.core.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.atn.app.service.OrderDynamicColumnService;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;
import org.hzero.halm.atn.domain.repository.OrderDynamicColumnRepository;
import org.hzero.halm.atn.infra.constant.AatnChangeOrderExternalControlFields;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.util.CommonProcessUtils;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 单据动态字段服务实现
 *
 * @author like.zhang@hand-china.com 2019-03-26 11:49:44
 */
@Service
public class OrderDynamicColumnServiceImpl implements OrderDynamicColumnService {

	@Autowired
	private OrderDynamicColumnRepository orderDynamicColumnRepository;
	@Autowired
	private CommonProcessUtils commonProcessUtils;

	private static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance();

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<OrderDynamicColumn> saveDynamicColumns(Long tenantId, Long orderLineId, Long orderHeaderId, String orderTypeCode, List<OrderDynamicColumn> dynamicColumns, Map<String, String> fieldRulesMap) {

		if (CollectionUtils.isEmpty(dynamicColumns)) {
			return dynamicColumns;
		}
		// 对于字段明细的操作：先删除行ID下所有的字段明细，在进行新增
		List<OrderDynamicColumn> currentDetails = orderDynamicColumnRepository.selectByCondition(Condition.builder(OrderDynamicColumn.class)
				.andWhere(Sqls.custom()
						.andEqualTo(OrderDynamicColumn.FIELD_ORDER_LINE_ID, orderLineId)
						.andEqualTo(OrderDynamicColumn.FIELD_ORDER_HEADER_ID, orderHeaderId)
						.andEqualTo(OrderDynamicColumn.FIELD_ORDER_TYPE_CODE, orderTypeCode)
				).build());

		// 校验变更规则
		commonProcessUtils.validTransactionTypeRules(dynamicColumns, fieldRulesMap, AatnChangeOrderExternalControlFields.values());

		if (CollectionUtils.isNotEmpty(currentDetails)) {
			orderDynamicColumnRepository.batchDeleteByPrimaryKey(currentDetails);
		}
		if (CollectionUtils.isNotEmpty(dynamicColumns)) {
			// 写入租户ID和头ID
			dynamicColumns.forEach(column -> {
				column.setTenantId(tenantId);
				column.setOrderLineId(orderLineId);
				column.setOrderHeaderId(orderHeaderId);
				column.setOrderTypeCode(orderTypeCode);
			});
			orderDynamicColumnRepository.batchInsertSelective(dynamicColumns);
		}
		return dynamicColumns;
	}

	@Override
	public Object columnTypeConversion(String targetColumnType, String targetColumnValue) {
		if (targetColumnValue == null) {
			return null;
		}
		Object targetValue;
		switch (targetColumnType) {
			case AatnConstans.targetColumnType.LOV:
			case AatnConstans.targetColumnType.INPUTNUMBER:
				targetValue = targetColumnValue.isEmpty() ? null : Long.parseLong(targetColumnValue);
				break;
			case AatnConstans.targetColumnType.INTEGER:
			case AatnConstans.targetColumnType.BOOLEAN:
				targetValue = Integer.parseInt(targetColumnValue);
				break;
			case AatnConstans.targetColumnType.DATEPICKER:
			case AatnConstans.targetColumnType.TIME:
				try {
					targetValue = DateUtils.parseDate(targetColumnValue, BaseConstants.Pattern.DATETIME);
				} catch (ParseException e) {
					throw new CommonException(e);
				}
				break;
			default:
				targetValue = targetColumnValue;
				break;
		}
		return targetValue;
	}
}
