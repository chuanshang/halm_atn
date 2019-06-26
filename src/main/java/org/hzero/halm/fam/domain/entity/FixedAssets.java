package org.hzero.halm.fam.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.choerodon.core.exception.CommonException;
import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.halm.fam.domain.repository.FixedAssetsRepository;
import org.hzero.halm.fam.infra.constant.Constants;

/**
 * 固定资产
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:22:24
 */
@ApiModel("固定资产")
@VersionAudit
@ModifyAudit
@Table(name = "afam_fixed_assets")
public class FixedAssets extends AuditDomain {

    public static final String FIELD_FIXED_ASSET_ID = "fixedAssetId";
    public static final String FIELD_FIXED_ASSET_NAME = "fixedAssetName";
    public static final String FIELD_FINANCIAL_NUM = "financialNum";
    public static final String FIELD_TRANSFER_DATE = "transferDate";
    public static final String FIELD_ASSET_BOOK_CODE = "assetBookCode";
    public static final String FIELD_ASSET_CATALOG_ID = "assetCatalogId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DEPRECIATION_START_DATE = "depreciationStartDate";
    public static final String FIELD_DEPRECIATION_MOUTH = "depreciationMouth";
    public static final String FIELD_DEPRECIATION_TYPE_CODE = "depreciationTypeCode";
    public static final String FIELD_RESIDUAL_VALUE_RATE = "residualValueRate";
    public static final String FIELD_TENANT_ID = "tenantId";

    public static final String SAVE_TYPE_INSERT = "insert";
    public static final String SAVE_TYPE_UPDATE = "update";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    /**
     * 校验
     *
     * @param tenantId              租户ID
     * @param fixedAssetsRepository fixedAssetsRepository
     */
    public void validateFixedAssets(Long tenantId, FixedAssetsRepository fixedAssetsRepository) {
        //校验名称是否重复
        validUniqueIndex(tenantId, fixedAssetsRepository);

    }

