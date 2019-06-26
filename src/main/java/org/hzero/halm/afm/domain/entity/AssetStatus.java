package org.hzero.halm.afm.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.choerodon.core.exception.CommonException;
import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.halm.afm.domain.repository.AssetStatusRepository;

/**
 * 资产状态
 *
 * @author zhisheng.zhang@hand-china.com 2019-02-20 15:56:29
 */
@ApiModel("资产状态")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_asset_status")
public class AssetStatus extends AuditDomain {

	public static final String FIELD_ASSET_STATUS_ID = "assetStatusId";
	public static final String FIELD_SYS_STATUS_NAME = "sysStatusName";
	public static final String FIELD_USER_STATUS_NAME = "userStatusName";
	public static final String FIELD_INITIAL_STATUS_FLAG = "initialStatusFlag";
	public static final String FIELD_MAINTAIN_FLAG = "maintainFlag";
	public static final String FIELD_TRANSACTION_FLAG = "transactionFlag";
	public static final String FIELD_EDIT_BASIC_FLAG = "editBasicFlag";
	public static final String FIELD_EDIT_SOURCE_FLAG = "editSourceFlag";
	public static final String FIELD_EDIT_MANAGE_FLAG = "editManageFlag";
	public static final String FIELD_EDIT_ATTRIBUTE_FLAG = "editAttributeFlag";
	public static final String FIELD_EDIT_WARRANTY_FLAG = "editWarrantyFlag";
	public static final String FIELD_PM_START_TRIGGER_FLAG = "pmStartTriggerFlag";
	public static final String FIELD_PM_STOP_TRIGGER_FLAG = "pmStopTriggerFlag";
	public static final String FIELD_NEXT_STATUS = "nextStatus";
	public static final String FIELD_ENABLED_FLAG = "enabledFlag";
	public static final String FIELD_ASSET_STATUS_CODE = "assetStatusCode";
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
	private Long assetStatusId;
	@ApiModelProperty(value = "系统状态名称，租户初始化时系统自动创建，不可修改")
	@NotBlank
	private String sysStatusName;
	@ApiModelProperty(value = "用户自定义名称，默认同系统状态名，租户可修改，不重名")
	@NotBlank
	private String userStatusName;
	@ApiModelProperty(value = "新增时可用")
	@LovValue(value = "HPFM.FLAG", meaningField = "initialStatusFlagMeaning")
	private Integer initialStatusFlag;
	@ApiModelProperty(value = "可维护")
	@LovValue(value = "HPFM.FLAG", meaningField = "maintainFlagMeaning")
	private Integer maintainFlag;
	@ApiModelProperty(value = "可事务处理")
	@LovValue(value = "HPFM.FLAG", meaningField = "transactionFlagMeaning")
	private Integer transactionFlag;
	@ApiModelProperty(value = "可编辑基本信息")
	@LovValue(value = "HPFM.FLAG", meaningField = "editBasicFlagMeaning")
	private Integer editBasicFlag;
	@ApiModelProperty(value = "可编辑来源信息")
	@LovValue(value = "HPFM.FLAG", meaningField = "editSourceFlagMeaning")
	private Integer editSourceFlag;
	@ApiModelProperty(value = "可编辑管理信息")
	@LovValue(value = "HPFM.FLAG", meaningField = "editManageFlagMeaning")
	private Integer editManageFlag;
	@ApiModelProperty(value = "可编辑属性信息")
	@LovValue(value = "HPFM.FLAG", meaningField = "editAttributeFlagMeaning")
	private Integer editAttributeFlag;
	@ApiModelProperty(value = "可编辑质保信息")
	@LovValue(value = "HPFM.FLAG", meaningField = "editWarrantyFlagMeaning")
	private Integer editWarrantyFlag;
	@ApiModelProperty(value = "触发预防性维护开启")
	@LovValue(value = "HPFM.FLAG", meaningField = "pmStartTriggerFlagMeaning")
	private Integer pmStartTriggerFlag;
	@ApiModelProperty(value = "触发预防性维护停止")
	@LovValue(value = "HPFM.FLAG", meaningField = "pmStopTriggerFlagMeaning")
	private Integer pmStopTriggerFlag;
	@ApiModelProperty(value = "后续状态")
	private String nextStatus;
	@ApiModelProperty(value = "")
	@NotNull
	private Integer enabledFlag;
	@ApiModelProperty(value = "系统状态编码")
	@NotNull
	private String assetStatusCode;
	@ApiModelProperty(value = "")
	@NotNull
	private Long tenantId;

