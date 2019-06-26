package org.hzero.halm.fam.api.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.halm.fam.domain.entity.FixedAssets;
import org.hzero.mybatis.domian.SecurityToken;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author QingHuang
 * @Title FixedAssetsDTO
 * @Description
 * @date: 2019-4-12 16:34
 */
@ExcelSheet(zh = "固定资产列表",en = "Fixed Assets List")
public class FixedAssetsDTO implements SecurityToken {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    private Long fixedAssetId;
    @ApiModelProperty(value = "固定资产名称")
    @ExcelColumn(zh = "固定资产名称", en = "Fixed Asset Name")
    private String fixedAssetName;
    @ApiModelProperty(value = "财务固定资产编号")
    @ExcelColumn(zh = "财务固定资产编号", en = "Financial Number")
    private String financialNum;
    @ApiModelProperty(value = "转固日期")
    @ExcelColumn(zh = "转固日期", en = "Transfer Date")
    private Date transferDate;
    @ApiModelProperty(value = "资产账簿，URL值集（开发人员补充值集名称）")
    private String assetBookCode;
    @ApiModelProperty(value = "资产账簿Meaning")
    @ExcelColumn(zh = "资产账簿", en = "Asset Book")
    private String assetBookMeaning;
    @ApiModelProperty(value = "资产目录，URL值集（开发人员补充值集名称）")
    private Long assetCatalogId;
    @ApiModelProperty(value = "资产目录名称")
    @ExcelColumn(zh = "资产目录", en = "Asset Catalog")
    private String assetCatalogName;
    @ApiModelProperty(value = "描述")
    @ExcelColumn(zh = "描述", en = "description")
    private String description;
    @ApiModelProperty(value = "折旧起始日期")
    @ExcelColumn(zh = "折旧起始日期", en = "Depreciation Start Date")
    private Date depreciationStartDate;
    @ApiModelProperty(value = "折旧月份")
    @ExcelColumn(zh = "折旧月份", en = "Depreciation Mouth")
    private int depreciationMouth;
    @ApiModelProperty(value = "折旧类型，独立值集: AFAM.DEPRECIATION_TYPE")
    @LovValue(value = "AFAM.DEPRECIATION_TYPE", meaningField = "depreciationTypeMeaning")
    private String depreciationTypeCode;
    @ApiModelProperty(value = "折旧类型Meaning")
    @ExcelColumn(zh = "折旧类型", en = "Depreciation Type")
    private String depreciationTypeMeaning;
    @ApiModelProperty(value = "残值率")
    @ExcelColumn(zh = "残值率(%)", en = "Residual Value Rate(%)")
    private BigDecimal residualValueRate;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty("初始原值，计算逻辑：价值变动中，类型为“增加”的金额总和")
    @ExcelColumn(zh = "初始原值", en = "Initial Original Value")
    private String initOriginalValue;
    @ApiModelProperty("当前原值，计算逻辑：价值变动中，类型为“增加”&“追加”的金额总和")
    @ExcelColumn(zh = "当前原值", en = "Current Original Value")
    private String currentOriginalValue;
    @ApiModelProperty("累计折旧，计算逻辑：价值变动中，类型为“折旧”和“计划外折旧”的金额总和")
    @ExcelColumn(zh = "累计折旧", en = "Accumulated depreciation")
    private String accumulatedDepreciation;
    @ApiModelProperty("YTD折旧")
    @ExcelColumn(zh = "YTD折旧", en = "YTD depreciation")
    private String YTDDepreciation;
    @ApiModelProperty("净值，计算逻辑：当前原值 - 累计折旧")
    @ExcelColumn(zh = "净值", en = "New Value")
    private String netValue;
    @ApiModelProperty("残值，计算逻辑：当前原值 *残值率（%）")
    @ExcelColumn(zh = "残值", en = "Residual Value")
    private String residualValue;

    @ApiModelProperty(value = "明细页列表查询字段")
    private String detailSelectItem;

    @ExcelColumn(zh = "价值变动信息", en = "attribute lines Info", child = true)
    private List<FixedAssetsChangesDTO> fixedAssetsChangesDTOList;

