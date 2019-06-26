package org.hzero.halm.fam.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;

import java.math.BigDecimal;
import java.util.List;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;

/**
 * 固定资产价值变动
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:23:27
 */
@ApiModel("固定资产价值变动")
@VersionAudit
@ModifyAudit
@Table(name = "afam_fixed_assets_changes")
public class FixedAssetsChanges extends AuditDomain {

    public static final String FIELD_CHANGE_ID = "changeId";
    public static final String FIELD_FIXED_ASSET_ID = "fixedAssetId";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_CHANGE_TYPE_CODE = "changeTypeCode";
    public static final String FIELD_CHANGE_VALUE = "changeValue";
    public static final String FIELD_CORRELATED_EVENT = "correlatedEvent";
    public static final String FIELD_ACCOUNTING_VOUCHER_NUMBER = "accountingVoucherNumber";
    public static final String FIELD_DESCRIPTION = "description";
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
    private Long changeId;
    @ApiModelProperty(value = "固定资产ID")
    private Long fixedAssetId;
    @ApiModelProperty(value = "期间，URL值集（开发人员补充值集名称）")
    @NotBlank
    private String periodName;
    @ApiModelProperty(value = "价值变动类型，独立值集: AFAM.ASSET_CHANGE_TYPE")
    @LovValue(value = "AFAM.ASSET_CHANGE_TYPE", meaningField = "changeTypeMeaning")
    @NotBlank
    private String changeTypeCode;
    @ApiModelProperty(value = "价值变动类型")
    @Transient
    private String changeTypeMeaning;
    @ApiModelProperty(value = "价值变动金额")
    @NotNull
    private BigDecimal changeValue;
    @ApiModelProperty(value = "关联事件")
    private String correlatedEvent;
    @ApiModelProperty(value = "会计凭证编号")
    private String accountingVoucherNumber;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------


    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    /**
     * @return 固定资产ID
     */
    public Long getFixedAssetId() {
        return fixedAssetId;
    }

    public void setFixedAssetId(Long fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    /**
     * @return 期间，URL值集（开发人员补充值集名称）
     */
    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    /**
     * @return 价值变动类型，独立值集（开发人员补充值集名称）
     */
    public String getChangeTypeCode() {
        return changeTypeCode;
    }

    public void setChangeTypeCode(String changeTypeCode) {
        this.changeTypeCode = changeTypeCode;
    }

    /**
     * @return 价值变动金额
     */
    public BigDecimal getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(BigDecimal changeValue) {
        this.changeValue = changeValue;
    }

    /**
     * @return 关联事件
     */
    public String getCorrelatedEvent() {
        return correlatedEvent;
    }

    public void setCorrelatedEvent(String correlatedEvent) {
        this.correlatedEvent = correlatedEvent;
    }

    /**
     * @return 会计凭证编号
     */
    public String getAccountingVoucherNumber() {
        return accountingVoucherNumber;
    }

    public void setAccountingVoucherNumber(String accountingVoucherNumber) {
        this.accountingVoucherNumber = accountingVoucherNumber;
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
     * @return 租户ID
     */
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getChangeTypeMeaning() {
        return changeTypeMeaning;
    }

    public void setChangeTypeMeaning(String changeTypeMeaning) {
        this.changeTypeMeaning = changeTypeMeaning;
    }

}