    /**
     * 唯一索引校验
     *
     * @param tenantId 租户ID
     */
    public void validUniqueIndex(Long tenantId, FixedAssetsRepository fixedAssetsRepository) {
        if (fixedAssetsRepository.selectCountByCondition(
                org.hzero.mybatis.domian.Condition.builder(FixedAssets.class)
                        .andWhere(
                                org.hzero.mybatis.util.Sqls.custom()
                                        .andEqualTo(FixedAssets.FIELD_TENANT_ID, tenantId)
                                        .andEqualTo(FixedAssets.FIELD_FIXED_ASSET_NAME, this.fixedAssetName)
                                        .andNotEqualTo(FixedAssets.FIELD_FIXED_ASSET_ID, this.fixedAssetId, true)
                        ).build()) != 0) {
            throw new CommonException(Constants.AfamErrorCode.FIXED_ASSETS_NAME_DUPLICATED, this.fixedAssetName);
        }
    }
    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long fixedAssetId;
    @ApiModelProperty(value = "固定资产名称")
    @NotBlank
    private String fixedAssetName;
    @ApiModelProperty(value = "财务固定资产编号")
    private String financialNum;
    @ApiModelProperty(value = "转固日期")
    private Date transferDate;
    @ApiModelProperty(value = "资产账簿，URL值集（开发人员补充值集名称）")
    @NotBlank
    private String assetBookCode;
    @ApiModelProperty(value = "资产账簿Meaning")
    @Transient
    private String assetBookMeaning;
    @ApiModelProperty(value = "资产目录，URL值集（开发人员补充值集名称）")
    @NotNull
    private Long assetCatalogId;
    @ApiModelProperty(value = "资产目录名称")
    @Transient
    private String assetCatalogName;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "折旧起始日期")
    private Date depreciationStartDate;
    @ApiModelProperty(value = "折旧月份")
    private int depreciationMouth;
    @ApiModelProperty(value = "折旧类型，独立值集: AFAM.DEPRECIATION_TYPE")
    @LovValue(value = "AFAM.DEPRECIATION_TYPE", meaningField = "depreciationTypeMeaning")
    private String depreciationTypeCode;
    @ApiModelProperty(value = "折旧类型Meaning")
    @Transient
    private String depreciationTypeMeaning;
    @ApiModelProperty(value = "残值率")
    private BigDecimal residualValueRate;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------


    @Transient
    @ApiModelProperty(value = "明细页列表查询字段")
    private String detailSelectItem;

    @ApiModelProperty(value = "转固日期从")
    @Transient
    private Date transferDateFrom;

    @ApiModelProperty(value = "转固日期至")
    @Transient
    private Date transferDateTo;

    @ApiModelProperty(value = "行list")
    @Transient
    private List<FixedAssetsChanges> fixedAssetsChangesList;

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getFixedAssetId() {
        return fixedAssetId;
    }

    public void setFixedAssetId(Long fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    /**
     * @return 固定资产名称
     */
    public String getFixedAssetName() {
        return fixedAssetName;
    }

    public void setFixedAssetName(String fixedAssetName) {
        this.fixedAssetName = fixedAssetName;
    }

    /**
     * @return 财务固定资产编号
     */
    public String getFinancialNum() {
        return financialNum;
    }

    public void setFinancialNum(String financialNum) {
        this.financialNum = financialNum;
    }

    /**
     * @return 转固日期
     */
    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    /**
     * @return 资产账簿，URL值集（开发人员补充值集名称）
     */
    public String getAssetBookCode() {
        return assetBookCode;
    }

    public void setAssetBookCode(String assetBookCode) {
        this.assetBookCode = assetBookCode;
    }

    /**
     * @return 资产目录，URL值集（开发人员补充值集名称）
     */
    public Long getAssetCatalogId() {
        return assetCatalogId;
    }

    public void setAssetCatalogId(Long assetCatalogId) {
        this.assetCatalogId = assetCatalogId;
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
     * @return 折旧起始日期
     */
    public Date getDepreciationStartDate() {
        return depreciationStartDate;
    }

    public void setDepreciationStartDate(Date depreciationStartDate) {
        this.depreciationStartDate = depreciationStartDate;
    }

    /**
     * @return 折旧月份
     */
    public int getDepreciationMouth() {
        return depreciationMouth;
    }

    public void setDepreciationMouth(int depreciationMouth) {
        this.depreciationMouth = depreciationMouth;
    }

    /**
     * @return 折旧类型，独立值集（开发人员补充值集名称）
     */
    public String getDepreciationTypeCode() {
        return depreciationTypeCode;
    }

    public void setDepreciationTypeCode(String depreciationTypeCode) {
        this.depreciationTypeCode = depreciationTypeCode;
    }

    /**
     * @return 残值率
     */
    public BigDecimal getResidualValueRate() {
        return residualValueRate;
    }

    public void setResidualValueRate(BigDecimal residualValueRate) {
        this.residualValueRate = residualValueRate;
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

    public String getAssetBookMeaning() {
        return assetBookMeaning;
    }

    public void setAssetBookMeaning(String assetBookMeaning) {
        this.assetBookMeaning = assetBookMeaning;
    }

    public String getAssetCatalogName() {
        return assetCatalogName;
    }

    public void setAssetCatalogName(String assetCatalogName) {
        this.assetCatalogName = assetCatalogName;
    }

    public String getDepreciationTypeMeaning() {
        return depreciationTypeMeaning;
    }

    public void setDepreciationTypeMeaning(String depreciationTypeMeaning) {
        this.depreciationTypeMeaning = depreciationTypeMeaning;
    }

    public Date getTransferDateFrom() {
        return transferDateFrom;
    }

    public void setTransferDateFrom(Date transferDateFrom) {
        this.transferDateFrom = transferDateFrom;
    }

    public Date getTransferDateTo() {
        return transferDateTo;
    }

    public void setTransferDateTo(Date transferDateTo) {
        this.transferDateTo = transferDateTo;
    }

    public String getDetailSelectItem() {
        return detailSelectItem;
    }

    public void setDetailSelectItem(String detailSelectItem) {
        this.detailSelectItem = detailSelectItem;
    }

    public List<FixedAssetsChanges> getFixedAssetsChangesList() {
        return fixedAssetsChangesList;
    }

    public void setFixedAssetsChangesList(List<FixedAssetsChanges> fixedAssetsChangesList) {
        this.fixedAssetsChangesList = fixedAssetsChangesList;
    }
}