	//
	// 非数据库字段
	// ------------------------------------------------------------------------------

	@ApiModelProperty(value = "新增时可用meaning")
	@Transient
	private String initialStatusFlagMeaning;
	@ApiModelProperty(value = "可编辑基本信息meaning")
	@Transient
	private String editBasicFlagMeaning;
	@ApiModelProperty(value = "可维护meaning")
	@Transient
	private String maintainFlagMeaning;
	@ApiModelProperty(value = "可事务处理meaning")
	@Transient
	private String transactionFlagMeaning;
	@ApiModelProperty(value = "可编辑来源信息meaning")
	@Transient
	private String editSourceFlagMeaning;
	@ApiModelProperty(value = "可编辑管理信息meaning")
	@Transient
	private String editManageFlagMeaning;
	@ApiModelProperty(value = "可编辑属性信息meaning")
	@Transient
	private String editAttributeFlagMeaning;
	@ApiModelProperty(value = "可编辑质保信息meaning")
	@Transient
	private String editWarrantyFlagMeaning;
	@ApiModelProperty(value = "触发预防性维护开启meaning")
	@Transient
	private String pmStartTriggerFlagMeaning;
	@ApiModelProperty(value = "触发预防性维护停止meaning")
	@Transient
	private String pmStopTriggerFlagMeaning;
	//
	// getter/setter
	// ------------------------------------------------------------------------------

	/**
	 * @return 表ID，主键，供其他表做外键
	 */
	public Long getAssetStatusId() {
		return assetStatusId;
	}

	public void setAssetStatusId(Long assetStatusId) {
		this.assetStatusId = assetStatusId;
	}

	/**
	 * @return 系统状态名称，租户初始化时系统自动创建，不可修改
	 */
	public String getSysStatusName() {
		return sysStatusName;
	}

	public void setSysStatusName(String sysStatusName) {
		this.sysStatusName = sysStatusName;
	}

	/**
	 * @return 用户自定义名称，默认同系统状态名，租户可修改，不重名
	 */
	public String getuserStatusName() {
		return userStatusName;
	}

	public void setuserStatusName(String userStatusName) {
		this.userStatusName = userStatusName;
	}

	/**
	 * @return 新增时可用
	 */
	public Integer getInitialStatusFlag() {
		return initialStatusFlag;
	}

	public void setInitialStatusFlag(Integer initialStatusFlag) {
		this.initialStatusFlag = initialStatusFlag;
	}

	/**
	 * @return 可维护
	 */
	public Integer getMaintainFlag() {
		return maintainFlag;
	}

	public void setMaintainFlag(Integer maintainFlag) {
		this.maintainFlag = maintainFlag;
	}

	/**
	 * @return 可事务处理
	 */
	public Integer getTransactionFlag() {
		return transactionFlag;
	}

	public void setTransactionFlag(Integer transactionFlag) {
		this.transactionFlag = transactionFlag;
	}

	/**
	 * @return 可编辑基本信息
	 */
	public Integer getEditBasicFlag() {
		return editBasicFlag;
	}

	public void setEditBasicFlag(Integer editBasicFlag) {
		this.editBasicFlag = editBasicFlag;
	}

	/**
	 * @return 可编辑来源信息
	 */
	public Integer getEditSourceFlag() {
		return editSourceFlag;
	}

	public void setEditSourceFlag(Integer editSourceFlag) {
		this.editSourceFlag = editSourceFlag;
	}

	/**
	 * @return 可编辑管理信息
	 */
	public Integer getEditManageFlag() {
		return editManageFlag;
	}

	public void setEditManageFlag(Integer editManageFlag) {
		this.editManageFlag = editManageFlag;
	}

