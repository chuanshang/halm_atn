package org.hzero.halm.afm.api.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.algorithm.tree.Child;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.halm.afm.domain.entity.TransactionTypeFields;
import org.hzero.halm.afm.domain.entity.TransactionTypes;
import org.hzero.mybatis.domian.SecurityToken;

import javax.validation.constraints.NotNull;
import java.util.List;

@ExcelSheet(zh = "项目类型", en = "project type")
public class TransactionTypesDTO extends Child<TransactionTypesDTO> implements SecurityToken, Comparable<TransactionTypesDTO> {


    private Long parentTypeId;
    @ApiModelProperty(value = "父级类型")
    @ExcelColumn(zh = "父级类型", en = "Parent Type")
    private String parentTypeName;

    //@LovValue(lovCode = "HPFM.FLAG", meaningField = "enabledFlagMeaning")
    private Integer enabledFlag;
    @ExcelColumn(zh = "启用标识", en = "enabled flag")
    private String enabledFlagMeaning;

    private Long tenantId;

    private String levelPath;
    private Integer levelNumber;


    private String _token;

    private Long objectVersionNumber;

    private Long transactionTypeId;
    @ApiModelProperty(value = "基础类型，独立值集：AAFM.ASSET_TRANSACTION_TYPE")
    @LovValue(value = "AAFM.ASSET_TRANSACTION_TYPE", meaningField = "basicTypeMeaning")
    private String basicTypeCode;
    @ApiModelProperty(value = "基础类型meaning")
    @ExcelColumn(zh = "基础类型", en = "basic type")
    private String basicTypeMeaning;
    @ApiModelProperty(value = "代码")
    @ExcelColumn(zh = "代码", en = "transaction type code")
    private String transactionTypeCode;
    @ApiModelProperty(value = "事件短名称")
    @ExcelColumn(zh = "事件短名称", en = "short name")
    private String shortName;
    @ApiModelProperty(value = "事件完整名称")
    @ExcelColumn(zh = "事件完整名称", en = "long name")
    private String longName;
    @ApiModelProperty(value = "自动编号规则ID")
    private String codeRule;
    @ApiModelProperty(value = "自动编号规则名称")
    @ExcelColumn(zh = "自动编号规则名称", en = "code rule name")
    private String codeRuleName;
    @ApiModelProperty(value = "是否需要2步确认")
    @LovValue(value = "AAFM.NEED_TWICE_CONFIRM", meaningField = "needTwiceConfirmMeaning")
    private String needTwiceConfirm;
    @ApiModelProperty(value = "是否需要2步确认meaning")
    @ExcelColumn(zh = "是否需要2步确认", en = "need twice confirm")
    private String needTwiceConfirmMeaning;
    @ApiModelProperty(value = "描述")
    @ExcelColumn(zh = "描述", en = "description")
    private String description;
    @ApiModelProperty(value = "图标")
    @ExcelColumn(zh = "图标", en = "icon")
    private String icon;
    @ApiModelProperty(value = "标记")
    @ExcelColumn(zh = "标记", en = "tag")
    private String tag;
    @ApiModelProperty(value = "组织范围，URL值集：AMDM.ORGANIZATION")
    private String organizationScope;
    @ApiModelProperty(value = "状态范围，URL值集，AAFM.ASSET_STATUS")
    private String statusScope;
    @ApiModelProperty(value = "专业范围，URL值集，AAFM.ASSET_SPECIALTY")
    private String specialtyScope;
    @ApiModelProperty(value = "仅针对特殊资产")
    @ExcelColumn(zh = "仅针对特殊资产", en = "special asset flag")
    private Integer specialAssetFlag;
    @ApiModelProperty(value = "特殊资产，值集：AMDM.SPECIAL_ASSET")
    @LovValue(value = "AAFM.SPECIAL_ASSET", meaningField = "specialAssetMeaning")
    private String specialAsset;
    @ApiModelProperty(value = "特殊资产meaning")
    @ExcelColumn(zh = "特殊资产", en = "special asset")
    private String specialAssetMeaning;
    @ApiModelProperty(value = "是否检查目标使用组织暂挂状态")
    @ExcelColumn(zh = "是否检查目标使用组织暂挂状态", en = "checkTargetOrgFlag")
    private Integer checkTargetOrgFlag;
    @ApiModelProperty(value = "是否检查当前所属组织暂挂状态")
    @ExcelColumn(zh = "是否检查当前所属组织暂挂状态", en = "checkCurrentOrgFlag")
    private Integer checkCurrentOrgFlag;
    @ApiModelProperty(value = "更改所属组织时，是否跨法人单位")
    @ExcelColumn(zh = "更改所属组织时，是否跨法人单位", en = "crossLegalFlag")
    private Integer crossLegalFlag;
    @ApiModelProperty(value = "是否需要修改资产状态，默认0")
    @ExcelColumn(zh = "是否需要修改资产状态", en = "statusUpdateFlag")
    private Integer statusUpdateFlag;
    @ApiModelProperty(value = "目标资产状态，URL值集：AAFM.ASSET_STATUS")
    private Long targetAssetStatusId;
    @ApiModelProperty(value = "目标资产状态名称")
    @ExcelColumn(zh = "目标资产状态", en = "targetAssetStatus")
    private String targetAssetStatusName;
    @ApiModelProperty(value = "过程中资产状态，URL值集：AAFM.ASSET_STATUS")
    private Long inprocessAssetStatusId;
    @ApiModelProperty(value = "过程中资产状态名称")
    @ExcelColumn(zh = "过程中资产状态名称", en = "inprocessAssetStatusName")
    private String inprocessAssetStatusName;
    @ApiModelProperty(value = "目标资产状态范围，URL值集：AAFM.ASSET_STATUS")
    private String targetAssetStatusScope;
    @ApiModelProperty(value = "涉及基本信息变更，默认0")
    private Integer basicColumnFlag;
    @ApiModelProperty(value = "涉及属性描述变更，默认0")
    private Integer attributeColumnFlag;
    @ApiModelProperty(value = "涉及跟踪与管理变更，默认0")
    private Integer trackingFlag;

