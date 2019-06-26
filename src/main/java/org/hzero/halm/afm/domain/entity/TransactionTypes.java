package org.hzero.halm.afm.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.choerodon.core.exception.CommonException;
import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.halm.afm.domain.repository.TransactionTypesRepository;
import org.hzero.halm.afm.infra.constant.Constants;

import java.util.List;

/**
 * 资产事务处理类型
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:43:01
 */
@ApiModel("资产事务处理类型")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_transaction_types")
public class TransactionTypes extends AuditDomain {

    public static final String FIELD_TRANSACTION_TYPE_ID = "transactionTypeId";
    public static final String FIELD_BASIC_TYPE_CODE = "basicTypeCode";
    public static final String FIELD_TRANSACTION_TYPE_CODE = "transactionTypeCode";
    public static final String FIELD_SHORT_NAME = "shortName";
    public static final String FIELD_LONG_NAME = "longName";
    public static final String FIELD_PARENT_TYPE_ID = "parentTypeId";
    public static final String FIELD_LEVEL_PATH = "levelPath";
    public static final String FIELD_LEVEL_NUMBER = "levelNumber";
    public static final String FIELD_CODE_RULE = "codeRule";
    public static final String FIELD_NEED_TWICE_CONFIRM = "needTwiceConfirm";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_TAG = "tag";
    public static final String FIELD_ORGANIZATION_SCOPE = "organizationScope";
    public static final String FIELD_STATUS_SCOPE = "statusScope";
    public static final String FIELD_SPECIALTY_SCOPE = "specialtyScope";
    public static final String FIELD_SPECIAL_ASSET_FLAG = "specialAssetFlag";
    public static final String FIELD_SPECIAL_ASSET = "specialAsset";
    public static final String FIELD_CHECK_TARGET_ORG_FLAG = "checkTargetOrgFlag";
    public static final String FIELD_CHECK_CURRENT_ORG_FLAG = "checkCurrentOrgFlag";
    public static final String FIELD_CROSS_LEGAL_FLAG = "crossLegalFlag";
    public static final String FIELD_STATUS_UPDATE_FLAG = "statusUpdateFlag";
    public static final String FIELD_TARGET_ASSET_STATUS_ID = "targetAssetStatusId";
    public static final String FIELD_INPROCESS_ASSET_STATUS_ID = "inprocessAssetStatusId";
    public static final String FIELD_TARGET_ASSET_STATUS_SCOPE = "targetAssetStatusScope";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_BASIC_COLUMN_FLAG = "basicColumnFlag";
    public static final String FIELD_ATTRIBUTE_COLUMN_FLAG = "attributeColumnFlag";
    public static final String FIELD_TRACKING_FLAG = "trackingFlag";
    public static final String FIELD_TENANT_ID = "tenantId";

    public static final long ROOT_KEY = 0L;
    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    /**
     * 校验
     *
     * @param tenantId                   租户ID
     * @param transactionTypesRepository transactionTypesRepository
     */
    public void validateTransactionTypes(Long tenantId, TransactionTypesRepository transactionTypesRepository) {
        //校验名称是否重复
        validUniqueIndex(tenantId, transactionTypesRepository);

    }

