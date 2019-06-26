package org.hzero.halm.rcv.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
 * 验收单
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@ApiModel("验收单")
@VersionAudit
@ModifyAudit
@Table(name = "arcv_acceptance_header")
@ExcelSheet(zh = "验收单", en = "Acceptance")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcceptanceHeader extends AuditDomain {

	public static final String FIELD_ACCEPTANCE_HEADER_ID = "acceptanceHeaderId";
	public static final String FIELD_ACCEPTANCE_TYPE_ID = "acceptanceTypeId";
	public static final String FIELD_ACCEPTANCE_STATUS_CODE = "acceptanceStatusCode";
	public static final String FIELD_PRINCIPAL_PERSON_ID = "principalPersonId";
	public static final String FIELD_ACCEPTANCE_NUM = "acceptanceNum";
	public static final String FIELD_TITLE = "title";
	public static final String FIELD_SUBMIT_DATE = "submitDate";
	public static final String FIELD_COMPLETE_DATE = "completeDate";
	public static final String FIELD_REQUEST_DEPARTMENT_ID = "requestDepartmentId";
	public static final String FIELD_PURCHASE_DEPARTMENT_ID = "purchaseDepartmentId";
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
	private Long acceptanceHeaderId;
	@ApiModelProperty(value = "验收类型，URL值集（ARCV.ACCEPTANCE_ORDER_TYPE）")
	@NotNull
	private Long acceptanceTypeId;
	@ApiModelProperty(value = "验收状态，独立值集（ARCV.ACCEPTANCE_STATUS）")
	@LovValue(lovCode = "ARCV.ACCEPTANCE_STATUS", meaningField = "acceptanceStatusMeaning")
	@NotBlank
	private String acceptanceStatusCode;
	@ApiModelProperty(value = "负责人，URL值集（HALM.EMPLOYEE），人员值列表")
	@NotNull
	private Long principalPersonId;
	@ApiModelProperty(value = "验收单编号 ")
	private String acceptanceNum;
	@ApiModelProperty(value = "标题概述")
	@NotBlank
	private String title;
	@ExcelColumn(zh = "提交日期", en = "Submit Date", pattern = BaseConstants.Pattern.DATE)
	@ApiModelProperty(value = "提交日期")
	private Date submitDate;
	@ExcelColumn(zh = "完成日期", en = "Complete Date", pattern = BaseConstants.Pattern.DATE)
	@ApiModelProperty(value = "完成日期")
	private Date completeDate;
	@ApiModelProperty(value = "申请部门，URL值集（AMDM.ORGANIZATION）类型为部门的组织")
	@NotNull
	private Long requestDepartmentId;
	@ApiModelProperty(value = "采购部门，URL值集（AMDM.ORGANIZATION）类型为部门的组织")
	@NotNull
	private Long purchaseDepartmentId;
	@ExcelColumn(zh = "描述", en = "description")
	@ApiModelProperty(value = "描述")
	private String description;
	@ApiModelProperty(value = "租户ID")
	@NotNull
	private Long tenantId;

	//
	// 非数据库字段
	// ------------------------------------------------------------------------------
	/**
	 * 验收类型Meaning
	 */
	@ExcelColumn(zh = "验收类型", en = "Acceptance Type")
	@Transient
	private String acceptanceTypeMeaning;

	/**
	 * 验收状态Meaning
	 */
	@ExcelColumn(zh = "验收状态", en = "Acceptance Status")
	@Transient
	private String acceptanceStatusMeaning;
	/**
	 * 负责人Meaning
	 */
	@ExcelColumn(zh = "负责人", en = "Principal Person")
	@Transient
	private String principalPersonMeaning;
	/**
	 * 申请部门Meaning
	 */
	@ExcelColumn(zh = "申请部门", en = "Request Department")
	@Transient
	private String requestDepartmentMeaning;
	/**
	 * 采购部门Meaning
	 */
	@ExcelColumn(zh = "采购部门", en = "Purchase Department")
	@Transient
	private String purchaseDepartmentMeaning;
	/**
	 * 验收单基础类型
	 */
	@Transient
	private String acceptanceTypeCode;
	/**
	 * 验收单关联
	 */
	@Transient
	@Valid
	private List<AcceptanceRelation> acceptanceRelationList;

	/**
	 * 验收单行
	 */
	@Transient
	@Valid
	private List<AcceptanceLine> acceptanceLineList;

	/**
	 * 	验收单资产明细
	 */
	@Transient
	@Valid
	private List<AcceptanceAsset> acceptanceAssetList;

	//
	// getter/setter
	// ------------------------------------------------------------------------------

	/**
	 * @return 表ID，主键，供其他表做外键
	 */
	public Long getAcceptanceHeaderId() {
		return acceptanceHeaderId;
	}

	public void setAcceptanceHeaderId(Long acceptanceHeaderId) {
		this.acceptanceHeaderId = acceptanceHeaderId;
	}

	/**
	 * @return 验收类型，URL值集（开发人员补充值集名称）
	 */
	public Long getAcceptanceTypeId() {
		return acceptanceTypeId;
	}

	public void setAcceptanceTypeId(Long acceptanceTypeId) {
		this.acceptanceTypeId = acceptanceTypeId;
	}

	/**
	 * @return 验收状态，独立值集（开发人员补充值集名称）
	 */
	public String getAcceptanceStatusCode() {
		return acceptanceStatusCode;
	}

	public void setAcceptanceStatusCode(String acceptanceStatusCode) {
		this.acceptanceStatusCode = acceptanceStatusCode;
	}

	/**
	 * @return 负责人，URL值集（开发人员补充值集名称），人员值列表
	 */
	public Long getPrincipalPersonId() {
		return principalPersonId;
	}

	public void setPrincipalPersonId(Long principalPersonId) {
		this.principalPersonId = principalPersonId;
	}

	/**
	 * @return 验收单编号
	 */
	public String getAcceptanceNum() {
		return acceptanceNum;
	}

	public void setAcceptanceNum(String acceptanceNum) {
		this.acceptanceNum = acceptanceNum;
	}

	/**
	 * @return 标题概述
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return 提交日期
	 */
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	/**
	 * @return 完成日期
	 */
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	/**
	 * @return 申请部门，URL值集（开发人员补充值集名称）类型为部门的组织
	 */
	public Long getRequestDepartmentId() {
		return requestDepartmentId;
	}

	public void setRequestDepartmentId(Long requestDepartmentId) {
		this.requestDepartmentId = requestDepartmentId;
	}

	/**
	 * @return 采购部门，URL值集（开发人员补充值集名称）类型为部门的组织
	 */
	public Long getPurchaseDepartmentId() {
		return purchaseDepartmentId;
	}

	public void setPurchaseDepartmentId(Long purchaseDepartmentId) {
		this.purchaseDepartmentId = purchaseDepartmentId;
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

	public List<AcceptanceRelation> getAcceptanceRelationList() {
		return acceptanceRelationList;
	}

	public void setAcceptanceRelationList(List<AcceptanceRelation> acceptanceRelationList) {
		this.acceptanceRelationList = acceptanceRelationList;
	}

	public List<AcceptanceLine> getAcceptanceLineList() {
		return acceptanceLineList;
	}

	public void setAcceptanceLineList(List<AcceptanceLine> acceptanceLineList) {
		this.acceptanceLineList = acceptanceLineList;
	}

	public String getAcceptanceTypeMeaning() {
		return acceptanceTypeMeaning;
	}

	public void setAcceptanceTypeMeaning(String acceptanceTypeMeaning) {
		this.acceptanceTypeMeaning = acceptanceTypeMeaning;
	}

	public String getAcceptanceStatusMeaning() {
		return acceptanceStatusMeaning;
	}

	public void setAcceptanceStatusMeaning(String acceptanceStatusMeaning) {
		this.acceptanceStatusMeaning = acceptanceStatusMeaning;
	}

	public String getPrincipalPersonMeaning() {
		return principalPersonMeaning;
	}

	public void setPrincipalPersonMeaning(String principalPersonMeaning) {
		this.principalPersonMeaning = principalPersonMeaning;
	}

	public String getRequestDepartmentMeaning() {
		return requestDepartmentMeaning;
	}

	public void setRequestDepartmentMeaning(String requestDepartmentMeaning) {
		this.requestDepartmentMeaning = requestDepartmentMeaning;
	}

	public String getPurchaseDepartmentMeaning() {
		return purchaseDepartmentMeaning;
	}

	public void setPurchaseDepartmentMeaning(String purchaseDepartmentMeaning) {
		this.purchaseDepartmentMeaning = purchaseDepartmentMeaning;
	}

	public List<AcceptanceAsset> getAcceptanceAssetList() {
		return acceptanceAssetList;
	}

	public void setAcceptanceAssetList(List<AcceptanceAsset> acceptanceAssetList) {
		this.acceptanceAssetList = acceptanceAssetList;
	}

	public String getAcceptanceTypeCode() {
		return acceptanceTypeCode;
	}

	public void setAcceptanceTypeCode(String acceptanceTypeCode) {
		this.acceptanceTypeCode = acceptanceTypeCode;
	}
}
