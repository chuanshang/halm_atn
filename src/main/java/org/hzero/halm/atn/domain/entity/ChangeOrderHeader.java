package org.hzero.halm.atn.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
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

/**
 * 资产信息变更单头
 *
 * @author like.zhang@hand-china.com 2019-03-26 10:20:34
 */
@ApiModel("资产信息变更单头")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_change_order_header")
@ExcelSheet(zh = "资产信息变更单", en = "Change Order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeOrderHeader extends AuditDomain {

    public static final String FIELD_CHANGE_HEADER_ID = "changeHeaderId";
    public static final String FIELD_TRANSACTION_TYPE_ID = "transactionTypeId";
    public static final String FIELD_PROCESS_STATUS = "processStatus";
    public static final String FIELD_PRINCIPAL_PERSON_ID = "principalPersonId";
    public static final String FIELD_CHANGE_NUM = "changeNum";
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
    private Long changeHeaderId;
    @ApiModelProperty(value = "事务类型，URL值集，AATN.ASSET_TRANSACTION_TYPE")
    @NotNull
    private Long transactionTypeId;
    @ApiModelProperty(value = "处理状态，值集HALM.APPROVE_STATUS")
    @Length(max = 30)
    @LovValue(lovCode = "HALM.APPROVE_STATUS")
    private String processStatus;
    @ApiModelProperty(value = "负责人,员工id,值集：")
    private Long principalPersonId;
    @ExcelColumn(zh = "资产信息变更单编号", en = "Change Number")
    @Length(max = 30)
    private String changeNum;
    @ExcelColumn(zh = "标题概述", en = "Title Overview")
    @Length(max = 240)
    private String titleOverview;
    @ExcelColumn(zh = "计划执行日期", en = "Execute date", pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    private Date planStartDate;
    @ExcelColumn(zh = "计划完成日期", en = "Planned date", pattern = BaseConstants.Pattern.DATE)
    @JsonFormat(pattern = BaseConstants.Pattern.DATE)
    private Date planEndDate;
    @ExcelColumn(zh = "描述", en = "Description")
    @Length(max = 240)
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
    private List<ChangeOrderLine> changeOrderLines;
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
    private Date planStartDateFrom;
    @Transient
    private Date planStartDateTo;
    @Transient
    private Date planEndDateFrom;
    @Transient
    private Date planEndDateTo;
    @Transient
    private Long transferLineId;
    
    
	@Transient
    private String condition;
	@Transient
    private List<String> processStatusCodeList;

    @Transient
    private Long assetId;
    @Transient
    private String lineProcessStatus;
    @Transient
    private Long changeLineId;

    @Transient
    private String[] changeOrderHeaderIds;

    //
    // getter/setter

    // ------------------------------------------------------------------------------

    public Long getChangeLineId() {
        return changeLineId;
    }

    public void setChangeLineId(Long changeLineId) {
        this.changeLineId = changeLineId;
    }

    public String[] getChangeOrderHeaderIds() {
        return changeOrderHeaderIds;
    }

    public void setChangeOrderHeaderIds(String[] changeOrderHeaderIds) {
        this.changeOrderHeaderIds = changeOrderHeaderIds;
    }

    public List<ChangeOrderLine> getChangeOrderLines() {
        return changeOrderLines;
    }

    public void setChangeOrderLines(List<ChangeOrderLine> changeOrderLines) {
        this.changeOrderLines = changeOrderLines;
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

    public Long getTransferLineId() {
        return transferLineId;
    }

    public void setTransferLineId(Long transferLineId) {
        this.transferLineId = transferLineId;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getChangeHeaderId() {
		return changeHeaderId;
	}

	public void setChangeHeaderId(Long changeHeaderId) {
		this.changeHeaderId = changeHeaderId;
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
     * @return 负责人,员工id,值集：
     */
	public Long getPrincipalPersonId() {
		return principalPersonId;
	}

	public void setPrincipalPersonId(Long principalPersonId) {
		this.principalPersonId = principalPersonId;
	}
    /**
     * @return 资产信息变更单编号
     */
	public String getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(String changeNum) {
		this.changeNum = changeNum;
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

    public void setProcessStatusCodeList(List<String> processStatusCodeList) {
        this.processStatusCodeList = processStatusCodeList;
    }

    public List<String> getProcessStatusCodeList() {
        return processStatusCodeList;
    }
}
