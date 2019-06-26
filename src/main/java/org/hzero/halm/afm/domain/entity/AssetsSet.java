/*
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */

package org.hzero.halm.afm.domain.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hzero.boot.platform.lov.annotation.LovValue;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 资产组
 *
 * @author sen.wang@hand-china.com 2019-01-11 13:58:49
 */
@ApiModel("资产组")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_assets_set")
public class AssetsSet extends AuditDomain {

    public static final String FIELD_ASSETS_SET_ID = "assetsSetId";
    public static final String FIELD_ASSETS_SET_NUM = "assetsSetNum";
    public static final String FIELD_ASSETS_SET_NAME = "assetsSetName";
    public static final String FIELD_ASSET_NAME = "assetName";
    public static final String FIELD_SPECIAL_ASSET_CODE = "specialAssetCode";
    public static final String FIELD_BRAND = "brand";
    public static final String FIELD_SPECIFICATIONS = "specifications";
    public static final String FIELD_ASSET_CLASS_ID = "assetClassId";
    public static final String FIELD_URL = "url";
    public static final String FIELD_ASSET_CRITICALITY = "assetCriticality";
    public static final String FIELD_ATTRIBUTE_SET_ID = "attributeSetId";
    public static final String FIELD_SPECIAL_ASSET_CLASS_ID = "specialAssetClassId";
    public static final String FIELD_FIXED_ASSET_TYPE_CODE = "fixedAssetTypeCode";
    public static final String FIELD_UOM_ID = "uomId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_CODE_RULE = "codeRule";
    public static final String FIELD_TRADE_IN_FLAG = "tradeInFlag";
    public static final String FIELD_ONLY_MAINT_SITES_FLAG = "onlyMaintSitesFlag";
    public static final String FIELD_MAINTAIN_FLAG = "maintainFlag";
    public static final String FIELD_PRODUCT_URL = "productUrl";
    public static final String FIELD_NAMEPLATE_RULE_CODE = "nameplateRuleCode";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------


    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long assetsSetId;

    @ApiModelProperty(value = "编号，必输，租户内唯一")
    @NotBlank
    @Length(max = 30)
    private String assetsSetNum;

    @ApiModelProperty(value = "名称，必输，租户内唯一")
    @NotBlank
    @Length(max = 60)
    private String assetsSetName;

    @ApiModelProperty(value = "资产名称")
    @NotBlank
    @Length(max = 60)
    private String assetName;

    @ApiModelProperty(value = "所属特殊资产，值集：AAFM.SPECIAL_ASSET")
    @Length(max = 20)
    @LovValue(value = "AAFM.SPECIAL_ASSET",meaningField = "specialAssetMeaning")
    private String specialAssetCode;

    @ApiModelProperty(value = "品牌/厂商")
    @Length(max = 150)
    private String brand;

    @ApiModelProperty(value = "规格/型号")
    @Length(max = 480)
    private String specifications;

    @ApiModelProperty(value = "产品类别/资产分类，值集：AAFM.ASSET_CLASS 必输")
    @NotNull
    private Long assetClassId;

    @ApiModelProperty(value = "URL")
    @Length(max = 480)
    private String url;

    @ApiModelProperty(value = "资产重要性，值集：AAFM.ASSET_CRITICALITY ")
    @Length(max = 30)
    @LovValue(value = "AAFM.ASSET_CRITICALITY",meaningField = "assetCriticalityMeaning")
    private String assetCriticality;

    @ApiModelProperty(value = "属性组，值集： AAFM.ATTRIBUTE_SET")
    private Long attributeSetId;

    @ApiModelProperty(value = "专业资产分类，值集：AAFM.SPECIAL_ASSET_CLASS")
    private Long specialAssetClassId;

    @ApiModelProperty(value = "固定资产类别，值集：AAFM.FIXED_ASSET_TYPE")
    @Length(max = 30)
    @LovValue(value = "AAFM.FIXED_ASSET_TYPE",meaningField = "fixedAssetTypeMeaning")
    private String fixedAssetTypeCode;

    @ApiModelProperty(value = "单位Id，值集：AAFM.UOM")
    private Long uomId;

    @ApiModelProperty(value = "说明")
    @Length(max = 120)
    private String description;

    @ApiModelProperty(value = "图标")
    @Length(max = 20)
    private String icon;

