package org.hzero.halm.fam.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.fam.api.dto.FixedAssetsChangesDTO;
import org.hzero.halm.fam.domain.entity.FixedAssetsChanges;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 固定资产价值变动Mapper
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:23:27
 */
public interface FixedAssetsChangesMapper extends BaseMapper<FixedAssetsChanges> {

    /**
     * 查询价值变动列表
     *
     * @param fixedAssetsChanges 查询条件
     * @return List<FixedAssetsChanges>
     */
    List<FixedAssetsChanges> listFixedAssetsChanges(FixedAssetsChanges fixedAssetsChanges);

    /**
     * 查询价值变动列表
     *
     * @param tenantId 租户ID
     * @param fixedAssetsIdList 头ID list
     * @return List<FixedAssetsChanges>
     */
    List<FixedAssetsChanges> listFixedAssetsChangesByFixedAssetsIds(@Param("tenantId") Long tenantId,@Param("fixedAssetsIdList") List<Long> fixedAssetsIdList);

    /**
     * 查询价值变动列表
     *
     * @param tenantId 租户ID
     * @param fixedAssetsId 头ID
     * @return List<FixedAssetsChangesDTO>
     */
    List<FixedAssetsChangesDTO> listFixedAssetsChangesDTOByFixedAssetsId(@Param("tenantId") Long tenantId,@Param("fixedAssetsId") Long fixedAssetsId);


    List<FixedAssetsChangesDTO> listFixedAssetsChangesDTOByIds(@Param("tenantId") Long tenantId, @Param("fixedAssetsIdList") List<Long> fixedAssetsIdList);
}
