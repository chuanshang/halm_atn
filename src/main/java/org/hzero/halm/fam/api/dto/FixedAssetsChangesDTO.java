package org.hzero.halm.fam.api.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.halm.fam.domain.entity.FixedAssetsChanges;
import org.hzero.mybatis.domian.SecurityToken;

import java.math.BigDecimal;

@ExcelSheet(zh = "价值变动", en = "Fixed Assets Changes")
public class FixedAssetsChangesDTO implements SecurityToken {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    private Long changeId;
    @ApiModelProperty(value = "固定资产ID")
    private Long fixedAssetId;
    @ApiModelProperty(value = "期间，URL值集（开发人员补充值集名称）")
    @ExcelColumn(zh = "期间", en = "Period Name")
    private String periodName;
    @ApiModelProperty(value = "价值变动类型，独立值集: AFAM.ASSET_CHANGE_TYPE")
    @LovValue(value = "AFAM.ASSET_CHANGE_TYPE", meaningField = "changeTypeMeaning")
    private String changeTypeCode;
    @ApiModelProperty(value = "价值变动类型")
    @ExcelColumn(zh = "价值变动类型", en = "Change Type")
    private String changeTypeMeaning;
    @ApiModelProperty(value = "价值变动金额")
    @ExcelColumn(zh = "价值变动金额", en = "Change Value")
    private BigDecimal changeValue;
    @ApiModelProperty(value = "关联事件")
    @ExcelColumn(zh = "关联事件", en = "Correlated Event")
    private String correlatedEvent;
    @ApiModelProperty(value = "会计凭证编号")
    @ExcelColumn(zh = "会计凭证编号", en = "Accounting Voucher Number")
    private String accountingVoucherNumber;
    @ApiModelProperty(value = "描述")
    @ExcelColumn(zh = "描述", en = "description")
    private String description;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;


    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    public Long getFixedAssetId() {
        return fixedAssetId;
    }

    public void setFixedAssetId(Long fixedAssetId) {
        this.fixedAssetId = fixedAssetId;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getChangeTypeCode() {
        return changeTypeCode;
    }

    public void setChangeTypeCode(String changeTypeCode) {
        this.changeTypeCode = changeTypeCode;
    }

    public String getChangeTypeMeaning() {
        return changeTypeMeaning;
    }

    public void setChangeTypeMeaning(String changeTypeMeaning) {
        this.changeTypeMeaning = changeTypeMeaning;
    }

    public BigDecimal getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(BigDecimal changeValue) {
        this.changeValue = changeValue;
    }

    public String getCorrelatedEvent() {
        return correlatedEvent;
    }

    public void setCorrelatedEvent(String correlatedEvent) {
        this.correlatedEvent = correlatedEvent;
    }

    public String getAccountingVoucherNumber() {
        return accountingVoucherNumber;
    }

    public void setAccountingVoucherNumber(String accountingVoucherNumber) {
        this.accountingVoucherNumber = accountingVoucherNumber;
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
        return FixedAssetsChanges.class;
    }
}