	/**
	 * @return 可编辑属性信息
	 */
	public Integer getEditAttributeFlag() {
		return editAttributeFlag;
	}

	public void setEditAttributeFlag(Integer editAttributeFlag) {
		this.editAttributeFlag = editAttributeFlag;
	}

	/**
	 * @return 可编辑质保信息
	 */
	public Integer getEditWarrantyFlag() {
		return editWarrantyFlag;
	}

	public void setEditWarrantyFlag(Integer editWarrantyFlag) {
		this.editWarrantyFlag = editWarrantyFlag;
	}

	/**
	 * @return 触发预防性维护开启
	 */
	public Integer getPmStartTriggerFlag() {
		return pmStartTriggerFlag;
	}

	public void setPmStartTriggerFlag(Integer pmStartTriggerFlag) {
		this.pmStartTriggerFlag = pmStartTriggerFlag;
	}

	/**
	 * @return 触发预防性维护停止
	 */
	public Integer getPmStopTriggerFlag() {
		return pmStopTriggerFlag;
	}

	public void setPmStopTriggerFlag(Integer pmStopTriggerFlag) {
		this.pmStopTriggerFlag = pmStopTriggerFlag;
	}

	/**
	 * @return
	 */
	public String getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}

	/**
	 * @return
	 */
	public Integer getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	/**
	 * @return
	 */
	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getInitialStatusFlagMeaning() {
		return initialStatusFlagMeaning;
	}

	public void setInitialStatusFlagMeaning(String initialStatusFlagMeaning) {
		this.initialStatusFlagMeaning = initialStatusFlagMeaning;
	}

	public String getMaintainFlagMeaning() {
		return maintainFlagMeaning;
	}

	public void setMaintainFlagMeaning(String maintainFlagMeaning) {
		this.maintainFlagMeaning = maintainFlagMeaning;
	}

	public String getTransactionFlagMeaning() {
		return transactionFlagMeaning;
	}

	public void setTransactionFlagMeaning(String transactionFlagMeaning) {
		this.transactionFlagMeaning = transactionFlagMeaning;
	}

	public String getEditBasicFlagMeaning() {
		return editBasicFlagMeaning;
	}

	public void setEditBasicFlagMeaning(String editBasicFlagMeaning) {
		this.editBasicFlagMeaning = editBasicFlagMeaning;
	}

	public String getEditSourceFlagMeaning() {
		return editSourceFlagMeaning;
	}

	public void setEditSourceFlagMeaning(String editSourceFlagMeaning) {
		this.editSourceFlagMeaning = editSourceFlagMeaning;
	}

	public String getEditManageFlagMeaning() {
		return editManageFlagMeaning;
	}

	public void setEditManageFlagMeaning(String editManageFlagMeaning) {
		this.editManageFlagMeaning = editManageFlagMeaning;
	}

	public String getEditAttributeFlagMeaning() {
		return editAttributeFlagMeaning;
	}

	public void setEditAttributeFlagMeaning(String editAttributeFlagMeaning) {
		this.editAttributeFlagMeaning = editAttributeFlagMeaning;
	}

	public String getEditWarrantyFlagMeaning() {
		return editWarrantyFlagMeaning;
	}

	public void setEditWarrantyFlagMeaning(String editWarrantyFlagMeaning) {
		this.editWarrantyFlagMeaning = editWarrantyFlagMeaning;
	}

	public String getPmStartTriggerFlagMeaning() {
		return pmStartTriggerFlagMeaning;
	}

	public void setPmStartTriggerFlagMeaning(String pmStartTriggerFlagMeaning) {
		this.pmStartTriggerFlagMeaning = pmStartTriggerFlagMeaning;
	}

	public String getPmStopTriggerFlagMeaning() {
		return pmStopTriggerFlagMeaning;
	}

	public void setPmStopTriggerFlagMeaning(String pmStopTriggerFlagMeaning) {
		this.pmStopTriggerFlagMeaning = pmStopTriggerFlagMeaning;
	}

	public String getAssetStatusCode() {
		return assetStatusCode;
	}

	public void setAssetStatusCode(String assetStatusCode) {
		this.assetStatusCode = assetStatusCode;
	}
}