    private Long objectVersionNumber;

    public Long getFixedAssetId() {
        return fixedAssetId;
    }

    public void setFixedAssetId(Long fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    public String getFixedAssetName() {
        return fixedAssetName;
    }

    public void setFixedAssetName(String fixedAssetName) {
        this.fixedAssetName = fixedAssetName;
    }

    public String getFinancialNum() {
        return financialNum;
    }

    public void setFinancialNum(String financialNum) {
        this.financialNum = financialNum;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getAssetBookCode() {
        return assetBookCode;
    }

    public void setAssetBookCode(String assetBookCode) {
        this.assetBookCode = assetBookCode;
    }

    public String getAssetBookMeaning() {
        return assetBookMeaning;
    }

    public void setAssetBookMeaning(String assetBookMeaning) {
        this.assetBookMeaning = assetBookMeaning;
    }

    public Long getAssetCatalogId() {
        return assetCatalogId;
    }

    public void setAssetCatalogId(Long assetCatalogId) {
        this.assetCatalogId = assetCatalogId;
    }

    public String getAssetCatalogName() {
        return assetCatalogName;
    }

    public void setAssetCatalogName(String assetCatalogName) {
        this.assetCatalogName = assetCatalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDepreciationStartDate() {
        return depreciationStartDate;
    }

    public void setDepreciationStartDate(Date depreciationStartDate) {
        this.depreciationStartDate = depreciationStartDate;
    }

    public int getDepreciationMouth() {
        return depreciationMouth;
    }

    public void setDepreciationMouth(int depreciationMouth) {
        this.depreciationMouth = depreciationMouth;
    }

    public String getDepreciationTypeCode() {
        return depreciationTypeCode;
    }

    public void setDepreciationTypeCode(String depreciationTypeCode) {
        this.depreciationTypeCode = depreciationTypeCode;
    }

    public String getDepreciationTypeMeaning() {
        return depreciationTypeMeaning;
    }

    public void setDepreciationTypeMeaning(String depreciationTypeMeaning) {
        this.depreciationTypeMeaning = depreciationTypeMeaning;
    }

    public String getDetailSelectItem() {
        return detailSelectItem;
    }

    public void setDetailSelectItem(String detailSelectItem) {
        this.detailSelectItem = detailSelectItem;
    }

    public BigDecimal getResidualValueRate() {
        return residualValueRate;
    }

    public void setResidualValueRate(BigDecimal residualValueRate) {
        this.residualValueRate = residualValueRate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getInitOriginalValue() {
        return initOriginalValue;
    }

    public void setInitOriginalValue(String initOriginalValue) {
        this.initOriginalValue = initOriginalValue;
    }

    public String getCurrentOriginalValue() {
        return currentOriginalValue;
    }

    public void setCurrentOriginalValue(String currentOriginalValue) {
        this.currentOriginalValue = currentOriginalValue;
    }

    public String getAccumulatedDepreciation() {
        return accumulatedDepreciation;
    }

    public void setAccumulatedDepreciation(String accumulatedDepreciation) {
        this.accumulatedDepreciation = accumulatedDepreciation;
    }

    public String getYTDDepreciation() {
        return YTDDepreciation;
    }

    public void setYTDDepreciation(String YTDDepreciation) {
        this.YTDDepreciation = YTDDepreciation;
    }

    public String getNetValue() {
        return netValue;
    }

    public void setNetValue(String netValue) {
        this.netValue = netValue;
    }

    public String getResidualValue() {
        return residualValue;
    }

    public void setResidualValue(String residualValue) {
        this.residualValue = residualValue;
    }

    public List<FixedAssetsChangesDTO> getFixedAssetsChangesDTOList() {
        return fixedAssetsChangesDTOList;
    }

    public void setFixedAssetsChangesDTOList(List<FixedAssetsChangesDTO> fixedAssetsChangesDTOList) {
        this.fixedAssetsChangesDTOList = fixedAssetsChangesDTOList;
    }

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    private String _token;

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
        return FixedAssets.class;
    }
}