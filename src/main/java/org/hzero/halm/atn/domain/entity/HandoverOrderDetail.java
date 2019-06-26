package org.hzero.halm.atn.domain.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.infra.annotation.FieldMessage;
import org.hzero.halm.atn.infra.constant.AatnConstans;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 资产移交归还明细
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:11
 */
@ApiModel("资产移交归还明细")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_handover_order_detail")
public class HandoverOrderDetail extends AuditDomain {

    public static final String FIELD_HANDOVER_DETAIL_ID = "handoverDetailId";
    public static final String FIELD_HANDOVER_LINE_ID = "handoverLineId";
    public static final String FIELD_HANDOVER_TYPE_CODE = "handoverTypeCode";
    public static final String FIELD_CURRENT_ASSET_STATUS_ID = "currentAssetStatusId";
    public static final String FIELD_TARGET_ASSET_STATUS_ID = "targetAssetStatusId";
    public static final String FIELD_CURRENT_OWNING_PERSON_ID = "currentOwningPersonId";
    public static final String FIELD_TARGET_OWNING_PERSON_ID = "targetOwningPersonId";
    public static final String FIELD_CURRENT_USING_PERSON_ID = "currentUsingPersonId";
    public static final String FIELD_TARGET_USING_PERSON_ID = "targetUsingPersonId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

	/**
	 * 将行上的部分字段设置给行
	 * @param line line
	 * @return detail
	 */
	public HandoverOrderDetail setLineFields(HandoverOrderLine line){
		this.setProcessStatus(line.getProcessStatus());
		this.setAssetDesc(line.getAssetDesc());
		this.setProcessStatusMeaning(line.getProcessStatusMeaning());
		this.setName(line.getName());
		return this;
	}
    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long handoverDetailId;
    @ApiModelProperty(value = "资产移交归还单行ID")
    private Long handoverLineId;
    @ApiModelProperty(value = "移交确认/移交归还")
    @NotBlank
    private String handoverTypeCode;

