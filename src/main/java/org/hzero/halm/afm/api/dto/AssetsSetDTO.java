/*
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */

package org.hzero.halm.afm.api.dto;

import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.halm.afm.domain.entity.AssetsSet;
import org.hzero.mybatis.domian.SecurityToken;

/**
 * @author WangSen
 * @Title AssetsSetDTO
 * @Description
 * @date: 2019-1-14 16:34
 */
@ExcelSheet(zh = "资产组列表",en = "Asset Set List")
public class AssetsSetDTO implements SecurityToken {

    private Long assetsSetId;

    @ExcelColumn(zh = "资产组编号", en = "Asset Set Num")
    private String assetsSetNum;

    @ExcelColumn(zh = "资产组名字", en = "Asset Set Name")
    private String assetsSetName;

    private String specialAssetCode;
    @ExcelColumn(zh = "所属特殊资产", en = "Special Asset")
    private String specialAssetMeaning;

    @ExcelColumn(zh = "品牌", en = "Brand")
    private String brand;

    @ExcelColumn(zh = "规格/型号", en = "Specifications")
    private String specifications;

    @ExcelColumn(zh = "产品类别/资产分类", en = "Asset Class")
    private String assetClassMeaning;
    private Long assetClassId;

    @ExcelColumn(zh = "URL", en = "URL")
    private String url;

    private String assetCriticality;
    @ExcelColumn(zh = "资产重要性", en = "Asset CriticalityC")
    private String assetCriticalityMeaning;

    private Long attributeSetId;
    @ExcelColumn(zh = "属性组", en = "Attribute Set")
    private String attributeSetMeaning;

    private Long specialAssetClassId;
    @ExcelColumn(zh = "专业资产分类", en = "Special Asset Class")
    private String specialAssetClassMeaning;

    private String fixedAssetTypeCode;
    @ExcelColumn(zh = "固定资产类别", en = "FixedAsset Type")
    private String fixedAssetTypeMeaning;

    private Long uomId;
    @ExcelColumn(zh = "单位", en = "Uom")
    private String uomMeaning;

    @ExcelColumn(zh = "说明", en = "description")
    private String description;

    @ExcelColumn(zh = "图标", en = "iocn")
    private String iocn;

    @ExcelColumn(zh = "编号规则", en = "Code Rule")
    private String codeRuleMeaning;
    private String codeRule;

    @ExcelColumn(zh = "是否可在合同中交易", en = "TradeInC Flag")
    private String tradeInFlagMeaning;
    private Integer tradeInFlag;

    @ExcelColumn(zh = "限用于分配的服务区域", en = "Only MaintSitesC Flag")
    private String onlyMaintSitesFlagMeaning;
    private Integer onlyMaintSitesFlag;

    @ExcelColumn(zh = "是否可维修", en = "isMaintainMeaning")
    private String maintainFlagMeaning;
    private Integer maintainFlag;

    @ExcelColumn(zh = "产品图片", en = "Product Url")
    private String productUrl;

    @ExcelColumn(zh = "资产标签/铭牌规则", en = "Nameplate Rule")
    private String nameplateRuleMeaning;
    private String nameplateRuleCode;

    @ExcelColumn(zh = "启用标记", en = "Enabled Flag")
    private String enabledFlagMeaning;
    private Integer enabledFlag;

    private Long tenantId;

    private String _token;

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

    public String getSpecialAssetCode() {
        return specialAssetCode;
    }

    public void setSpecialAssetCode(String specialAssetCode) {
        this.specialAssetCode = specialAssetCode;
    }

    public String getSpecialAssetMeaning() {
        return specialAssetMeaning;
    }

