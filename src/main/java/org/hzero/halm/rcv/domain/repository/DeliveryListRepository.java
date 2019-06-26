package org.hzero.halm.rcv.domain.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.rcv.domain.entity.DeliveryList;

import java.util.List;

/**
 * 交付清单行资源库
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-17 16:06:43
 */
public interface DeliveryListRepository extends BaseRepository<DeliveryList> {

	/**
	 * 通过查询条件查询交付清单行列表
	 *
	 * @param pageRequest  分页参数
	 * @param deliveryList 查询参数
	 * @return 交付清单行列表
	 */
	Page<DeliveryList> listDeliveryList(PageRequest pageRequest, DeliveryList deliveryList);

	/**
	 * 通过查询内容模糊查询交付清单
	 *
	 * @param pageRequest 分页参数
	 * @param tenantId    租户ID
	 * @param condition   查询参数
	 * @return 交付清单行列表
	 */
	Page<DeliveryList> retrieveDeliveryList(PageRequest pageRequest, Long tenantId, String condition);
}
