package org.hzero.halm.rcv.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.rcv.infra.mapper.DeliveryListMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.rcv.domain.entity.DeliveryList;
import org.hzero.halm.rcv.domain.repository.DeliveryListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 交付清单行 资源库实现
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-17 16:06:43
 */
@Component
public class DeliveryListRepositoryImpl extends BaseRepositoryImpl<DeliveryList> implements DeliveryListRepository {

	@Autowired
	private DeliveryListMapper deliveryListMapper;

	@Override
	public Page<DeliveryList> listDeliveryList(PageRequest pageRequest, DeliveryList deliveryList) {
		return PageHelper.doPageAndSort(pageRequest, () -> deliveryListMapper.listDeliveryList(deliveryList));
	}

	@Override
	public Page<DeliveryList> retrieveDeliveryList(PageRequest pageRequest, Long tenantId, String condition) {
		return PageHelper.doPageAndSort(pageRequest, () -> deliveryListMapper.retrieveDeliveryList(tenantId, condition));
	}

}