    /**
     * 唯一索引校验
     *
     * @param tenantId 租户ID
     */
    public void validUniqueIndex(Long tenantId, TransactionTypesRepository transactionTypesRepository) {
        if (transactionTypesRepository.selectCountByCondition(
                org.hzero.mybatis.domian.Condition.builder(TransactionTypes.class)
                        .andWhere(
                                org.hzero.mybatis.util.Sqls.custom()
                                        .andEqualTo(TransactionTypes.FIELD_TENANT_ID, tenantId)
                                        .andEqualTo(TransactionTypes.FIELD_TRANSACTION_TYPE_CODE, this.transactionTypeCode)
                                        .andNotEqualTo(TransactionTypes.FIELD_TRANSACTION_TYPE_ID, this.transactionTypeId, true)
                        ).build()) != 0) {
            throw new CommonException(Constants.AafmErrorCode.TRANSACTION_TYPE_CODE_DUPLICATED, this.transactionTypeCode);
        }
    }


    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long transactionTypeId;
    @ApiModelProperty(value = "基础类型，独立值集：AAFM.ASSET_TRANSACTION_TYPE")
    @LovValue(value = "AAFM.ASSET_TRANSACTION_TYPE", meaningField = "basicTypeMeaning")
    @NotBlank
    private String basicTypeCode;
    @ApiModelProperty(value = "基础类型meaning")
    @Transient
    private String basicTypeMeaning;
    @ApiModelProperty(value = "代码")
    private String transactionTypeCode;
    @ApiModelProperty(value = "事件短名称")
    @NotBlank
    private String shortName;
    @ApiModelProperty(value = "事件完整名称")
    @NotBlank
    private String longName;
    @ApiModelProperty(value = "父类型ID,URL值集群：AAFM.TRANSACTION_TYPES")
    private Long parentTypeId;
    @ApiModelProperty(value = "父类型名称")
    @Transient
    private String parentTypeName;
    @ApiModelProperty(value = "层级结构的节点路径,递归父级id/本级id")
    private String levelPath;
    @ApiModelProperty(value = "当前所处层级")
    private Integer levelNumber;
    @ApiModelProperty(value = "自动编号规则ID")
    private String codeRule;
    @ApiModelProperty(value = "自动编号规则名称")
    @Transient
    private String codeRuleName;
    @ApiModelProperty(value = "是否需要2步确认,独立值集： AAFM.NEED_TWICE_CONFIRM")
    @LovValue(value = "AAFM.NEED_TWICE_CONFIRM", meaningField = "needTwiceConfirmMeaning")
    @NotBlank
    private String needTwiceConfirm;
    @ApiModelProperty(value = "是否需要2步确认meaning")
    @Transient
    private String needTwiceConfirmMeaning;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "标记")
    private String tag;
    @ApiModelProperty(value = "组织范围，URL值集：AMDM.ORGANIZATION")
    private String organizationScope;
    @ApiModelProperty(value = "状态范围，URL值集，AAFM.ASSET_STATUS")
    private String statusScope;
    @ApiModelProperty(value = "专业范围，URL值集，AAFM.ASSET_SPECIALTY")
    private String specialtyScope;
    @ApiModelProperty(value = "仅针对特殊资产")
    @NotNull
    private Integer specialAssetFlag;
    @ApiModelProperty(value = "特殊资产，值集：AMDM.SPECIAL_ASSET")
    @LovValue(value = "AAFM.SPECIAL_ASSET", meaningField = "specialAssetMeaning")
    private String specialAsset;
    @ApiModelProperty(value = "特殊资产meaning")
    @Transient
    private String specialAssetMeaning;
    @ApiModelProperty(value = "是否检查目标使用组织暂挂状态")
    @NotNull
    private Integer checkTargetOrgFlag;
    @ApiModelProperty(value = "是否检查当前所属组织暂挂状态")
    @NotNull
    private Integer checkCurrentOrgFlag;
    @ApiModelProperty(value = "更改所属组织时，是否跨法人单位")
    @NotNull
    private Integer crossLegalFlag;
    @ApiModelProperty(value = "是否需要修改资产状态，默认0")
    @NotNull
    private Integer statusUpdateFlag;
    @ApiModelProperty(value = "目标资产状态，URL值集：AAFM.ASSET_STATUS")
    private String targetAssetStatusId;
    @ApiModelProperty(value = "目标资产状态名称")
    @Transient
    private String targetAssetStatusName;
    @ApiModelProperty(value = "过程中资产状态，URL值集：AAFM.ASSET_STATUS")
    private String inprocessAssetStatusId;
    @ApiModelProperty(value = "过程中资产状态名称")
    @Transient
    private String inprocessAssetStatusName;
    @ApiModelProperty(value = "目标资产状态范围，URL值集：AAFM.ASSET_STATUS")
    private String targetAssetStatusScope;
    @ApiModelProperty(value = "是否启用，默认1")
    @NotNull
    private Integer enabledFlag;
    @ApiModelProperty(value = "涉及基本信息变更，默认0")
    @NotNull
    private Integer basicColumnFlag;
    @ApiModelProperty(value = "涉及属性描述变更，默认0")
    @NotNull
    private Integer attributeColumnFlag;
    @ApiModelProperty(value = "涉及跟踪与管理变更，默认0")
    @NotNull
    private Integer trackingFlag;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    @Transient
    @ApiModelProperty(value = "明细页列表查询字段")
    private String detailSelectItem;

    @ApiModelProperty(value = "仅查询已启用")
    @Transient
    private Integer onlyEnabledFlag;

    @ApiModelProperty(value = "涉及基本信息变更list,独立值集 AAFM.ASSET_BASIC_COLUMN")
    @Transient
    private List<TransactionTypeFields> basicAssetColumnList;

    @ApiModelProperty(value = "涉及属性描述变更list")
    @Transient
    private List<TransactionTypeFields> attributeColumnList;

    @ApiModelProperty(value = "涉及跟踪与管理变更list,独立值集 AAFM.ASSET_TRACKING_MANAGEMENT_COLUMN")
    @Transient
    private List<TransactionTypeFields> trackingManagementColumnList;

    @ApiModelProperty(value = "需要删除行list")
    @Transient
    private List<TransactionTypeFields> deleteColumnList;

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    /**
     * @return 基础类型，独立值集：APFM.ASSET_TRANSACTION_TYPE
     */
    public String getBasicTypeCode() {
        return basicTypeCode;
    }

    public void setBasicTypeCode(String basicTypeCode) {
        this.basicTypeCode = basicTypeCode;
    }

    /**
     * @return 代码
     */
    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    /**
     * @return 事件短名称
     */
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return 时间完整名称
     */
    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    /**
     * @return 父类型ID
     */
    public Long getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(Long parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    /**
     * @return 层级结构的节点路径, 递归父级id/本级id
     */
    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }

    /**
     * @return 当前所处层级
     */
    public Integer getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(Integer levelNumber) {
        this.levelNumber = levelNumber;
    }

    /**
     * @return 自动编号规则ID
     */
    public String getCodeRule() {
        return codeRule;
    }

    public void setCodeRule(String codeRule) {
        this.codeRule = codeRule;
    }

    /**
     * @return 是否需要2步确认
     */
    public String getNeedTwiceConfirm() {
        return needTwiceConfirm;
    }

    public void setNeedTwiceConfirm(String needTwiceConfirm) {
        this.needTwiceConfirm = needTwiceConfirm;
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return 图标
     */
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return 标记
     */
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return 组织范围，URL值集：AMDM.ORGANIZATION
     */
    public String getOrganizationScope() {
        return organizationScope;
    }

    public void setOrganizationScope(String organizationScope) {
        this.organizationScope = organizationScope;
    }

    /**
     * @return 状态范围，URL值集，AAFM.ASSET_STATUS
     */
    public String getStatusScope() {
        return statusScope;
    }

    public void setStatusScope(String statusScope) {
        this.statusScope = statusScope;
    }

    /**
     * @return 专业范围，URL值集，AAFM.ASSET_SPECIALTY
     */
    public String getSpecialtyScope() {
        return specialtyScope;
    }

    public void setSpecialtyScope(String specialtyScope) {
        this.specialtyScope = specialtyScope;
    }

    /**
     * @return 仅针对特殊资产
     */
    public Integer getSpecialAssetFlag() {
        return specialAssetFlag;
    }

    public void setSpecialAssetFlag(Integer specialAssetFlag) {
        this.specialAssetFlag = specialAssetFlag;
    }

    /**
     * @return 特殊资产，值集：AMDM.SPECIAL_ASSET
     */
    public String getSpecialAsset() {
        return specialAsset;
    }

    public void setSpecialAsset(String specialAsset) {
        this.specialAsset = specialAsset;
    }

    /**
     * @return 是否检查目标使用组织暂挂状态
     */
    public Integer getCheckTargetOrgFlag() {
        return checkTargetOrgFlag;
    }

    public void setCheckTargetOrgFlag(Integer checkTargetOrgFlag) {
        this.checkTargetOrgFlag = checkTargetOrgFlag;
    }

    /**
     * @return 是否检查当前所属组织暂挂状态
     */
    public Integer getCheckCurrentOrgFlag() {
        return checkCurrentOrgFlag;
    }

    public void setCheckCurrentOrgFlag(Integer checkCurrentOrgFlag) {
        this.checkCurrentOrgFlag = checkCurrentOrgFlag;
    }

    /**
     * @return 更改所属组织时，是否跨法人单位
     */
    public Integer getCrossLegalFlag() {
        return crossLegalFlag;
    }

    public void setCrossLegalFlag(Integer crossLegalFlag) {
        this.crossLegalFlag = crossLegalFlag;
    }

    /**
     * @return 是否需要修改资产状态，默认0
     */
    public Integer getStatusUpdateFlag() {
        return statusUpdateFlag;
    }

    public void setStatusUpdateFlag(Integer statusUpdateFlag) {
        this.statusUpdateFlag = statusUpdateFlag;
    }

    /**
     * @return 目标资产状态，URL值集：AAFM.ASSET_STATUS
     */
    public String getTargetAssetStatusId() {
        return targetAssetStatusId;
    }

    public void setTargetAssetStatusId(String targetAssetStatusId) {
        this.targetAssetStatusId = targetAssetStatusId;
    }

    /**
     * @return 过程中资产状态，URL值集：AAFM.ASSET_STATUS
     */
    public String getInprocessAssetStatusId() {
        return inprocessAssetStatusId;
    }

    public void setInprocessAssetStatusId(String inprocessAssetStatusId) {
        this.inprocessAssetStatusId = inprocessAssetStatusId;
    }

    /**
     * @return 目标资产状态范围，URL值集：AAFM.ASSET_STATUS
     */
    public String getTargetAssetStatusScope() {
        return targetAssetStatusScope;
    }

    public void setTargetAssetStatusScope(String targetAssetStatusScope) {
        this.targetAssetStatusScope = targetAssetStatusScope;
    }

    /**
     * @return 是否启用，默认1
     */
    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    /**
     * @return 租户ID
     */
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getBasicColumnFlag() {
        return basicColumnFlag;
    }

    public void setBasicColumnFlag(Integer basicColumnFlag) {
        this.basicColumnFlag = basicColumnFlag;
    }

    public Integer getAttributeColumnFlag() {
        return attributeColumnFlag;
    }

    public void setAttributeColumnFlag(Integer attributeColumnFlag) {
        this.attributeColumnFlag = attributeColumnFlag;
    }

    public Integer getTrackingFlag() {
        return trackingFlag;
    }

    public void setTrackingFlag(Integer trackingFlag) {
        this.trackingFlag = trackingFlag;
    }

    public List<TransactionTypeFields> getBasicAssetColumnList() {
        return basicAssetColumnList;
    }

    public void setBasicAssetColumnList(List<TransactionTypeFields> basicAssetColumnList) {
        this.basicAssetColumnList = basicAssetColumnList;
    }

    public List<TransactionTypeFields> getAttributeColumnList() {
        return attributeColumnList;
    }

    public void setAttributeColumnList(List<TransactionTypeFields> attributeColumnList) {
        this.attributeColumnList = attributeColumnList;
    }

    public List<TransactionTypeFields> getTrackingManagementColumnList() {
        return trackingManagementColumnList;
    }

    public void setTrackingManagementColumnList(List<TransactionTypeFields> trackingManagementColumnList) {
        this.trackingManagementColumnList = trackingManagementColumnList;
    }

    public String getBasicTypeMeaning() {
        return basicTypeMeaning;
    }

    public void setBasicTypeMeaning(String basicTypeMeaning) {
        this.basicTypeMeaning = basicTypeMeaning;
    }

    public String getNeedTwiceConfirmMeaning() {
        return needTwiceConfirmMeaning;
    }

    public void setNeedTwiceConfirmMeaning(String needTwiceConfirmMeaning) {
        this.needTwiceConfirmMeaning = needTwiceConfirmMeaning;
    }

    public String getParentTypeName() {
        return parentTypeName;
    }

    public void setParentTypeName(String parentTypeName) {
        this.parentTypeName = parentTypeName;
    }

    public String getCodeRuleName() {
        return codeRuleName;
    }

    public void setCodeRuleName(String codeRuleName) {
        this.codeRuleName = codeRuleName;
    }

    public String getSpecialAssetMeaning() {
        return specialAssetMeaning;
    }

    public void setSpecialAssetMeaning(String specialAssetMeaning) {
        this.specialAssetMeaning = specialAssetMeaning;
    }

    public static String getFieldTransactionTypeId() {
        return FIELD_TRANSACTION_TYPE_ID;
    }

    public String getTargetAssetStatusName() {
        return targetAssetStatusName;
    }

    public void setTargetAssetStatusName(String targetAssetStatusName) {
        this.targetAssetStatusName = targetAssetStatusName;
    }

    public String getInprocessAssetStatusName() {
        return inprocessAssetStatusName;
    }

    public void setInprocessAssetStatusName(String inprocessAssetStatusName) {
        this.inprocessAssetStatusName = inprocessAssetStatusName;
    }

    public List<TransactionTypeFields> getDeleteColumnList() {
        return deleteColumnList;
    }

    public void setDeleteColumnList(List<TransactionTypeFields> deleteColumnList) {
        this.deleteColumnList = deleteColumnList;
    }

    public Integer getOnlyEnabledFlag() {
        return onlyEnabledFlag;
    }

    public void setOnlyEnabledFlag(Integer onlyEnabledFlag) {
        this.onlyEnabledFlag = onlyEnabledFlag;
    }

    public String getDetailSelectItem() {
        return detailSelectItem;
    }

    public void setDetailSelectItem(String detailSelectItem) {
        this.detailSelectItem = detailSelectItem;
    }
}
