package org.hzero.halm.afm.domain.repository;

import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.atn.domain.entity.OrderDynamicColumn;
import org.hzero.halm.atn.domain.entity.ChangeOrderLine;
import org.hzero.mybatis.base.BaseRepository;

import java.util.List;

/**
 * 资产/设备基本信息资源库
 *
 * @author zhisheng.zhang@hand-china.com 2019-01-24 16:43:37
 */
public interface AssetRepository extends BaseRepository<Asset> {

	/**
	 * 查询资产
	 *
	 * @param asset 查询条件
	 * @return List<Asset>
	 */
	List<Asset> selectAssetList(Asset asset);

	/**
	 * 详情界面查询资产列表
	 *
	 * @param organizationId    租户ID
	 * @param detailCondition   查询条件
	 * @param organizationScope 组织范围
	 * @param statusScope       状态范围
	 * @param specialtyScope    专业范围
	 * @return List<Asset>
	 */
	List<Asset> selectAssetByDetailCondition(Long organizationId,
											 String detailCondition,
											 String organizationScope,
											 String statusScope,
											 String specialtyScope);

	/**
	 * 根据资产变更单变更资产台账信息
	 *
	 * @param line                资产变更单行内容
	 * @param orderDynamicColumns 资产变更单字段详细内容
	 * @param asset               目标资产
	 */
	void updateAssetByChangeOrder(ChangeOrderLine line, List<OrderDynamicColumn> orderDynamicColumns, Asset asset);

	/**
	 * 更新资产状态信息
	 *
	 * @param orderDynamicColumns orderDynamicColumns
	 * @param asset               asset
	 */
	void updateAssetOnDynamicColumn(List<OrderDynamicColumn> orderDynamicColumns, Asset asset);
}
