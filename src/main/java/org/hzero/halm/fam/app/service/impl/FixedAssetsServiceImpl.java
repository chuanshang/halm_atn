package org.hzero.halm.fam.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang.StringUtils;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.halm.fam.api.dto.FixedAssetsChangesDTO;
import org.hzero.halm.fam.api.dto.FixedAssetsDTO;
import org.hzero.halm.fam.app.service.FixedAssetsService;
import org.hzero.halm.fam.domain.entity.FixedAssets;
import org.hzero.halm.fam.domain.entity.FixedAssetsChanges;
import org.hzero.halm.fam.domain.repository.FixedAssetsChangesRepository;
import org.hzero.halm.fam.domain.repository.FixedAssetsRepository;
import org.hzero.halm.fam.infra.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 固定资产应用服务默认实现
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:22:24
 */
@Service
public class FixedAssetsServiceImpl implements FixedAssetsService {

    @Autowired
    FixedAssetsRepository fixedAssetsRepository;

    @Autowired
    FixedAssetsChangesRepository fixedAssetsChangesRepository;

    @Autowired
    LovValueHandle lovValueHandle;

    @Override
    public Page<FixedAssetsDTO> pageFixedAssets(Long tenantId, FixedAssets fixedAssets, PageRequest pageRequest) {
        Page<FixedAssetsDTO> fixedAssetsDTOPage = PageHelper.doPageAndSort(pageRequest, () -> {
            return fixedAssetsRepository.listFixedAssets(tenantId, fixedAssets);
        });

        calculationChange(tenantId, fixedAssetsDTOPage.getContent());

        return fixedAssetsDTOPage;
    }

