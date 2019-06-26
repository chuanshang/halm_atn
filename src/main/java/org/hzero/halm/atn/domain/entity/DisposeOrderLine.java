package org.hzero.halm.atn.domain.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.infra.annotation.FieldMessage;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.constant.TransactionLine;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 资产处置单行
 *
 * @author zhiguang.guo@hand-china.com 2019-03-25 15:48:13
 */
@ApiModel("资产处置单行")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_dispose_order_line")
public class DisposeOrderLine extends TransactionLine {

    public static final String FIELD_DISPOSE_LINE_ID = "disposeLineId";
    public static final String FIELD_DISPOSE_HEADER_ID = "disposeHeaderId";
    public static final String FIELD_ASSET_ID = "assetId";
    public static final String FIELD_PROCESS_STATUS = "processStatus";
    public static final String FIELD_CURRENT_ASSET_STATUS_ID = "currentAssetStatusId";
    public static final String FIELD_TARGET_ASSET_STATUS_ID = "targetAssetStatusId";
    public static final String FIELD_DISPOSE_TYPE_CODE = "disposeTypeCode";
    public static final String FIELD_DISPOSE_PERSON_ID = "disposePersonId";
    public static final String FIELD_DISPOSE_PRICE = "disposePrice";
    public static final String FIELD_DISPOSE_COST = "disposeCost";
    public static final String FIELD_DISPOSE_RATE = "disposeRate";
    public static final String FIELD_DISPOSE_INCOME = "disposeIncome";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_REMARK = "remark";
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
    private Long disposeLineId;
    @ApiModelProperty(value = "处置单头ID")
    private Long disposeHeaderId;
    @ApiModelProperty(value = "设备/资产ID")
    @NotNull
    private Long assetId;
	@ApiModelProperty(value = "资产信息变更行编号")
	private Integer lineNum;
    @ApiModelProperty(value = "处理状态，值集")
    @NotBlank
	@LovValue(lovCode = "AATN.DISPOSE_LINE_STATUS")
    private String processStatus;

	@ApiModelProperty(value = "当前资产状态")
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.CURRENT)
	private Long currentAssetStatusId;

	@ApiModelProperty(value = "目标资产状态")
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private Long targetAssetStatusId;

    @ApiModelProperty(value = "资产处置类型")
	@LovValue(value = "AATN.DISPOSE_TYPE", meaningField = "disposeTypeCodeMeaning")
    private String disposeTypeCode;
    @ApiModelProperty(value = "处置人员")
    private Long disposePersonId;
    @ApiModelProperty(value = "处置价格")
    private BigDecimal disposePrice;
    @ApiModelProperty(value = "处置成本")
    private BigDecimal disposeCost;
    @ApiModelProperty(value = "处置税率")
    private Long disposeRate;
    @ApiModelProperty(value = "处置收益")
    private BigDecimal disposeIncome;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------
	@Transient
	private String assetNum;
	@Transient
	private String assetName;
	@Transient
	private String assetDesc;
	@Transient
	private String nameRuleCode;
	@Transient
	private String processStatusMeaning;
	@Transient
	private String disposeTypeCodeMeaning;
	@Transient
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.CURRENT_MEANING)
	private String currentAssetStatusName;
	@Transient
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.CURRENT_MEANING)
	private String targetAssetStatusName;
	@Transient
	private String disposePersonName;
	/**
	 * 查询行明细，带出头的资产处置单编号
	 */
	@Transient
	private String disposeNum;
	@Transient
	private String lineNumber;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getDisposeLineId() {
		return disposeLineId;
	}

	public void setDisposeLineId(Long disposeLineId) {
		this.disposeLineId = disposeLineId;
	}
    /**
     * @return 处置单头ID
     */
	public Long getDisposeHeaderId() {
		return disposeHeaderId;
	}

	public void setDisposeHeaderId(Long disposeHeaderId) {
		this.disposeHeaderId = disposeHeaderId;
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
     * @return 资产处置类型
     */
	public String getDisposeTypeCode() {
		return disposeTypeCode;
	}

	public void setDisposeTypeCode(String disposeTypeCode) {
		this.disposeTypeCode = disposeTypeCode;
	}
    /**
     * @return 处置人员
     */
	public Long getDisposePersonId() {
		return disposePersonId;
	}

	public void setDisposePersonId(Long disposePersonId) {
		this.disposePersonId = disposePersonId;
	}

	public BigDecimal getDisposePrice() {
		return disposePrice;
	}

	public void setDisposePrice(BigDecimal disposePrice) {
		this.disposePrice = disposePrice;
	}

	public BigDecimal getDisposeCost() {
		return disposeCost;
	}

	public void setDisposeCost(BigDecimal disposeCost) {
		this.disposeCost = disposeCost;
	}

	public BigDecimal getDisposeIncome() {
		return disposeIncome;
	}

	public void setDisposeIncome(BigDecimal disposeIncome) {
		this.disposeIncome = disposeIncome;
	}

	/**
     * @return 处置税率
     */
	public Long getDisposeRate() {
		return disposeRate;
	}

	public void setDisposeRate(Long disposeRate) {
		this.disposeRate = disposeRate;
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
     * @return 备注
     */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetDesc() {
		return assetDesc;
	}

	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}

	public String getNameRuleCode() {
		return nameRuleCode;
	}

	public void setNameRuleCode(String nameRuleCode) {
		this.nameRuleCode = nameRuleCode;
	}

	public String getProcessStatusMeaning() {
		return processStatusMeaning;
	}

	public void setProcessStatusMeaning(String processStatusMeaning) {
		this.processStatusMeaning = processStatusMeaning;
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

	public String getDisposePersonName() {
		return disposePersonName;
	}

	public void setDisposePersonName(String disposePersonName) {
		this.disposePersonName = disposePersonName;
	}

	public String getDisposeNum() {
		return disposeNum;
	}

	public void setDisposeNum(String disposeNum) {
		this.disposeNum = disposeNum;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Integer getLineNum() {
		return lineNum;
	}

	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}

	public String getDisposeTypeCodeMeaning() {
		return disposeTypeCodeMeaning;
	}

	public void setDisposeTypeCodeMeaning(String disposeTypeCodeMeaning) {
		this.disposeTypeCodeMeaning = disposeTypeCodeMeaning;
	}
}