    public void setSpecialAssetMeaning(String specialAssetMeaning) {
        this.specialAssetMeaning = specialAssetMeaning;
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

    public String getAttributeSetMeaning() {
        return attributeSetMeaning;
    }

    public void setAttributeSetMeaning(String attributeSetMeaning) {
        this.attributeSetMeaning = attributeSetMeaning;
    }

    public String getAssetClassMeaning() {
        return assetClassMeaning;
    }

    public void setAssetClassMeaning(String assetClassMeaning) {
        this.assetClassMeaning = assetClassMeaning;
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

    public String getSpecialAssetClassMeaning() {
        return specialAssetClassMeaning;
    }

    public void setSpecialAssetClassMeaning(String specialAssetClassMeaning) {
        this.specialAssetClassMeaning = specialAssetClassMeaning;
    }

    public String getFixedAssetTypeCode() {
        return fixedAssetTypeCode;
    }

    public void setFixedAssetTypeCode(String fixedAssetTypeCode) {
        this.fixedAssetTypeCode = fixedAssetTypeCode;
    }

    public String getFixedAssetTypeMeaning() {
        return fixedAssetTypeMeaning;
    }

    public void setFixedAssetTypeMeaning(String fixedAssetTypeMeaning) {
        this.fixedAssetTypeMeaning = fixedAssetTypeMeaning;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public String getUomMeaning() {
        return uomMeaning;
    }

    public void setUomMeaning(String uomMeaning) {
        this.uomMeaning = uomMeaning;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIocn() {
        return iocn;
    }

    public void setIocn(String iocn) {
        this.iocn = iocn;
    }

    public String getCodeRuleMeaning() {
        return codeRuleMeaning;
    }

    public void setCodeRuleMeaning(String codeRuleMeaning) {
        this.codeRuleMeaning = codeRuleMeaning;
    }

    public String getCodeRule() {
        return codeRule;
    }

    public void setCodeRule(String codeRule) {
        this.codeRule = codeRule;
    }

    public String getTradeInFlagMeaning() {
        return tradeInFlagMeaning;
    }

    public void setTradeInFlagMeaning(String tradeInFlagMeaning) {
        this.tradeInFlagMeaning = tradeInFlagMeaning;
    }

    public Integer getTradeInFlag() {
        return tradeInFlag;
    }

    public void setTradeInFlag(Integer tradeInFlag) {
        this.tradeInFlag = tradeInFlag;
    }

    public String getOnlyMaintSitesFlagMeaning() {
        return onlyMaintSitesFlagMeaning;
    }

    public void setOnlyMaintSitesFlagMeaning(String onlyMaintSitesFlagMeaning) {
        this.onlyMaintSitesFlagMeaning = onlyMaintSitesFlagMeaning;
    }

    public Integer getOnlyMaintSitesFlag() {
        return onlyMaintSitesFlag;
    }

    public void setOnlyMaintSitesFlag(Integer onlyMaintSitesFlag) {
        this.onlyMaintSitesFlag = onlyMaintSitesFlag;
    }

    public String getMaintainFlagMeaning() {
        return maintainFlagMeaning;
    }

    public void setMaintainFlagMeaning(String maintainFlagMeaning) {
        this.maintainFlagMeaning = maintainFlagMeaning;
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

    public String getNameplateRuleMeaning() {
        return nameplateRuleMeaning;
    }

    public void setNameplateRuleMeaning(String nameplateRuleMeaning) {
        this.nameplateRuleMeaning = nameplateRuleMeaning;
    }

    public String getNameplateRuleCode() {
        return nameplateRuleCode;
    }

    public void setNameplateRuleCode(String nameplateRuleCode) {
        this.nameplateRuleCode = nameplateRuleCode;
    }

    public String getEnabledFlagMeaning() {
        return enabledFlagMeaning;
    }

    public void setEnabledFlagMeaning(String enabledFlagMeaning) {
        this.enabledFlagMeaning = enabledFlagMeaning;
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

    @Override
    public void set_token(String _token) {
        this._token = _token;
    }
    @Override
    public String get_token() {
        return this._token;
    }
    @Override
    public Class<? extends SecurityToken> associateEntityClass() {
        return AssetsSet.class;
    }
}
