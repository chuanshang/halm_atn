package org.hzero.halm.atn.domain.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 资产移交归还单头
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:03
 */
@ApiModel("资产移交归还单头")
@VersionAudit
@ModifyAudit
@ExcelSheet
@Table(name = "aatn_handover_order_header")
public class HandoverOrderHeader extends AuditDomain {

    public static final String FIELD_HANDOVER_HEADER_ID = "handoverHeaderId";
    public static final String FIELD_TRANSACTION_TYPE_ID = "transactionTypeId";
    public static final String FIELD_PROCESS_STATUS = "processStatus";
    public static final String FIELD_PRINCIPAL_PERSON_ID = "principalPersonId";
    public static final String FIELD_HANDOVER_NUM = "handoverNum";
    public static final String FIELD_TITLE_OVERVIEW = "titleOverview";
    public static final String FIELD_PLAN_START_DATE = "planStartDate";
    public static final String FIELD_PLAN_END_DATE = "planEndDate";
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
    private Long handoverHeaderId;
    @ApiModelProperty(value = "事务类型，URL值集，AATN.ASSET_TRANSACTION_TYPE")
    @NotNull
    private Long transactionTypeId;
    @ApiModelProperty(value = "处理状态，值集")
    @NotBlank
    @LovValue(lovCode = "HALM.APPROVE_STATUS")
    private String processStatus;
    @ApiModelProperty(value = "负责人,员工id,值集：")
    @ExcelColumn(zh = "负责人", en = "principal personId")
    private Long principalPersonId;
    @ApiModelProperty(value = "资产移交归还单编号")

    // 该字段后台自动赋值，前台不需要传值
    @Length(max = 30)
    @ExcelColumn(zh = "事务处理单编码", en = "handover number")
    private String handoverNum;
    @ExcelColumn(zh = "标题概述", en = "title overview")
    @ApiModelProperty(value = "标题概述")
    @Length(max = 240)
    private String titleOverview;
    @ExcelColumn(zh = "计划执行日期", en = "plan start date")
    @ApiModelProperty(value = "计划执行日期")
    private Date planStartDate;
    @ExcelColumn(zh = "计划结束时期", en = "plan end date")
    @ApiModelProperty(value = "计划完成日期")
    private Date planEndDate;
    @ApiModelProperty(value = "描述")
    @Length(max = 240)
    @ExcelColumn(zh = "描述", en = "description")
    private String description;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------


    @Transient
    @ApiModelProperty(value = "计划执行日期从")
    private Date planStartDateFrom;
    @Transient
    @ApiModelProperty(value = "计划执行日期至")
    private Date planStartDateTo;
    @Transient
    @ApiModelProperty(value = "计划完成日期从")
    private Date planEndDateFrom;
    @Transient
    @ApiModelProperty(value = "计划完成日期至")
    private Date planEndDateTo;
    @Transient
    @ApiModelProperty(value = "明细列表的查询条件")
    private String detailCondition;

    @Transient
    @ApiModelProperty(hidden = true)
    @ExcelColumn(zh = "事务类型", en = "transaction type")
    private String transactionTypeMeaning;
    @Transient
    @ExcelColumn(zh = "处理状态", en = "process status")
    private String processStatusMeaning;


    @Transient
    @ApiModelProperty(hidden = true)
    private String transactionTypeCode;
    @Transient
    private String principalPersonName;

    @Transient
    @Valid
    private List<HandoverOrderLine> lineList;


    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getHandoverHeaderId() {
        return handoverHeaderId;
    }

    public void setHandoverHeaderId(Long handoverHeaderId) {
        this.handoverHeaderId = handoverHeaderId;
    }

    /**
     * @return 事务类型，URL值集，AATN.ASSET_TRANSACTION_TYPE
     */
    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
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
     * @return 负责人, 员工id, 值集：
     */
    public Long getPrincipalPersonId() {
        return principalPersonId;
    }

    public void setPrincipalPersonId(Long principalPersonId) {
        this.principalPersonId = principalPersonId;
    }

    /**
     * @return 资产移交归还单编号
     */
    public String getHandoverNum() {
        return handoverNum;
    }

    public void setHandoverNum(String handoverNum) {
        this.handoverNum = handoverNum;
    }

    /**
     * @return 标题概述
     */
    public String getTitleOverview() {
        return titleOverview;
    }

    public void setTitleOverview(String titleOverview) {
        this.titleOverview = titleOverview;
    }

    /**
     * @return 计划执行日期
     */
    public Date getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    /**
     * @return 计划完成日期
     */
    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
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

    public void setPlanEndDateFrom(Date planEndDateFrom) {
        this.planEndDateFrom = planEndDateFrom;
    }

    public Date getPlanEndDateTo() {
        return planEndDateTo;
    }

    public void setPlanEndDateTo(Date planEndDateTo) {
        this.planEndDateTo = planEndDateTo;
    }

    public String getDetailCondition() {
        return detailCondition;
    }

    public void setDetailCondition(String detailCondition) {
        this.detailCondition = detailCondition;
    }

    public String getTransactionTypeMeaning() {
        return transactionTypeMeaning;
    }

    public void setTransactionTypeMeaning(String transactionTypeMeaning) {
        this.transactionTypeMeaning = transactionTypeMeaning;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public List<HandoverOrderLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<HandoverOrderLine> lineList) {
        this.lineList = lineList;
    }

    public String getProcessStatusMeaning() {
        return processStatusMeaning;
    }

    public void setProcessStatusMeaning(String processStatusMeaning) {
        this.processStatusMeaning = processStatusMeaning;
    }

    public String getPrincipalPersonName() {
        return principalPersonName;
    }

    public void setPrincipalPersonName(String principalPersonName) {
        this.principalPersonName = principalPersonName;
    }
}