    @Override
    public FixedAssetsDTO getFixedAssets(Long tenantId, Long fixedAssetId) {
        FixedAssetsDTO fixedAssetsDto = fixedAssetsRepository.getFixedAssets(tenantId, fixedAssetId);

        FixedAssetsChanges queryFixedAssetsChanges = new FixedAssetsChanges();
        queryFixedAssetsChanges.setTenantId(tenantId);
        queryFixedAssetsChanges.setFixedAssetId(fixedAssetId);

        List<FixedAssetsChangesDTO> fixedAssetsChangesList =
                fixedAssetsChangesRepository.listFixedAssetsChangesDTOByFixedAssetsId(tenantId, fixedAssetId);
        fixedAssetsChangesList = (List<FixedAssetsChangesDTO>) lovValueHandle.process(null, fixedAssetsChangesList);
        fixedAssetsDto.setFixedAssetsChangesDTOList(fixedAssetsChangesList);

        setFixedAssetsDTOChange(fixedAssetsDto,fixedAssetsChangesList);
        
        return fixedAssetsDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FixedAssets createAndUpdateFixedAssets(Long tenantId, FixedAssets fixedAssets) {
        fixedAssets.setTenantId(tenantId);

        if (fixedAssets.getFixedAssetId() == null) {
            fixedAssetsRepository.insertSelective(fixedAssets);
        } else {
            fixedAssetsRepository.updateOptional(fixedAssets,
                    FixedAssets.FIELD_ASSET_BOOK_CODE,
                    FixedAssets.FIELD_ASSET_CATALOG_ID,
                    FixedAssets.FIELD_DEPRECIATION_MOUTH,
                    FixedAssets.FIELD_DEPRECIATION_START_DATE,
                    FixedAssets.FIELD_DEPRECIATION_TYPE_CODE,
                    FixedAssets.FIELD_DESCRIPTION,
                    FixedAssets.FIELD_FINANCIAL_NUM,
                    FixedAssets.FIELD_FIXED_ASSET_NAME,
                    FixedAssets.FIELD_RESIDUAL_VALUE_RATE,
                    FixedAssets.FIELD_TRANSFER_DATE);
        }

        //保存行
        batchSaveFixedAssetsChanges(tenantId, fixedAssets.getFixedAssetId(), fixedAssets.getFixedAssetsChangesList());

        return fixedAssets;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFixedAssets(Long tenantId, List<FixedAssets> fixedAssetsList) {
        Set<Long> fixedAssetsIdSet = fixedAssetsList.stream().map(FixedAssets::getFixedAssetId).collect(Collectors.toSet());
        //根据头id查行
        List<FixedAssetsChanges> fixedAssetsChangesList = fixedAssetsChangesRepository.listFixedAssetsChangesByFixedAssetsIds(tenantId, StringUtils.join(fixedAssetsIdSet, ','));

        //删除
        fixedAssetsRepository.batchDeleteByPrimaryKey(fixedAssetsList);
        fixedAssetsChangesRepository.batchDeleteByPrimaryKey(fixedAssetsChangesList);
    }

    @Override
    public Page<FixedAssetsChanges> pageFixedAssetsChanges(Long tenantId, Long fixedAssetId, PageRequest pageRequest) {
        FixedAssetsChanges queryFixedAssetsChanges = new FixedAssetsChanges();
        queryFixedAssetsChanges.setTenantId(tenantId);
        queryFixedAssetsChanges.setFixedAssetId(fixedAssetId);

        return PageHelper.doPageAndSort(pageRequest, () -> {
            return fixedAssetsChangesRepository.listFixedAssetsChanges(tenantId, queryFixedAssetsChanges);
        });
    }

    @Override
    public List<FixedAssetsChanges> listFixedAssetsChanges(Long tenantId, Long fixedAssetId) {
        FixedAssetsChanges queryFixedAssetsChanges = new FixedAssetsChanges();
        queryFixedAssetsChanges.setTenantId(tenantId);
        queryFixedAssetsChanges.setFixedAssetId(fixedAssetId);

        return fixedAssetsChangesRepository.listFixedAssetsChanges(tenantId, queryFixedAssetsChanges);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFixedAssetsChanges(Long tenantId, List<FixedAssetsChanges> fixedAssetsChangesList) {
        fixedAssetsChangesRepository.batchDeleteByPrimaryKey(fixedAssetsChangesList);
    }

    @Override
    public List<FixedAssetsDTO> exportFixedAssets(FixedAssetsDTO fixedAssetsDto) {
        //查询头
        List<FixedAssetsDTO> fixedAssetsExportList = fixedAssetsRepository.exportFixedAssets(fixedAssetsDto);
        calculationChange(fixedAssetsDto.getTenantId(), fixedAssetsExportList);
        return fixedAssetsExportList;
    }

    /**
     * 批量保存行
     *
     * @param tenantId
     * @param fixedAssetId
     * @param fixedAssetsChangesList
     */
    private void batchSaveFixedAssetsChanges(Long tenantId, Long fixedAssetId, List<FixedAssetsChanges> fixedAssetsChangesList) {

        fixedAssetsChangesList.forEach(e -> {
            e.setTenantId(tenantId);
            e.setFixedAssetId(fixedAssetId);
        });

        Map<String, List<FixedAssetsChanges>> groupMap = fixedAssetsChangesList.stream().collect(Collectors.groupingBy((e) -> {
            return e.getChangeId() == null ? FixedAssets.SAVE_TYPE_INSERT : FixedAssets.SAVE_TYPE_UPDATE;
        }));
        List<FixedAssetsChanges> insertFixedAssetsChangesList = groupMap.get(FixedAssets.SAVE_TYPE_INSERT);
        List<FixedAssetsChanges> updateFixedAssetsChangesList = groupMap.get(FixedAssets.SAVE_TYPE_UPDATE);

        fixedAssetsChangesRepository.batchInsertSelective(insertFixedAssetsChangesList);
        fixedAssetsChangesRepository.batchUpdateOptional(updateFixedAssetsChangesList,
                FixedAssetsChanges.FIELD_ACCOUNTING_VOUCHER_NUMBER,
                FixedAssetsChanges.FIELD_CHANGE_TYPE_CODE,
                FixedAssetsChanges.FIELD_CHANGE_VALUE,
                FixedAssetsChanges.FIELD_CORRELATED_EVENT,
                FixedAssetsChanges.FIELD_DESCRIPTION,
                FixedAssetsChanges.FIELD_PERIOD_NAME);
    }

    /**
     * 批量计算价值变更
     *
     * @param tenantId           租户ID
     * @param fixedAssetsDTOList fixedAssetsDTOList
     */
    private List<FixedAssetsDTO> calculationChange(Long tenantId, List<FixedAssetsDTO> fixedAssetsDTOList) {
        //计算
        Set<Long> fixedAssetsIdSet =
                fixedAssetsDTOList.stream().map(fixedAssetsDTO -> fixedAssetsDTO.getFixedAssetId()).collect(Collectors.toSet());
        //根据头id查行
        List<FixedAssetsChangesDTO> allFixedAssetsChangesList =
                fixedAssetsChangesRepository.listFixedAssetsChangesDTO(tenantId,
                        org.apache.commons.lang3.StringUtils.join(fixedAssetsIdSet, ','));
        //按照头id分组
        Map<Long, List<FixedAssetsChangesDTO>> fixedAssetsChangesMap =
                allFixedAssetsChangesList.stream().collect(Collectors.groupingBy(FixedAssetsChangesDTO::getFixedAssetId));

        fixedAssetsDTOList.forEach(fixedAssetsDTO -> {
            //当前固定资产下的价值变动
            List<FixedAssetsChangesDTO> fixedAssetsChangesList = fixedAssetsChangesMap.get(fixedAssetsDTO.getFixedAssetId());
            fixedAssetsDTO.setFixedAssetsChangesDTOList(fixedAssetsChangesList);
            setFixedAssetsDTOChange(fixedAssetsDTO,fixedAssetsChangesList);
        });
        return fixedAssetsDTOList;
    }

    private void setFixedAssetsDTOChange(FixedAssetsDTO fixedAssetsDTO,List<FixedAssetsChangesDTO> fixedAssetsChangesList){
        if (fixedAssetsChangesList != null && fixedAssetsChangesList.size() > 0) {
            //根据类型分组
            Map<String, List<FixedAssetsChangesDTO>> changeTypeCodeMap =
                    fixedAssetsChangesList.stream().collect(Collectors.groupingBy(FixedAssetsChangesDTO::getChangeTypeCode));

            //增加类型
            List<FixedAssetsChangesDTO> addChangeTypeCodeList = changeTypeCodeMap.get(Constants.FixedAssetsChangeTypeCode.ADD);
            //追加类型
            List<FixedAssetsChangesDTO> appendChangeTypeCodeList = changeTypeCodeMap.get(Constants.FixedAssetsChangeTypeCode.APPEND);
            //折旧类型
            List<FixedAssetsChangesDTO> depreciationChangeTypeCodeList = changeTypeCodeMap.get(Constants.FixedAssetsChangeTypeCode.DEPRECIATION);
            //计划外折旧类型
            List<FixedAssetsChangesDTO> udChangeTypeCodeList = changeTypeCodeMap.get(Constants.FixedAssetsChangeTypeCode.UNPLANNED_DEPRECIATION);

            BigDecimal sumAddVal = sumChangeTypeValue(addChangeTypeCodeList);
            BigDecimal sumAppendVal = sumChangeTypeValue(appendChangeTypeCodeList);
            BigDecimal sumDVal = sumChangeTypeValue(depreciationChangeTypeCodeList);
            BigDecimal sumUdVal = sumChangeTypeValue(udChangeTypeCodeList);

            BigDecimal residualValueRate = fixedAssetsDTO.getResidualValueRate()!=null
                    ? fixedAssetsDTO.getResidualValueRate() : new BigDecimal("0");


            //初始原值，计算逻辑：价值变动中，类型为“增加”的金额总和
            BigDecimal initOriginalValue = sumAddVal;
            //当前原值，计算逻辑：价值变动中，类型为“增加”&“追加”的金额总和
            BigDecimal currentOriginalValue = sumAddVal.add(sumAppendVal);
            //累计折旧，计算逻辑：价值变动中，类型为“折旧”和“计划外折旧”的金额总和
            BigDecimal accumulatedDepreciation = sumDVal.add(sumUdVal);
            //净值，计算逻辑：当前原值 - 累计折旧
            BigDecimal netValue = currentOriginalValue.subtract(accumulatedDepreciation);
            //残值，计算逻辑：当前原值 *残值率（%）
            BigDecimal residualValue =
                    currentOriginalValue.multiply(residualValueRate).divide(new BigDecimal("100"),2);

            fixedAssetsDTO.setInitOriginalValue(moneyFormat(initOriginalValue));
            fixedAssetsDTO.setCurrentOriginalValue(moneyFormat(currentOriginalValue));
            fixedAssetsDTO.setAccumulatedDepreciation(moneyFormat(accumulatedDepreciation));
            fixedAssetsDTO.setNetValue(moneyFormat(netValue));
            fixedAssetsDTO.setResidualValue(moneyFormat(residualValue));
        }
    }

    private String moneyFormat(BigDecimal money){
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(money);
    }

    private BigDecimal sumChangeTypeValue(List<FixedAssetsChangesDTO> changeTypeCodeList){
        BigDecimal sumVal = new BigDecimal("0");
        if (changeTypeCodeList != null) {
            for (int idx = 0; idx < changeTypeCodeList.size(); idx++) {
                BigDecimal val = changeTypeCodeList.get(idx).getChangeValue();
                if(val!=null){
                    sumVal = sumVal.add(val);
                }
            }
        }
        return sumVal;
    }
}
