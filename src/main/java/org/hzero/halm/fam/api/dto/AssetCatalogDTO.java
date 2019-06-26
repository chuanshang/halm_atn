package org.hzero.halm.fam.api.dto;

import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.algorithm.tree.Child;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.halm.fam.domain.entity.AssetCatalog;
import org.hzero.mybatis.domian.SecurityToken;
import java.math.BigDecimal;

import javax.persistence.Transient;

/**
 * 资产目录Dto
 *
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
@ExcelSheet(zh = "资产目录",en = "Asset Catalog")
public class AssetCatalogDTO extends Child<AssetCatalogDTO> implements SecurityToken, Comparable<AssetCatalogDTO>{


    private Long assetCatalogId;
    @ExcelColumn(zh = "名称", en = "name")
    private String catalogName;

    private Long productCategoryId;
    @ExcelColumn(zh = "资产目录代码", en = "Catalog Code")
    private String catalogCode;
    @ExcelColumn(zh = "残值率", en = "Residual Value Rate")
    private BigDecimal residualValueRate;
    @ExcelColumn(zh = "折旧月份", en = "Depreciation Month")
    private Long depreciationMonth;
    @LovValue(value = "AFAM.DEPRECIATION_TYPE", meaningField = "depreciationTypeCodeMeaning")
    private String depreciationTypeCode;
    @LovValue(value = "AFAM.ACCOUNT_TYPE", meaningField = "accountTypeCodeMeaning")
    private String accountTypeCode;

    private Long parentCatalogId;

    private String levelPath;

    private Long levelNumber;
    @LovValue(value = "HPFM.FLAG", meaningField = "enabledFlagMeaning")
    private Integer enabledFlag;
    @ExcelColumn(zh = "描述", en = "Description")
    private String description;

    private Long tenantId;

    private Long objectVersionNumber;

    private String _token;

    @Transient
    @ExcelColumn(zh = "启用", en = "Enabled")
    private String enabledFlagMeaning;
    @Transient
    @ExcelColumn(zh = "折旧类型", en = "Depreciation Type")
    private String depreciationTypeCodeMeaning;
    @Transient
    @ExcelColumn(zh = "入账会计科目类型", en = "Account Type")
    private String accountTypeCodeMeaning;
    @Transient
    @ExcelColumn(zh = "产品类别", en = "Product Category")
    private String productCategoryMeaning;

    public Long getAssetCatalogId() {
        return assetCatalogId;
    }

    public void setAssetCatalogId(Long assetCatalogId) {
        this.assetCatalogId = assetCatalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public BigDecimal getResidualValueRate() {
        return residualValueRate;
    }

    public void setResidualValueRate(BigDecimal residualValueRate) {
        this.residualValueRate = residualValueRate;
    }

    public Long getDepreciationMonth() {
        return depreciationMonth;
    }

    public void setDepreciationMonth(Long depreciationMonth) {
        this.depreciationMonth = depreciationMonth;
    }

    public String getDepreciationTypeCode() {
        return depreciationTypeCode;
    }

    public void setDepreciationTypeCode(String depreciationTypeCode) {
        this.depreciationTypeCode = depreciationTypeCode;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public Long getParentCatalogId() {
        return parentCatalogId;
    }

    public void setParentCatalogId(Long parentCatalogId) {
        this.parentCatalogId = parentCatalogId;
    }

    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }

    public Long getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(Long levelNumber) {
        this.levelNumber = levelNumber;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public String getEnabledFlagMeaning() {
        return enabledFlagMeaning;
    }

    public void setEnabledFlagMeaning(String enabledFlagMeaning) {
        this.enabledFlagMeaning = enabledFlagMeaning;
    }

    public String getDepreciationTypeCodeMeaning() {
        return depreciationTypeCodeMeaning;
    }

    public void setDepreciationTypeCodeMeaning(String depreciationTypeCodeMeaning) {
        this.depreciationTypeCodeMeaning = depreciationTypeCodeMeaning;
    }

    public String getAccountTypeCodeMeaning() {
        return accountTypeCodeMeaning;
    }

    public void setAccountTypeCodeMeaning(String accountTypeCodeMeaning) {
        this.accountTypeCodeMeaning = accountTypeCodeMeaning;
    }

    public String getProductCategoryMeaning() {
        return productCategoryMeaning;
    }

    public void setProductCategoryMeaning(String productCategoryMeaning) {
        this.productCategoryMeaning = productCategoryMeaning;
    }

    @Override
    public String get_token() {
        return _token;
    }

    @Override
    public void set_token(String _token) {
        this._token = _token;
    }

    @Override
    public int compareTo(AssetCatalogDTO o) {
        return 0;
    }

    @Override
    public Class<? extends SecurityToken> associateEntityClass() {
        return AssetCatalog.class;
    }

    @Override
    public String toString() {
        return "AssetCatalogDTO{" +
                "assetCatalogId=" + assetCatalogId +
                ", catalogName='" + catalogName + '\'' +
                ", productCategoryId=" + productCategoryId +
                ", catalogCode='" + catalogCode + '\'' +
                ", residualValueRate=" + residualValueRate +
                ", depreciationMonth=" + depreciationMonth +
                ", depreciationTypeCode='" + depreciationTypeCode + '\'' +
                ", accountTypeCode='" + accountTypeCode + '\'' +
                ", parentCatalogId=" + parentCatalogId +
                ", levelPath='" + levelPath + '\'' +
                ", levelNumber=" + levelNumber +
                ", enabledFlag=" + enabledFlag +
                ", description='" + description + '\'' +
                ", tenantId=" + tenantId +
                ", _token='" + _token + '\'' +
                '}';
    }
}
