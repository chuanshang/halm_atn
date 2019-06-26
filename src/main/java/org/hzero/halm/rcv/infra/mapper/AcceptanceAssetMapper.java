package org.hzero.halm.rcv.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.rcv.domain.entity.AcceptanceAsset;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 验收单资产明细Mapper
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
public interface AcceptanceAssetMapper extends BaseMapper<AcceptanceAsset> {

	/**
	 * 通过验收单头ID来查询所有的资产明细行
	 * @param tenantId 租户ID
	 * @param acceptanceHeaderIds 验收单头ID
	 * @return 资产明细行
	 */
	List<AcceptanceAsset> listacceptanceAssetByAcceptanceHeaderIds(@Param("tenantId") Long tenantId, @Param("acceptanceHeaderIds") List<Long> acceptanceHeaderIds);
}
