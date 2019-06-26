package org.hzero.halm.afm.infra.mapper;

import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hzero.halm.afm.domain.entity.Asset;

import java.util.List;

/**
 * 资产/设备基本信息Mapper
 *
 * @author zhisheng.zhang@hand-china.com 2019-01-24 16:43:37
 */
public interface AssetMapper extends BaseMapper<Asset> {

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
	 * @param tenantId        租户ID
	 * @param detailCondition 搜索条件
	 * @return List<Asset>
	 */
	List<Asset> selectAssetByDetailCondition(@Param("tenantId") Long tenantId,
											 @Param("detailCondition") String detailCondition,
											 @Param("organizationIds") List<Long> organizationIds,
											 @Param("statusIds") List<Long> statusIds,
											 @Param("specialtyIds") List<Long> specialtyIds);
}