    @ApiModelProperty(value = "编号规则，值集：AAFM.CODE_RULE 必输")
    private String codeRule;

    @ApiModelProperty(value = "是否可在合同中交易，默认：1")
    @Range(max = 1)
    @LovValue(value = "HPFM.FLAG",meaningField = "tradeInFlagMeaning")
    private Integer tradeInFlag;

    @ApiModelProperty(value = "限用于分配的服务区域，默认：1")
    @Range(max = 1)
    @LovValue(value = "HPFM.FLAG",meaningField = "onlyMaintSitesFlagMeaning")
    private Integer onlyMaintSitesFlag;

    @ApiModelProperty(value = "是否可维修，默认：1")
    @NotNull
    @Range(max = 1)
    @LovValue(value = "HPFM.FLAG",meaningField = "maintainFlagMeaning")
    private Integer maintainFlag;

    @ApiModelProperty(value = "产品图片")
    @Length(max = 480)
    private String productUrl;

    @ApiModelProperty(value = "资产标签/铭牌规则，值集：AAFM.NAMEPLATE_RULE 必输")
    @NotBlank
    @Length(max = 30)
    @LovValue(value = "AAFM.NAMEPLATE_RULE",meaningField = "nameplateRuleMeaning")
    private String nameplateRuleCode;

    @ApiModelProperty(value = "启用标记，默认：1（启用）")
    @NotNull
    @LovValue(value = "HPFM.FLAG",meaningField = "enabledFlagMeaning")
    private Integer enabledFlag;

    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

    @ApiModelProperty(value = "产品名称")
    @Length(max = 120)
    private String productName;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @Transient
    private String assetClassMeaning;
    // 所属特殊资产 值集
    @Transient
    private String specialAssetMeaning;
    //资产重要性 值级
    @Transient
    private String assetCriticalityMeaning;
    //属性组 值级
    @Transient
    private String attributeSetMeaning;
    //专业资产分类 值级
    @Transient
    private String specialAssetClassMeaning;
    //固定资产分类 值级
    @Transient
    private String fixedAssetTypeMeaning;
    //单位 值级
    @Transient
    private String uomMeaning;
    //编号规则 值级
    @Transient
    private String codeRuleMeaning;
    //资产标签 值级
    @Transient
    private String nameplateRuleMeaning;
    @Transient
    private String tradeInFlagMeaning;
    @Transient
    private String maintainFlagMeaning;
    @Transient
    private String enabledFlagMeaning;
    @Transient
    private String onlyMaintSitesFlagMeaning;

    @ApiModelProperty(value = "明细界面的筛选条件")
    @Transient
    private String detailCondition;

    @Transient
    private Set<Long> usedIds;






    //
    // getter/setter
    // ------------------------------------------------------------------------------


    public Set<Long> getUsedIds() {
        return usedIds;
    }

    public void setUsedIds(Set<Long> usedIds) {
        this.usedIds = usedIds;
    }

    public Long getAssetsSetId() {
        return assetsSetId;
    }

    public void setAssetsSetId(Long assetsSetId) {
        this.assetsSetId = assetsSetId;
    }

    public String getAssetsSetNum() {
        return assetsSetNum;
    }

    public void setAssetsSetNum(String assetsSetNum) {
        this.assetsSetNum = assetsSetNum;
    }

    public String getAssetsSetName() {
        return assetsSetName;
    }

