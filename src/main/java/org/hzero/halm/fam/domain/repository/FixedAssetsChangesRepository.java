package org.hzero.halm.fam.domain.repository;

import org.hzero.halm.fam.api.dto.FixedAssetsChangesDTO;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.fam.domain.entity.FixedAssetsChanges;

import java.util.List;

/**
 * 固定资产价值变动资源库
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:23:27
 */
public interface FixedAssetsChangesRepository extends BaseRepository<FixedAssetsChanges> {
    /**
     * 查询列表
     *
     * @param tenantId 租户ID
     * @param fixedAssetsChanges 查询条件
     * @return Page<FixedAssets>
     */
    List<FixedAssetsChanges> listFixedAssetsChanges(Long tenantId, FixedAssetsChanges fixedAssetsChanges);

    /**
     * 查询
     *
     * @param tenantId 租户ID
     * @param fixedAssetsIds 头id
     * @return Page<FixedAssets>
     */
    List<FixedAssetsChanges> listFixedAssetsChangesByFixedAssetsIds(Long tenantId,String fixedAssetsIds);

    /**
     * 查询
     *
     * @param tenantId 租户ID
     * @param fixedAssetsId 头id
     * @return Page<FixedAssets>
     */
    List<FixedAssetsChangesDTO> listFixedAssetsChangesDTOByFixedAssetsId(Long tenantId,Long fixedAssetsId);

    /**
     * 查询价值变动
     * @param tenantId 租户ID
     * @param fixedAssetsIds 固定资产ids
     * @return
     */
    List<FixedAssetsChangesDTO> listFixedAssetsChangesDTO(Long tenantId, String fixedAssetsIds);
}
