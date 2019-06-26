package org.hzero.halm.atn.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

import java.util.Date;
import java.util.List;

/**
 * 资产报废单头
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:50
 */
@ApiModel("资产报废单头")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_scrap_order_header")
@ExcelSheet(zh = "资产报废单", en = "Scrap Order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScrapOrderHeader extends AuditDomain {

    public static final String FIELD_SCRAP_HEADER_ID = "scrapHeaderId";
    public static final String FIELD_TRANSACTION_TYPE_ID = "transactionTypeId";
    public static final String FIELD_PROCESS_STATUS = "processStatus";
    public static final String FIELD_PRINCIPAL_PERSON_ID = "principalPersonId";
    public static final String FIELD_SCRAP_NUM = "scrapNum";
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
    private Long scrapHeaderId;
    @ApiModelProperty(value = "事务类型，URL值集，AATN.ASSET_TRANSACTION_TYPE")
    @NotNull
    private Long transactionTypeId;
    @ApiModelProperty(value = "处理状态，值集")
    @NotBlank
    @LovValue(lovCode = "HALM.APPROVE_STATUS",meaningField = "processStatusMeaning")
    private String processStatus;
    @ApiModelProperty(value = "负责人,员工id,值集：")
    private Long principalPersonId;
    @ApiModelProperty(value = "报废单归还单编号")
    private String scrapNum;
    @ApiModelProperty(value = "标题概述")
    @NotNull
    private String titleOverview;
    @ExcelColumn(zh = "计划执行日期", en = "Execute date", pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    private Date planStartDate;
    @ExcelColumn(zh = "计划完成日期", en = "Planned date", pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    private Date planEndDate;
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
    private List<ScrapOrderLine> scrapOrderLines;

    /**
     * 事件类型名称，匹配shortName or longName, 展示shortName
     */
    @Transient
    @ExcelColumn(zh = "事务类型", en = "Transaction name")
    private String transactionName;
    @Transient
    @ExcelColumn(zh = "处理状态", en = "Process status")
    private String processStatusMeaning;
    /**
     * 员工id关联
     */
    @Transient
    @ExcelColumn(zh = "负责人", en = "Principal")
    private String principalPersonName;

    @Transient
    private String planStartDateFrom;
    @Transient
    private String planStartDateTo;
    @Transient
    private String planEndDateFrom;
    @Transient
    private String planEndDateTo;
    @Transient
    private Long scrapLineId;

    @Transient
    private String condition;
    @Transient
    private List<String> processStatusCodeList;

    @Transient
    private Long assetId;
    @Transient
    private String lineProcessStatus;
    @Transient
    private String transactionTypeMeaning;
    @Transient
    private String lineAssetName;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

    public List<ScrapOrderLine> getScrapOrderLines() {
        return scrapOrderLines;
    }

    public void setScrapOrderLines(List<ScrapOrderLine> scrapOrderLines) {
        this.scrapOrderLines = scrapOrderLines;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
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

    public Long getScrapLineId() {
        return scrapLineId;
    }

    public void setScrapLineId(Long scrapLineId) {
        this.scrapLineId = scrapLineId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<String> getProcessStatusCodeList() {
        return processStatusCodeList;
    }

    public void setProcessStatusCodeList(List<String> processStatusCodeList) {
        this.processStatusCodeList = processStatusCodeList;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getLineProcessStatus() {
        return lineProcessStatus;
    }

    public void setLineProcessStatus(String lineProcessStatus) {
        this.lineProcessStatus = lineProcessStatus;
    }

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getScrapHeaderId() {
        return scrapHeaderId;
    }

    public void setScrapHeaderId(Long scrapHeaderId) {
        this.scrapHeaderId = scrapHeaderId;
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
     * @return 报废单归还单编号
     */
    public String getScrapNum() {
        return scrapNum;
    }

    public void setScrapNum(String scrapNum) {
        this.scrapNum = scrapNum;
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

    public String getTransactionTypeMeaning() {
        return transactionTypeMeaning;
    }

    public void setTransactionTypeMeaning(String transactionTypeMeaning) {
        this.transactionTypeMeaning = transactionTypeMeaning;
    }

    public String getLineAssetName() {
        return lineAssetName;
    }

    public void setLineAssetName(String lineAssetName) {
        this.lineAssetName = lineAssetName;
    }

}