    @ApiModelProperty(value = "涉及基本信息变更list")
    private List<TransactionTypeFields> basicAssetColumnList;

    @ApiModelProperty(value = "涉及属性描述变更list")
    private List<TransactionTypeFields> attributeColumnList;

    @ApiModelProperty(value = "涉及跟踪与管理变更list")
    private List<TransactionTypeFields> trackingManagementColumnList;

    @ApiModelProperty(value = "需要删除行list")
    private List<TransactionTypeFields> deleteColumnList;


    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getBasicTypeCode() {
        return basicTypeCode;
    }

    public void setBasicTypeCode(String basicTypeCode) {
        this.basicTypeCode = basicTypeCode;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getCodeRule() {
        return codeRule;
    }

    public void setCodeRule(String codeRule) {
        this.codeRule = codeRule;
    }

    public String getNeedTwiceConfirm() {
        return needTwiceConfirm;
    }

    public void setNeedTwiceConfirm(String needTwiceConfirm) {
        this.needTwiceConfirm = needTwiceConfirm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOrganizationScope() {
        return organizationScope;
    }

    public void setOrganizationScope(String organizationScope) {
        this.organizationScope = organizationScope;
    }

    public String getStatusScope() {
        return statusScope;
    }

    public void setStatusScope(String statusScope) {
        this.statusScope = statusScope;
    }

    public String getSpecialtyScope() {
        return specialtyScope;
    }

    public void setSpecialtyScope(String specialtyScope) {
        this.specialtyScope = specialtyScope;
    }

    public Integer getSpecialAssetFlag() {
        return specialAssetFlag;
    }

    public void setSpecialAssetFlag(Integer specialAssetFlag) {
        this.specialAssetFlag = specialAssetFlag;
    }

    public String getSpecialAsset() {
        return specialAsset;
    }

    public void setSpecialAsset(String specialAsset) {
        this.specialAsset = specialAsset;
    }

    public Integer getCheckTargetOrgFlag() {
        return checkTargetOrgFlag;
    }

    public void setCheckTargetOrgFlag(Integer checkTargetOrgFlag) {
        this.checkTargetOrgFlag = checkTargetOrgFlag;
    }

    public Integer getCheckCurrentOrgFlag() {
        return checkCurrentOrgFlag;
    }

    public void setCheckCurrentOrgFlag(Integer checkCurrentOrgFlag) {
        this.checkCurrentOrgFlag = checkCurrentOrgFlag;
    }

    public Integer getCrossLegalFlag() {
        return crossLegalFlag;
    }

    public void setCrossLegalFlag(Integer crossLegalFlag) {
        this.crossLegalFlag = crossLegalFlag;
    }

    public Integer getStatusUpdateFlag() {
        return statusUpdateFlag;
    }

    public void setStatusUpdateFlag(Integer statusUpdateFlag) {
        this.statusUpdateFlag = statusUpdateFlag;
    }

    public Long getTargetAssetStatusId() {
        return targetAssetStatusId;
    }

    public void setTargetAssetStatusId(Long targetAssetStatusId) {
        this.targetAssetStatusId = targetAssetStatusId;
    }

    public Long getInprocessAssetStatusId() {
        return inprocessAssetStatusId;
    }

    public void setInprocessAssetStatusId(Long inprocessAssetStatusId) {
        this.inprocessAssetStatusId = inprocessAssetStatusId;
    }

    public String getTargetAssetStatusScope() {
        return targetAssetStatusScope;
    }

    public void setTargetAssetStatusScope(String targetAssetStatusScope) {
        this.targetAssetStatusScope = targetAssetStatusScope;
    }

    public Long getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(Long parentTypeId) {
        this.parentTypeId = parentTypeId;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }

    public Integer getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(Integer levelNumber) {
        this.levelNumber = levelNumber;
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

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
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

    @Override
    public String get_token() {
        return this._token;
    }

    @Override
    public void set_token(String s) {
        this._token = s;
    }

    @Override
    public Class<? extends SecurityToken> associateEntityClass() {
        return TransactionTypes.class;
    }

    @Override
    public int compareTo(TransactionTypesDTO o) {
        return o.getTransactionTypeId().compareTo(this.transactionTypeId);
    }

    public String getSpecialAssetMeaning() {
        return specialAssetMeaning;
    }

    public void setSpecialAssetMeaning(String specialAssetMeaning) {
        this.specialAssetMeaning = specialAssetMeaning;
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

    public List<TransactionTypeFields> getDeleteColumnList() {
        return deleteColumnList;
    }

    public void setDeleteColumnList(List<TransactionTypeFields> deleteColumnList) {
        this.deleteColumnList = deleteColumnList;
    }
}

