package org.hzero.halm.fam.infra.repository.impl;

import org.hzero.halm.afm.infra.utils.IdsExportConvert;
import org.hzero.halm.fam.api.dto.FixedAssetsChangesDTO;
import org.hzero.halm.fam.infra.mapper.FixedAssetsChangesMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.halm.fam.domain.entity.FixedAssetsChanges;
import org.hzero.halm.fam.domain.repository.FixedAssetsChangesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 固定资产价值变动 资源库实现
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:23:27
 */
@Component
public class FixedAssetsChangesRepositoryImpl extends BaseRepositoryImpl<FixedAssetsChanges> implements FixedAssetsChangesRepository {

    @Autowired
    FixedAssetsChangesMapper fixedAssetsChangesMapper;

    @Override
    public List<FixedAssetsChanges> listFixedAssetsChanges(Long tenantId, FixedAssetsChanges fixedAssetsChanges) {
        fixedAssetsChanges.setTenantId(tenantId);
        return fixedAssetsChangesMapper.listFixedAssetsChanges(fixedAssetsChanges);
    }

    @Override
    public List<FixedAssetsChanges> listFixedAssetsChangesByFixedAssetsIds(Long tenantId, String fixedAssetsIds) {
        List<FixedAssetsChanges> fixedAssetsChangesList =
                fixedAssetsChangesMapper.listFixedAssetsChangesByFixedAssetsIds(tenantId, IdsExportConvert.stringToList(fixedAssetsIds));
        return fixedAssetsChangesList;
    }

    @Override
    public List<FixedAssetsChangesDTO> listFixedAssetsChangesDTOByFixedAssetsId(Long tenantId, Long fixedAssetsId) {
        List<FixedAssetsChangesDTO> fixedAssetsChangesList =
                fixedAssetsChangesMapper.listFixedAssetsChangesDTOByFixedAssetsId(tenantId, fixedAssetsId);
        return fixedAssetsChangesList;
    }

    @Override
    public List<FixedAssetsChangesDTO> listFixedAssetsChangesDTO(Long tenantId, String fixedAssetsIds) {
        List<FixedAssetsChangesDTO> FixedAssetsChangesDTOList = fixedAssetsChangesMapper.listFixedAssetsChangesDTOByIds(tenantId,IdsExportConvert.stringToList(fixedAssetsIds));
        return FixedAssetsChangesDTOList;
    }
}
