package org.hzero.halm.afm.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang.StringUtils;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.algorithm.tree.TreeBuilder;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.api.dto.TransactionTypesDTO;
import org.hzero.halm.afm.app.service.TransactionTypesService;
import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.halm.afm.domain.repository.TransactionTypeFieldsRepository;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
import org.hzero.halm.afm.infra.constant.Constants;
import org.hzero.halm.afm.infra.mapper.TransactionTypesMapper;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hzero.mybatis.domian.Condition;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资产事务处理类型应用服务默认实现
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:43:01
 */
@Service
public class TransactionTypesServiceImpl implements TransactionTypesService {

    @Autowired
    TransactionTypesRepository transactionTypesRepository;
    @Autowired
    TransactionTypeFieldsRepository transactionTypeFieldsRepository;

    @Autowired
    TransactionTypesMapper transactionTypesMapper;
    @Autowired
    LovValueHandle lovValueHandle;

    @Override
    public List<TransactionTypesDTO> treeTransactionTypes(TransactionTypes transactionTypes, Long tenantId) {
        transactionTypes.setTenantId(tenantId);
        List<TransactionTypesDTO> allList = transactionTypesMapper.selectAllNodesTransactionTypes(transactionTypes);
        allList = (List<TransactionTypesDTO>) lovValueHandle.process(null, allList);
        List<TransactionTypesDTO> returnResult = TreeBuilder.buildTree(allList, TransactionTypes.ROOT_KEY, TransactionTypesDTO::getTransactionTypeId,
                taskTemplatesDTO -> taskTemplatesDTO.getParentTypeId() != null ? taskTemplatesDTO.getParentTypeId() : TransactionTypes.ROOT_KEY);
        Collections.sort(returnResult);

        return returnResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionTypes saveTransactionTypes(Long tenantId, TransactionTypes transactionTypes) {
        transactionTypes.setTenantId(tenantId);
        return saveTransactionTypes(transactionTypes);
    }

    @Override
    public TransactionTypes getTransactionTypes(Long tenantId, Long transactionTypeId) {
        TransactionTypes transactionTypes = transactionTypesRepository.getTransactionTypes(tenantId, transactionTypeId);

        List<TransactionTypeFields> transactionTypeFieldsList = transactionTypeFieldsRepository.listTransactionTypeFields(tenantId, transactionTypeId);
        //处理值集
        transactionTypeFieldsList = (List<TransactionTypeFields>) lovValueHandle.process(null, transactionTypeFieldsList);

        //按分类分组
        Map<String, List<TransactionTypeFields>> groupMap
                = transactionTypeFieldsList.stream().collect(Collectors.groupingBy(TransactionTypeFields::getFieldClass));
        transactionTypes.setBasicAssetColumnList(groupMap.get(TransactionTypeFields.CLASS_BASIC_ASSET_COLUMN));
        transactionTypes.setAttributeColumnList(groupMap.get(TransactionTypeFields.CLASS_ATTRIBUTE_COLUMN));
        transactionTypes.setTrackingManagementColumnList(groupMap.get(TransactionTypeFields.CLASS_TRACKING_AND_MANAGEMENT_COLUMN));

        return transactionTypes;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionTypes disabledTransactionTypes(Long organizationId, TransactionTypes transactionTypes) {

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.selectNodeAndChildNodeTransactionTypes(organizationId, transactionTypes);
        transactionTypesList.forEach(transactionType -> {
            transactionType.setEnabledFlag(BaseConstants.Flag.NO);
        });
        transactionTypesRepository.batchUpdateOptional(transactionTypesList, TransactionTypes.FIELD_ENABLED_FLAG);

        return transactionTypes;
    }

    @Override
    public TransactionTypes enabledTransactionTypes(Long organizationId, TransactionTypes transactionTypes) {
        //仅能启用父级为可用的
        if (transactionTypes.getParentTypeId() != null) {
            TransactionTypes parent = transactionTypesRepository.getTransactionTypes(organizationId, transactionTypes.getParentTypeId());
            if ((int) parent.getEnabledFlag() == BaseConstants.Flag.NO) {
                throw new CommonException(Constants.AafmErrorCode.TRANSACTION_TYPE_ENABLED);
            }
        }
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.selectNodeAndChildNodeTransactionTypes(organizationId, transactionTypes);
        transactionTypesList.forEach(transactionType -> {
            transactionType.setEnabledFlag(BaseConstants.Flag.YES);
        });
        transactionTypesRepository.batchUpdateOptional(transactionTypesList, TransactionTypes.FIELD_ENABLED_FLAG);

        return transactionTypes;
    }

    @Override
    public List<TransactionTypesDTO> exportTransactionTypes(TransactionTypesDTO transactionTypesDto) {
        //查询头
        List<TransactionTypesDTO> transactionTypesExportList = transactionTypesRepository.exportTransactionTypes(transactionTypesDto);
        return transactionTypesExportList;
    }

    @Override
    public Page<TransactionTypes> pageTransactionTypes(PageRequest pageRequest, TransactionTypes transactionTypes) {
        return PageHelper.doPageAndSort(pageRequest, () -> {
            return transactionTypesRepository.listTransactionTypes(transactionTypes);
        });
    }

    /**
     * 保存事件类型
     *
     * @param transactionTypes 事件信息
     * @return
     */
    private TransactionTypes saveTransactionTypes(TransactionTypes transactionTypes) {
        if (transactionTypes.getTransactionTypeId() == null) {

            Long parentId = transactionTypes.getParentTypeId();

            // 如果存在父节点
            if (Objects.nonNull(parentId)) {
                TransactionTypes parentTransactionTypes = transactionTypesRepository.selectByPrimaryKey(parentId);
                if (Objects.isNull(parentTransactionTypes.getLevelPath())) {
                    transactionTypes.setLevelPath(parentId.toString());
                } else {
                    transactionTypes.setLevelPath(parentTransactionTypes.getLevelPath() + BaseConstants.Symbol.SLASH + parentId);
                }
                transactionTypes.setLevelNumber(parentTransactionTypes.getLevelNumber() + 1);
            } else {
                transactionTypes.setLevelNumber(Constants.DEFAULT_LEVEL_NUMBER);
                transactionTypes.setLevelPath(StringUtils.EMPTY);
            }

            transactionTypesRepository.insertSelective(transactionTypes);
        } else {
            transactionTypesRepository.updateByPrimaryKey(transactionTypes);
        }

        //保存行
        List<TransactionTypeFields> basicAssetColumnList = saveTransactionTypeFields(transactionTypes.getTenantId(),
                transactionTypes.getTransactionTypeId(),
                TransactionTypeFields.CLASS_BASIC_ASSET_COLUMN,
                transactionTypes.getBasicAssetColumnList());
        transactionTypes.setBasicAssetColumnList(basicAssetColumnList);

        List<TransactionTypeFields> attributeColumnList = saveTransactionTypeFields(transactionTypes.getTenantId(),
                transactionTypes.getTransactionTypeId(),
                TransactionTypeFields.CLASS_ATTRIBUTE_COLUMN,
                transactionTypes.getAttributeColumnList());
        transactionTypes.setAttributeColumnList(attributeColumnList);

        List<TransactionTypeFields> trackingManagementColumnList = saveTransactionTypeFields(transactionTypes.getTenantId(),
                transactionTypes.getTransactionTypeId(),
                TransactionTypeFields.CLASS_TRACKING_AND_MANAGEMENT_COLUMN,
                transactionTypes.getTrackingManagementColumnList());
        transactionTypes.setTrackingManagementColumnList(trackingManagementColumnList);

        //删除行数据
        batchDeleteTaskTemplatesRels(transactionTypes.getDeleteColumnList());

        return transactionTypes;
    }

    /**
     * 保存行
     *
     * @param tenantId                  租户ID
     * @param transactionTypesId        事件ID
     * @param fieldClass                所属分类
     * @param transactionTypeFieldsList transactionTypeFieldsList
     * @return
     */
    private List<TransactionTypeFields> saveTransactionTypeFields(Long tenantId, Long transactionTypesId, String fieldClass, List<TransactionTypeFields> transactionTypeFieldsList) {
        if (Objects.nonNull(transactionTypeFieldsList)) {

            //设置分类
            transactionTypeFieldsList.forEach(transactionTypeFields -> {
                transactionTypeFields.setTenantId(tenantId);
                transactionTypeFields.setTransationTypeId(transactionTypesId);
                transactionTypeFields.setFieldClass(fieldClass);
            });

            for (int i = 0; i < transactionTypeFieldsList.size(); i++) {
                for (int j = 0; j < transactionTypeFieldsList.size(); j++) {
                    if (i != j
                            && transactionTypeFieldsList.get(i).getFieldColumn().equals(transactionTypeFieldsList.get(j).getFieldColumn())) {
                        throw new CommonException(Constants.AafmErrorCode.TRANSACTION_TYPE_FIELD_COLUMN_DUPLICATED, transactionTypeFieldsList.get(i).getFieldColumnMeaning());
                    }
                }
            }

            //区分更新和新增
            Map<String, List<TransactionTypeFields>> groupMap =
                    transactionTypeFieldsList.stream().collect(Collectors.groupingBy((e) -> {
                        return e.getFieldId() == null ? TransactionTypeFields.SAVE_TYPE_INSERT : TransactionTypeFields.SAVE_TYPE_UPDATE;
                    }));

            List<TransactionTypeFields> insertList = groupMap.get(TransactionTypeFields.SAVE_TYPE_INSERT);
            List<TransactionTypeFields> updateList = groupMap.get(TransactionTypeFields.SAVE_TYPE_UPDATE);

            transactionTypeFieldsRepository.batchInsertSelective(insertList);
            transactionTypeFieldsRepository.batchUpdateOptional(updateList,
                    TransactionTypeFields.FIELD_FIELD_COLUMN,
                    TransactionTypeFields.FIELD_FIELD_TYPE);

        }

        return transactionTypeFieldsList;
    }

    /**
     * 删除行
     *
     * @param transactionTypeFieldsList transactionTypeFieldsList
     * @return
     */
    private void batchDeleteTaskTemplatesRels(List<TransactionTypeFields> transactionTypeFieldsList) {
        if (Objects.nonNull(transactionTypeFieldsList)) {
            transactionTypeFieldsRepository.batchDelete(transactionTypeFieldsList);
        }
    }
}
