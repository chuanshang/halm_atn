package org.hzero.halm.atn.domain.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.halm.atn.infra.constant.TransactionLine;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 资产移交归还单行
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:48:48
 */
@ApiModel("资产移交归还单行")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_handover_order_line")
public class HandoverOrderLine extends TransactionLine {

    public static final String FIELD_HANDOVER_LINE_ID = "handoverLineId";
    public static final String FIELD_HANDOVER_HEADER_ID = "handoverHeaderId";
    public static final String FIELD_LINE_NUM = "lineNum";
    public static final String FIELD_ASSET_ID = "assetId";
    public static final String FIELD_PROCESS_STATUS = "processStatus";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TENANT_ID = "tenantId";
    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------


    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long handoverLineId;
    @ApiModelProperty(value = "资产移交归还单头ID")
    private Long handoverHeaderId;

    @ApiModelProperty(value = "事务处理行号")
    private Long lineNum;
    @ApiModelProperty(value = "设备/资产ID")
    @NotNull
    private Long assetId;
    @ApiModelProperty(value = "处理状态，值集")
    @NotBlank
    @LovValue(lovCode = "AATN.HANDOVER_LINE_STATUS")
    private String processStatus;
    @ApiModelProperty(value = "描述")
    @NotBlank
    private String description;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    /**
     * 资产全称
     */
    @Transient
    private String assetDesc;
    /**
     * 可视标签
     */
    @Transient
    private String name;
    @Transient
    private String assetCode;
    @Transient
    private String processStatusMeaning;

    @Transient
    @Valid
    private List<HandoverOrderDetail> detailList;

    @Transient
    private Long transactionTypeId;
    @Transient
    private String headerStatus;
    @Transient
    private String assetsSetName;
    @Transient
    private Long principalPersonId;
    @Transient
    private String handoverNum;
    @Transient
    private String titleOverview;
    @Transient
    private Date planStartDateFrom;
    @Transient
    private Date planStartDateTo;
    @Transient
    private Date planEndDateFrom;
    @Transient
    private Date planEndDateTo;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getHandoverLineId() {
        return handoverLineId;
    }

    public void setHandoverLineId(Long handoverLineId) {
        this.handoverLineId = handoverLineId;
    }

    /**
     * @return 资产移交归还单头ID
     */
    public Long getHandoverHeaderId() {
        return handoverHeaderId;
    }

    public void setHandoverHeaderId(Long handoverHeaderId) {
        this.handoverHeaderId = handoverHeaderId;
    }

    /**
     * @return 事务处理行号
     */
    public Long getLineNum() {
        return lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * @return 设备/资产ID
     */
    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    /**
     * @return 处理状态，值集
     */
    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
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


    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public List<HandoverOrderDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HandoverOrderDetail> detailList) {
        this.detailList = detailList;
    }

    public String getProcessStatusMeaning() {
        return processStatusMeaning;
    }

    public String getAssetDesc() {
        return assetDesc;
    }

    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProcessStatusMeaning(String processStatusMeaning) {
        this.processStatusMeaning = processStatusMeaning;
    }

    public Date getPlanEndDateTo() {
        return planEndDateTo;
    }

    public void setPlanEndDateTo(Date planEndDateTo) {
        this.planEndDateTo = planEndDateTo;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getHeaderStatus() {
        return headerStatus;
    }

    public void setHeaderStatus(String headerStatus) {
        this.headerStatus = headerStatus;
    }

    public Long getPrincipalPersonId() {
        return principalPersonId;
    }

    public void setPrincipalPersonId(Long principalPersonId) {
        this.principalPersonId = principalPersonId;
    }

    public String getHandoverNum() {
        return handoverNum;
    }

    public void setHandoverNum(String handoverNum) {
        this.handoverNum = handoverNum;
    }

    public String getTitleOverview() {
        return titleOverview;
    }

    public void setTitleOverview(String titleOverview) {
        this.titleOverview = titleOverview;
    }

    public Date getPlanStartDateFrom() {
        return planStartDateFrom;
    }

    public void setPlanStartDateFrom(Date planStartDateFrom) {
        this.planStartDateFrom = planStartDateFrom;
    }

    public Date getPlanStartDateTo() {
        return planStartDateTo;
    }

    public void setPlanStartDateTo(Date planStartDateTo) {
        this.planStartDateTo = planStartDateTo;
    }

    public Date getPlanEndDateFrom() {
        return planEndDateFrom;
    }

    public String getAssetsSetName() {
        return assetsSetName;
    }

    public void setAssetsSetName(String assetsSetName) {
        this.assetsSetName = assetsSetName;
    }

    public void setPlanEndDateFrom(Date planEndDateFrom) {
        this.planEndDateFrom = planEndDateFrom;
    }
}