    @ApiModelProperty(value = "当前资产状态")
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.CURRENT)
    private Long currentAssetStatusId;

    @ApiModelProperty(value = "目标资产状态")
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private Long targetAssetStatusId;

    @ApiModelProperty(value = "当前所属人")
	@FieldMessage(tag = Asset.FIELD_OWNING_PERSON_ID,des = AatnConstans.FieldMessageDes.CURRENT)
    private Long currentOwningPersonId;

    @ApiModelProperty(value = "目标所属人")
    @NotNull
	@FieldMessage(tag = Asset.FIELD_OWNING_PERSON_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private Long targetOwningPersonId;

    @ApiModelProperty(value = "当前使用人")
	@FieldMessage(tag = Asset.FIELD_USER_PERSON_ID,des = AatnConstans.FieldMessageDes.CURRENT)
    private Long currentUsingPersonId;

    @ApiModelProperty(value = "目标使用人")
    @NotNull
	@FieldMessage(tag = Asset.FIELD_USER_PERSON_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private Long targetUsingPersonId;
    @ApiModelProperty(value = "备注")
    private String description;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

	@Transient
	@FieldMessage(tag = Asset.FIELD_OWNING_PERSON_ID,des = AatnConstans.FieldMessageDes.CURRENT_MEANING)
	private String currentOwningPersonName;
	@Transient
	@FieldMessage(tag = Asset.FIELD_OWNING_PERSON_ID,des = AatnConstans.FieldMessageDes.TARGET_MEANING)
	private String targetOwningPersonName;
    @Transient
	@FieldMessage(tag = Asset.FIELD_USER_PERSON_ID,des = AatnConstans.FieldMessageDes.CURRENT_MEANING)
	private String currentUsingPersonName;
    @Transient
	@FieldMessage(tag = Asset.FIELD_USER_PERSON_ID,des = AatnConstans.FieldMessageDes.TARGET_MEANING)
	private String targetUsingPersonName;
    @Transient
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.CURRENT_MEANING)
	private String currentAssetStatusName;
    @Transient
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.TARGET_MEANING)
	private String targetAssetStatusName;

	// 行的资产全称
	@Transient
	private String assetDesc;
	// 行的可视标签
	@Transient
	private String name;
	// 行的处理状态
	@Transient
	private String processStatusMeaning;
	@Transient
	private String processStatus;
	@Transient
	private List<OrderDynamicColumn> dynamicColumnList;

    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getHandoverDetailId() {
		return handoverDetailId;
	}

	public void setHandoverDetailId(Long handoverDetailId) {
		this.handoverDetailId = handoverDetailId;
	}
    /**
     * @return 资产移交归还单行ID
     */
	public Long getHandoverLineId() {
		return handoverLineId;
	}

	public void setHandoverLineId(Long handoverLineId) {
		this.handoverLineId = handoverLineId;
	}
    /**
     * @return 移交确认/移交归还
     */
	public String getHandoverTypeCode() {
		return handoverTypeCode;
	}

	public void setHandoverTypeCode(String handoverTypeCode) {
		this.handoverTypeCode = handoverTypeCode;
	}
    /**
     * @return 当前资产状态
     */
	public Long getCurrentAssetStatusId() {
		return currentAssetStatusId;
	}

	public void setCurrentAssetStatusId(Long currentAssetStatusId) {
		this.currentAssetStatusId = currentAssetStatusId;
	}
    /**
     * @return 目标资产状态
     */
	public Long getTargetAssetStatusId() {
		return targetAssetStatusId;
	}

	public void setTargetAssetStatusId(Long targetAssetStatusId) {
		this.targetAssetStatusId = targetAssetStatusId;
	}
    /**
     * @return 当前所属人
     */
	public Long getCurrentOwningPersonId() {
		return currentOwningPersonId;
	}

	public void setCurrentOwningPersonId(Long currentOwningPersonId) {
		this.currentOwningPersonId = currentOwningPersonId;
	}
    /**
     * @return 目标所属人
     */
	public Long getTargetOwningPersonId() {
		return targetOwningPersonId;
	}

	public void setTargetOwningPersonId(Long targetOwningPersonId) {
		this.targetOwningPersonId = targetOwningPersonId;
	}
    /**
     * @return 当前使用人
     */
	public Long getCurrentUsingPersonId() {
		return currentUsingPersonId;
	}

	public void setCurrentUsingPersonId(Long currentUsingPersonId) {
		this.currentUsingPersonId = currentUsingPersonId;
	}
    /**
     * @return 目标使用人
     */
	public Long getTargetUsingPersonId() {
		return targetUsingPersonId;
	}

	public void setTargetUsingPersonId(Long targetUsingPersonId) {
		this.targetUsingPersonId = targetUsingPersonId;
	}

	/**
     * @return 备注
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

	public String getCurrentOwningPersonName() {
		return currentOwningPersonName;
	}

	public void setCurrentOwningPersonName(String currentOwningPersonName) {
		this.currentOwningPersonName = currentOwningPersonName;
	}

	public String getTargetOwningPersonName() {
		return targetOwningPersonName;
	}

	public void setTargetOwningPersonName(String targetOwningPersonName) {
		this.targetOwningPersonName = targetOwningPersonName;
	}

	public String getCurrentUsingPersonName() {
		return currentUsingPersonName;
	}

	public void setCurrentUsingPersonName(String currentUsingPersonName) {
		this.currentUsingPersonName = currentUsingPersonName;
	}

	public String getTargetUsingPersonName() {
		return targetUsingPersonName;
	}

	public void setTargetUsingPersonName(String targetUsingPersonName) {
		this.targetUsingPersonName = targetUsingPersonName;
	}

	public String getCurrentAssetStatusName() {
		return currentAssetStatusName;
	}

	public void setCurrentAssetStatusName(String currentAssetStatusName) {
		this.currentAssetStatusName = currentAssetStatusName;
	}

	public String getTargetAssetStatusName() {
		return targetAssetStatusName;
	}

	public void setTargetAssetStatusName(String targetAssetStatusName) {
		this.targetAssetStatusName = targetAssetStatusName;
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

	public String getProcessStatusMeaning() {
		return processStatusMeaning;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public List<OrderDynamicColumn> getDynamicColumnList() {
		return dynamicColumnList;
	}

	public void setDynamicColumnList(List<OrderDynamicColumn> dynamicColumnList) {
		this.dynamicColumnList = dynamicColumnList;
	}

	public void setProcessStatusMeaning(String processStatusMeaning) {
		this.processStatusMeaning = processStatusMeaning;
	}
}