    public void setAssetsSetName(String assetsSetName) {
        this.assetsSetName = assetsSetName;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getSpecialAssetCode() {
        return specialAssetCode;
    }

    public void setSpecialAssetCode(String specialAssetCode) {
        this.specialAssetCode = specialAssetCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAssetCriticality() {
        return assetCriticality;
    }

    public void setAssetCriticality(String assetCriticality) {
        this.assetCriticality = assetCriticality;
    }

    public String getAssetCriticalityMeaning() {
        return assetCriticalityMeaning;
    }

    public void setAssetCriticalityMeaning(String assetCriticalityMeaning) {
        this.assetCriticalityMeaning = assetCriticalityMeaning;
    }

    public Long getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(Long attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    public Long getAssetClassId() {
        return assetClassId;
    }

    public void setAssetClassId(Long assetClassId) {
        this.assetClassId = assetClassId;
    }

    public Long getSpecialAssetClassId() {
        return specialAssetClassId;
    }

    public void setSpecialAssetClassId(Long specialAssetClassId) {
        this.specialAssetClassId = specialAssetClassId;
    }

    public String getAssetClassMeaning() {
        return assetClassMeaning;
    }

    public void setAssetClassMeaning(String assetClassMeaning) {
        this.assetClassMeaning = assetClassMeaning;
    }

    public String getFixedAssetTypeCode() {
        return fixedAssetTypeCode;
    }

    public void setFixedAssetTypeCode(String fixedAssetTypeCode) {
        this.fixedAssetTypeCode = fixedAssetTypeCode;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
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

    public String getCodeRule() {
        return codeRule;
    }

    public void setCodeRule(String codeRule) {
        this.codeRule = codeRule;
    }

    public Integer getTradeInFlag() {
        return tradeInFlag;
    }

    public void setTradeInFlag(Integer tradeInFlag) {
        this.tradeInFlag = tradeInFlag;
    }

    public Integer getOnlyMaintSitesFlag() {
        return onlyMaintSitesFlag;
    }

    public void setOnlyMaintSitesFlag(Integer onlyMaintSitesFlag) {
        this.onlyMaintSitesFlag = onlyMaintSitesFlag;
    }

    public Integer getMaintainFlag() {
        return maintainFlag;
    }

    public void setMaintainFlag(Integer maintainFlag) {
        this.maintainFlag = maintainFlag;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getNameplateRuleCode() {
        return nameplateRuleCode;
    }

    public void setNameplateRuleCode(String nameplateRuleCode) {
        this.nameplateRuleCode = nameplateRuleCode;
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

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecialAssetMeaning() {
        return specialAssetMeaning;
    }

    public void setSpecialAssetMeaning(String specialAssetMeaning) {
        this.specialAssetMeaning = specialAssetMeaning;
    }


    public String getAttributeSetMeaning() {
        return attributeSetMeaning;
    }

    public void setAttributeSetMeaning(String attributeSetMeaning) {
        this.attributeSetMeaning = attributeSetMeaning;
    }

    public String getSpecialAssetClassMeaning() {
        return specialAssetClassMeaning;
    }

    public void setSpecialAssetClassMeaning(String specialAssetClassMeaning) {
        this.specialAssetClassMeaning = specialAssetClassMeaning;
    }

    public String getFixedAssetTypeMeaning() {
        return fixedAssetTypeMeaning;
    }

    public void setFixedAssetTypeMeaning(String fixedAssetTypeMeaning) {
        this.fixedAssetTypeMeaning = fixedAssetTypeMeaning;
    }

    public String getUomMeaning() {
        return uomMeaning;
    }

    public void setUomMeaning(String uomMeaning) {
        this.uomMeaning = uomMeaning;
    }

    public String getCodeRuleMeaning() {
        return codeRuleMeaning;
    }

    public void setCodeRuleMeaning(String codeRuleMeaning) {
        this.codeRuleMeaning = codeRuleMeaning;
    }

    public String getNameplateRuleMeaning() {
        return nameplateRuleMeaning;
    }

    public void setNameplateRuleMeaning(String nameplateRuleMeaning) {
        this.nameplateRuleMeaning = nameplateRuleMeaning;
    }

    public String getTradeInFlagMeaning() {
        return tradeInFlagMeaning;
    }

    public void setTradeInFlagMeaning(String tradeInFlagMeaning) {
        this.tradeInFlagMeaning = tradeInFlagMeaning;
    }

    public String getMaintainFlagMeaning() {
        return maintainFlagMeaning;
    }

    public void setMaintainFlagMeaning(String maintainFlagMeaning) {
        this.maintainFlagMeaning = maintainFlagMeaning;
    }

    public String getEnabledFlagMeaning() {
        return enabledFlagMeaning;
    }

    public void setEnabledFlagMeaning(String enabledFlagMeaning) {
        this.enabledFlagMeaning = enabledFlagMeaning;
    }

    public String getOnlyMaintSitesFlagMeaning() {
        return onlyMaintSitesFlagMeaning;
    }

    public void setOnlyMaintSitesFlagMeaning(String onlyMaintSitesFlagMeaning) {
        this.onlyMaintSitesFlagMeaning = onlyMaintSitesFlagMeaning;
    }

    public String getDetailCondition() {
        return detailCondition;
    }

    public void setDetailCondition(String detailCondition) {
        this.detailCondition = detailCondition;
    }
}
