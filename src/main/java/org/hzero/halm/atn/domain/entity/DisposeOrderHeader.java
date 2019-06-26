package org.hzero.halm.atn.domain.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资产处置单头
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 11:05:34
 */
@ApiModel("资产处置单头")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_dispose_order_header")
@ExcelSheet(zh = "资产处置单", en = "Dispose Order")
public class DisposeOrderHeader extends AuditDomain {

    public static final String FIELD_DISPOSE_HEADER_ID = "disposeHeaderId";
    public static final String FIELD_TRANSACTION_TYPE_ID = "transactionTypeId";
    public static final String FIELD_PROCESS_STATUS = "processStatus";
    public static final String FIELD_PRINCIPAL_PERSON_ID = "principalPersonId";
    public static final String FIELD_DISPOSE_NUM = "disposeNum";
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
    private Long disposeHeaderId;
    @ApiModelProperty(value = "事务类型，URL值集，AATN.ASSET_TRANSACTION_TYPE")
    @NotNull
    private Long transactionTypeId;
    @ApiModelProperty(value = "处理状态，值集HALM.APPROVE_STATUS")
    @NotBlank
    @LovValue(value = "HALM.APPROVE_STATUS", meaningField = "processStatusMeaning")
    private String processStatus;
    @ApiModelProperty(value = "负责人,员工id,值集：")
    private Long principalPersonId;
    @ApiModelProperty(value = "资产处置单编号")
    @NotBlank
    @ExcelColumn(zh = "资产处置单编号", en = "Dispose Order Number")
    private String disposeNum;
    @ApiModelProperty(value = "标题概述")
    @ExcelColumn(zh = "标题概述", en = "Title Overview")
    private String titleOverview;
    @ApiModelProperty(value = "计划执行日期")
    @ExcelColumn(zh = "计划执行日期", en = "Execute date", pattern = BaseConstants.Pattern.DATE)
    private Date planStartDate;
    @ApiModelProperty(value = "计划完成日期")
    @ExcelColumn(zh = "计划完成日期", en = "Planned date", pattern = BaseConstants.Pattern.DATE)
    private Date planEndDate;
    @ExcelColumn(zh = "描述", en = "Description")
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    /**
     * 行数据
     */
    @Transient
    @Valid
    private List<DisposeOrderLine> disposeOrderLines;

    @Transient
    @ApiModelProperty(value = "计划执行日期从")
    private String planStartDateFrom;
    @Transient
    @ApiModelProperty(value = "计划执行日期至")
    private String planStartDateTo;
    @Transient
    @ApiModelProperty(value = "计划完成日期从")
    private String planEndDateFrom;
    @Transient
    @ApiModelProperty(value = "计划完成日期至")
    private String planEndDateTo;
    @Transient
    @ApiModelProperty(value = "明细列表的查询条件")
    private String condition;
    @Transient
    private List<String> processStatusCodeList;

    /**
     * 事件类型名称，匹配shortName or longName, 展示shortName
     * 导出Excel字段
     */
    @Transient
    @ExcelColumn(zh = "事务类型", en = "Transaction name")
    private String transactionTypeMeaning;
    @Transient
    private String transactionTypeCode;
    @Transient
    @ExcelColumn(zh = "负责人", en = "Principal")
    private String principalPersonName;
    @Transient
    @ExcelColumn(zh = "处理状态", en = "Process status")
    private String processStatusMeaning;
    /**
     * 入口&处理页的行查询条件
     */
    @Transient
    private String lineAssetId;
    @Transient
    private String lineAssetName;
    @Transient
    private String lineProcessStatus;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getDisposeHeaderId() {
        return disposeHeaderId;
    }

    public void setDisposeHeaderId(Long disposeHeaderId) {
        this.disposeHeaderId = disposeHeaderId;
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
     * @return 资产处置单编号
     */
    public String getDisposeNum() {
        return disposeNum;
    }

    public void setDisposeNum(String disposeNum) {
        this.disposeNum = disposeNum;
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

    public List<DisposeOrderLine> getDisposeOrderLines() {
        return disposeOrderLines;
    }

    public void setDisposeOrderLines(List<DisposeOrderLine> disposeOrderLines) {
        this.disposeOrderLines = disposeOrderLines;
    }

    public String getPlanStartDateFrom() {
        return planStartDateFrom;
    }

    public void setPlanStartDateFrom(String planStartDateFrom) {
        this.planStartDateFrom = planStartDateFrom;
    }

    public String getPlanStartDateTo() {
        return planStartDateTo;
    }

    public void setPlanStartDateTo(String planStartDateTo) {
        this.planStartDateTo = planStartDateTo;
    }

    public String getPlanEndDateFrom() {
        return planEndDateFrom;
    }

    public void setPlanEndDateFrom(String planEndDateFrom) {
        this.planEndDateFrom = planEndDateFrom;
    }

    public String getPlanEndDateTo() {
        return planEndDateTo;
    }

    public void setPlanEndDateTo(String planEndDateTo) {
        this.planEndDateTo = planEndDateTo;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTransactionTypeMeaning() {
        return transactionTypeMeaning;
    }

    public void setTransactionTypeMeaning(String transactionTypeMeaning) {
        this.transactionTypeMeaning = transactionTypeMeaning;
    }

    public String getPrincipalPersonName() {
        return principalPersonName;
    }

    public void setPrincipalPersonName(String principalPersonName) {
        this.principalPersonName = principalPersonName;
    }

    public String getProcessStatusMeaning() {
        return processStatusMeaning;
    }

    public void setProcessStatusMeaning(String processStatusMeaning) {
        this.processStatusMeaning = processStatusMeaning;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public String getLineAssetName() {
        return lineAssetName;
    }

    public void setLineAssetName(String lineAssetName) {
        this.lineAssetName = lineAssetName;
    }

    public String getLineProcessStatus() {
        return lineProcessStatus;
    }

    public void setLineProcessStatus(String lineProcessStatus) {
        this.lineProcessStatus = lineProcessStatus;
    }

    public List<String> getProcessStatusCodeList() {
        return processStatusCodeList;
    }

    public void setProcessStatusCodeList(List<String> processStatusCodeList) {
        this.processStatusCodeList = processStatusCodeList;
    }

    public String getLineAssetId() {
        return lineAssetId;
    }

    public void setLineAssetId(String lineAssetId) {
        this.lineAssetId = lineAssetId;
    }
}
