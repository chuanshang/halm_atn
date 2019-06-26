package org.hzero.halm.afm.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.halm.afm.domain.entity.Asset;

/**
 * 资产/设备基本信息应用服务
 *
 * @author zhisheng.zhang@hand-china.com 2019-01-24 16:43:37
 */
public interface AssetService {
	/**
	 * 资产新增
	 *
	 * @param tenantId 租户ID
	 * @param asset    新增资产数据
	 * @return 返回满足条件的值
	 */
	Asset insertAsset(Long tenantId, Asset asset);

	/**
	 * 资产更新
	 *
	 * @param asset 新增资产数据
	 * @return 返回满足条件的值
	 */
	Asset updateAsset(Asset asset);

	/**
	 * 资产删除
	 *
	 * @param asset 新增资产数据
	 * @return 返回满足条件的值
	 */
	Asset deleteAsset(Asset asset);

	/**
	 * 条件查询资产
	 *
	 * @param pageRequest    pageRequest
	 * @param organizationId 租户ID
	 * @return
	 */
	Page<Asset> pageAsset(PageRequest pageRequest, Long organizationId, Asset asset);

	/**
	 * 详情界面列表 条件查询资产
	 *
	 * @param pageRequest       pageRequest
	 * @param organizationId    租户ID
	 * @param condition         传入搜索值
	 * @param organizationScope 组织范围
	 * @param statusScope       状态范围
	 * @param specialtyScope    专业范围
	 * @return
	 */
	Page<Asset> selectAssetByDetailCondition(PageRequest pageRequest,
											 Long organizationId,
											 String condition,
											 String organizationScope,
											 String statusScope,
											 String specialtyScope);

	/**
	 * 资产详细信息
	 *
	 * @param organizationId 租户ID
	 * @param assetId        资产ID
	 */
	Asset assetDetail(Long organizationId, Long assetId);

	/**
	 * 唯一性校验
	 *
	 * @param asset 被校验的对象
	 */
	void validUniqueFields(Asset asset);


}
