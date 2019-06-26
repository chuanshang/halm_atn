package org.hzero.halm.atn.domain.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * 资产字段历史明细
 *
 * @author sen.wang@hand-china.com 2019-03-30 14:14:11
 */
@ApiModel("资产字段历史明细")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_txn_history_detail")
public class TransactionHistoryDetail extends AuditDomain {

    public static final String FIELD_HISTORY_DETAIL_ID = "historyDetailId";
    public static final String FIELD_TRANSACTION_HISTORY_ID = "transactionHistoryId";
    public static final String FIELD_FIELD_NAME = "fieldName";
    public static final String FIELD_FIELD_VALUE = "fieldValue";
    public static final String FIELD_OCCUR_TIME = "occurTime";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("资产字段事务变化明细")
    @Id
    @GeneratedValue
    private Long historyDetailId;
    @ApiModelProperty(value = "资产信息主键ID")
    @NotNull
    private Long transactionHistoryId;
    @ApiModelProperty(value = "发生改变的字段")
    @NotBlank
    private String fieldName;
    @ApiModelProperty(value = "改变的字段值")
    @NotBlank
    private String fieldValue;
    @ApiModelProperty(value = "改变的字段含义")
    private String fieldMeaning;
    @ApiModelProperty(value = "发生改变的时间")
    @NotNull
    private Date occurTime;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @Transient
    @ApiModelProperty(value = "需要查询的资产id")
    private Long assetId;
    @Transient
    private Date occurTimeFrom;
    @Transient
    private Date occurTimeTo;
    @Transient
    @ApiModelProperty(value = "需要查询的字段集合")
    private Set fieldSet;

    @Transient
    private String transactionNum;
    @Transient
    private String typeName;
    @Transient
    private String titleOverview;

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 资产字段事务变化明细
     */
    public Long getHistoryDetailId() {
        return historyDetailId;
    }

    public void setHistoryDetailId(Long historyDetailId) {
        this.historyDetailId = historyDetailId;
    }

    /**
     * @return 资产信息主键ID
     */
    public Long getTransactionHistoryId() {
        return transactionHistoryId;
    }

    public void setTransactionHistoryId(Long transactionHistoryId) {
        this.transactionHistoryId = transactionHistoryId;
    }

    /**
     * @return 发生改变的字段
     */
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return 改变的字段值
     */
    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    /**
     * @return 发生改变的时间
     */
    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
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

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Date getOccurTimeFrom() {
        return occurTimeFrom;
    }

    public void setOccurTimeFrom(Date occurTimeFrom) {
        this.occurTimeFrom = occurTimeFrom;
    }

    public Date getOccurTimeTo() {
        return occurTimeTo;
    }

    public void setOccurTimeTo(Date occurTimeTo) {
        this.occurTimeTo = occurTimeTo;
    }

    public Set getFieldSet() {
        return fieldSet;
    }

    public void setFieldSet(Set fieldSet) {
        this.fieldSet = fieldSet;
    }

    public String getTransactionNum() {
        return transactionNum;
    }

    public void setTransactionNum(String transactionNum) {
        this.transactionNum = transactionNum;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFieldMeaning() {
        return fieldMeaning;
    }

    public String getTitleOverview() {
        return titleOverview;
    }

    public void setTitleOverview(String titleOverview) {
        this.titleOverview = titleOverview;
    }

    public void setFieldMeaning(String fieldMeaning) {
        this.fieldMeaning = fieldMeaning;
    }
}
