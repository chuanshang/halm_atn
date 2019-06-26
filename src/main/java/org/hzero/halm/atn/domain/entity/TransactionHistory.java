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
import java.util.List;

/**
 * 资产处理历史信息
 *
 * @author sen.wang@hand-china.com 2019-03-28 14:03:23
 */
@ApiModel("资产处理历史信息")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_transaction_history")
public class TransactionHistory extends AuditDomain {

    public static final String FIELD_TRANSACTION_HISTORY_ID = "transactionHistoryId";
    public static final String FIELD_ASSET_ID = "assetId";
    public static final String FIELD_TRANSACTION_HEADER_ID = "transactionHeaderId";
    public static final String FIELD_TRANSACTION_LINE_ID = "transactionLineId";
    public static final String FIELD_TRANSACTION_TYPE_ID = "transactionTypeId";
    public static final String FIELD_PROCESS_STATUS_RECORD = "processStatusRecord";
    public static final String FIELD_PROCESS_TIME_RECORD = "processTimeRecord";
    public static final String FIELD_TENANT_ID = "tenantId";

	/**
	 * 对应着资产数据库的字段
	 */
	public static final String EVENT_MAP_KEY_FIELD = "fieldTable";
	/**
	 * 对应着资产，字段的中文含义
	 */
    public static final String EVENT_MAP_KEY_FIELD_MEANING = "fieldName";
    public static final String EVENT_MAP_KEY_LIST = "listData";



    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long transactionHistoryId;
    @ApiModelProperty(value = "资产ID")
    @NotNull
    private Long assetId;
    @ApiModelProperty(value = "事务处理头id")
    @NotNull
    private Long transactionHeaderId;
    @ApiModelProperty(value = "事务处理行id")
    @NotNull
    private Long transactionLineId;
    @ApiModelProperty(value = "事务处理类型id")
    @NotNull
    private Long transactionTypeId;
    @ApiModelProperty(value = "当前资产处理状态")
    @NotBlank
    private String processStatusRecord;
    @ApiModelProperty(value = "目标资产处理状态")
    @NotBlank
    private String processTimeRecord;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

	@Transient
	private String transactionNum;
	@Transient
	private String titleOverview;
	@Transient
	private String description;
	@Transient
	private String typeName;
	@Transient
	private String shortName;
	@Transient
	private Long headerId;
	@Transient
	private Date createDateFrom;
	@Transient
	private Date createDateTo;
	@Transient
	private String processStatusRecordMeaning;
	@Transient
	private List transactionTypeIdList;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getTransactionHistoryId() {
		return transactionHistoryId;
	}

	public void setTransactionHistoryId(Long transactionHistoryId) {
		this.transactionHistoryId = transactionHistoryId;
	}
    /**
     * @return 资产ID
     */
	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
    /**
     * @return 事务处理头id
     */
	public Long getTransactionHeaderId() {
		return transactionHeaderId;
	}

	public void setTransactionHeaderId(Long transactionHeaderId) {
		this.transactionHeaderId = transactionHeaderId;
	}
    /**
     * @return 事务处理行id
     */
	public Long getTransactionLineId() {
		return transactionLineId;
	}

	public void setTransactionLineId(Long transactionLineId) {
		this.transactionLineId = transactionLineId;
	}
    /**
     * @return 事务处理类型id
     */
	public Long getTransactionTypeId() {
		return transactionTypeId;
	}

	public String getProcessStatusRecord() {
		return processStatusRecord;
	}

	public void setProcessStatusRecord(String processStatusRecord) {
		this.processStatusRecord = processStatusRecord;
	}

	public String getProcessTimeRecord() {
		return processTimeRecord;
	}

	public void setProcessTimeRecord(String processTimeRecord) {
		this.processTimeRecord = processTimeRecord;
	}

	public String getTransactionNum() {
		return transactionNum;
	}

	public void setTransactionNum(String transactionNum) {
		this.transactionNum = transactionNum;
	}

	public String getTitleOverview() {
		return titleOverview;
	}

	public void setTitleOverview(String titleOverview) {
		this.titleOverview = titleOverview;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTransactionTypeId(Long transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
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

	public String getTypeName() {
		return typeName;
	}

	public Long getHeaderId() {
		return headerId;
	}

	public void setHeaderId(Long headerId) {
		this.headerId = headerId;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Date getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Date getCreateDateTo() {
		return createDateTo;
	}

	public String getProcessStatusRecordMeaning() {
		return processStatusRecordMeaning;
	}

	public void setProcessStatusRecordMeaning(String processStatusRecordMeaning) {
		this.processStatusRecordMeaning = processStatusRecordMeaning;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public List getTransactionTypeIdList() {
		return transactionTypeIdList;
	}

	public void setTransactionTypeIdList(List transactionTypeIdList) {
		this.transactionTypeIdList = transactionTypeIdList;
	}

	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}
}
