package org.hzero.halm.rcv.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.rcv.domain.entity.DeliveryList;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 交付清单行Mapper
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-17 16:06:43
 */
public interface DeliveryListMapper extends BaseMapper<DeliveryList> {

	/**
	 * 通过查询条件查询交付清单行列表
	 * @param deliveryList 查询参数
	 * @return 交付清单行列表
	 */
	List<DeliveryList> listDeliveryList(DeliveryList deliveryList);

	/**
	 * 通过查询内容模糊查询交付清单
	 * @param condition 查询参数
	 * @return 交付清单行列表
	 */
	List<DeliveryList> retrieveDeliveryList(@Param("tenantId") Long tenantId,@Param("condition") String condition);

}
