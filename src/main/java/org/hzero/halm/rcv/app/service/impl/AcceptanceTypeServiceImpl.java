package org.hzero.halm.rcv.app.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.hzero.halm.rcv.app.service.AcceptanceTypeService;
import org.hzero.halm.rcv.domain.entity.AcceptanceType;
import org.hzero.halm.rcv.domain.repository.AcceptanceTypeRepository;
import org.hzero.halm.rcv.infra.constant.ArcvConstants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.choerodon.core.exception.CommonException;

/**
 * 验收单类型应用服务默认实现
 *
 * @author zhiguang.guo@hand-china.com 2019-04-17 17:55:24
 */
@Service
public class AcceptanceTypeServiceImpl implements AcceptanceTypeService {

    @Autowired
    private AcceptanceTypeRepository acceptanceTypeRepository;
    @Autowired
    private LovAdapter lovAdapter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AcceptanceType acceptanceTypeCreateAndEdit(Long tenantId, AcceptanceType acceptanceType) {
            //验证唯一性
            validUniqueFields(tenantId, acceptanceType);
            acceptanceType.setTenantId(tenantId);
            if(Objects.isNull(acceptanceType.getAcceptanceTypeId())){
                acceptanceTypeRepository.insert(acceptanceType);
            }else{
                acceptanceTypeRepository.updateOptional(acceptanceType,AcceptanceType.FIELD_CODE_RULE,AcceptanceType.FIELD_ACCEPTANCE_TYPE_CODE,AcceptanceType.FIELD_CODE,
                        AcceptanceType.FIELD_FULL_NAME,AcceptanceType.FIELD_SHORT_NAME,AcceptanceType.FIELD_PROJECT_FLAG,AcceptanceType.FIELD_BUDGET_FLAG,AcceptanceType.FIELD_TRANSFER_FIXED_CODE,
                        AcceptanceType.FIELD_APPROVE_FLOW_FLAG,AcceptanceType.FIELD_TAG,AcceptanceType.FIELD_COMPLETE_ASSET_STATUS_ID,AcceptanceType.FIELD_TRANSFER_INTERFACE_FLAG,
                        AcceptanceType.FIELD_DIRECTLY_COMPLETE_FLAG,AcceptanceType.FIELD_IN_CONTRACT_FLAG,AcceptanceType.FIELD_CREATE_FA_FLAG,AcceptanceType.FIELD_ENABLED_FLAG,
                        AcceptanceType.FIELD_DESCRIPTION);
            }
        return acceptanceType;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AcceptanceType acceptanceTypeDetail(Long tenantId, Long acceptanceTypeId) {
        return acceptanceTypeRepository.acceptanceTypeDetail(tenantId, acceptanceTypeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AcceptanceType changeAssetCatalogEnabledFlag(Long tenantId, Long acceptanceTypeId, Integer enabledFlag) {
        AcceptanceType acceptanceType = acceptanceTypeRepository.selectByPrimaryKey(acceptanceTypeId);
        acceptanceType.setEnabledFlag(enabledFlag);
        acceptanceTypeRepository.updateOptional(acceptanceType,AcceptanceType.FIELD_ENABLED_FLAG);
        return acceptanceType;
    }

    private void validUniqueFields(Long tenantId, AcceptanceType acceptanceType) {
        // 校验唯一性
        int count = acceptanceTypeRepository.selectCountByCondition(Condition.builder(AcceptanceType.class)
                    .andWhere(Sqls.custom()
                            .andEqualTo(AcceptanceType.FIELD_TENANT_ID, tenantId)
                            .andEqualTo(AcceptanceType.FIELD_FULL_NAME, acceptanceType.getFullName())
                            .andNotEqualTo(AcceptanceType.FIELD_ACCEPTANCE_TYPE_ID, acceptanceType.getAcceptanceTypeId(),true)).build());

         if (count > 0){
            throw new CommonException(ArcvConstants.ArcvErrorCode.ARCV_DUPLICATE_FULL_NAME,acceptanceType.getFullName());
        }
    }

    @Override
    public List<String> getListByCondition(Long tenantId, String condition, String lovCode) {
        // 处理查询condition参数
        List<String> codeList = new ArrayList<>();
        if (StringUtils.isNotEmpty(condition)) {
            // 处理状态
            List<LovValueDTO> processStatusLov = lovAdapter.queryLovValue(lovCode, tenantId);
            if (CollectionUtils.isNotEmpty(processStatusLov)) {
                processStatusLov.forEach(location -> {
                    if (StringUtils.contains(location.getMeaning(), condition)) {
                        codeList.add(location.getValue());
                    }
                });
            }
        }
        return codeList;
    }

    @Override
    public List<String> getListByAccTypeCondition(String accTypeCondition) {
        if(StringUtils.isNotEmpty(accTypeCondition)){
            List<String> asList = Arrays.asList(accTypeCondition.split(","));
            return asList;
        }else{
            return null;
        }
    }
}
