package org.hzero.halm.atn.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.infra.annotation.FieldMessage;
import org.hzero.halm.atn.infra.constant.AatnConstans;
import org.hzero.halm.atn.infra.constant.TransactionLine;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 资产报废单行
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:10
 */
@ApiModel("资产报废单行")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_scrap_order_line")
public class ScrapOrderLine extends TransactionLine {

	public static final String FIELD_SCRAP_LINE_ID = "scrapLineId";
	public static final String FIELD_SCRAP_HEADER_ID = "scrapHeaderId";
	public static final String FIELD_LINE_NUM = "lineNum";
	public static final String FIELD_ASSET_ID = "assetId";
	public static final String FIELD_PROCESS_STATUS = "processStatus";
	public static final String FIELD_CURRENT_ASSET_STATUS_ID = "currentAssetStatusId";
	public static final String FIELD_TARGET_ASSET_STATUS_ID = "targetAssetStatusId";
	public static final String FIELD_CURRENT_LOCATION_ID = "currentLocationId";
	public static final String FIELD_TARGET_LOCATION_ID = "targetLocationId";
	public static final String FIELD_ORIGINAL_COST = "originalCost";
	public static final String FIELD_REMAIN_COST = "remainCost";
	public static final String FIELD_CAPITALIZATION_DATE = "capitalizationDate";
	public static final String FIELD_REMAIN_DEPRECIATION_MOUTH = "remainDepreciationMouth";
	public static final String FIELD_SCRAP_TYPE_CODE = "scrapTypeCode";
	public static final String FIELD_DISPOSE_TYPE_CODE = "disposeTypeCode";
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
	private Long scrapLineId;
	@ApiModelProperty(value = "报废单头ID")
	private Long scrapHeaderId;
	@ApiModelProperty(value = "事务处理行号")
	private Integer lineNum;
	@ApiModelProperty(value = "设备/资产ID")
	@NotNull
	private Long assetId;
	@ApiModelProperty(value = "处理状态，值集")
	@NotBlank
	@LovValue(lovCode = "AATN.SCRAP_ORDER_LINE_STATUS")
	private String processStatus;
	@ApiModelProperty(value = "当前资产状态ID")
	@NotNull
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID, des = AatnConstans.FieldMessageDes.CURRENT)
	private Long currentAssetStatusId;

	@ApiModelProperty(value = "目标资产状态ID")
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID, des = AatnConstans.FieldMessageDes.TARGET)
	private Long targetAssetStatusId;

	@ApiModelProperty(value = "当前位置")
	@NotNull
	@FieldMessage(tag = Asset.FIELD_ASSET_LOCATION_ID, des = AatnConstans.FieldMessageDes.CURRENT)
	private Long currentLocationId;

	@ApiModelProperty(value = "目标位置")
	@FieldMessage(tag = Asset.FIELD_ASSET_LOCATION_ID, des = AatnConstans.FieldMessageDes.TARGET)
	private Long targetLocationId;

	@ApiModelProperty(value = "资产原值")
	private Long originalCost;
	@ApiModelProperty(value = "剩余价值")
	@NotNull
	private Long remainCost;
	@ExcelColumn(zh = "资本化日期", en = "Capitalization date", pattern = BaseConstants.Pattern.DATE)
	@JsonFormat(pattern = BaseConstants.Pattern.DATE)
	private Date capitalizationDate;
	@ApiModelProperty(value = "剩余折旧日期")
	@NotNull
	private Long remainDepreciationMouth;
	@ApiModelProperty(value = "资产报废类型")
	@LovValue(lovCode = "AATN.ASSET_SCRAP_TYPE", meaningField = "scrapTypeCodeMeaning")
	private String scrapTypeCode;
	@ApiModelProperty(value = "资产处置类型")
	@LovValue(lovCode = "AATN.DISPOSE_TYPE", meaningField = "disposeTypeCodeMeaning")
	private String disposeTypeCode;
	@ApiModelProperty(value = "描述")
	@NotBlank
	private String description;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "租户ID")
	private Long tenantId;

	//
	// 非数据库字段
	// ------------------------------------------------------------------------------
	@Transient
	private String assetNum;
	@Transient
	private String assetDesc;
	@Transient
	private String processStatusMeaning;
	@Transient
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID, des = AatnConstans.FieldMessageDes.CURRENT_MEANING)
	private String currentAssetStatus;
	@Transient
	@FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID, des = AatnConstans.FieldMessageDes.TARGET_MEANING)
	private String targetAssetStatus;
	@Transient
	@FieldMessage(tag = Asset.FIELD_ASSET_LOCATION_ID, des = AatnConstans.FieldMessageDes.CURRENT_MEANING)
	private String currentLocationName;
	@Transient
	@FieldMessage(tag = Asset.FIELD_ASSET_LOCATION_ID, des = AatnConstans.FieldMessageDes.TARGET_MEANING)
	private String targetLocationName;
	@Transient
	private String assetName;
	/**
	 * 过程中资产状态
	 */
	@Transient
	private String inprocessAssetStatus;
	@Transient
	private Long inprocessAssetStatusId;
	@Transient
	private Long objectVersionNumber;
	@Transient
	private String lineNumDisplay;
	@Transient
	private String scrapNum;
	@Transient
	private String scrapTypeCodeMeaning;
	@Transient
	private String disposeTypeCodeMeaning;
	//
	// getter/setter
	// ------------------------------------------------------------------------------

	public String getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
	}

	public String getAssetDesc() {
		return assetDesc;
	}

	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}

	public String getProcessStatusMeaning() {
		return processStatusMeaning;
	}

	public void setProcessStatusMeaning(String processStatusMeaning) {
		this.processStatusMeaning = processStatusMeaning;
	}

	public String getInprocessAssetStatus() {
		return inprocessAssetStatus;
	}

	public void setInprocessAssetStatus(String inprocessAssetStatus) {
		this.inprocessAssetStatus = inprocessAssetStatus;
	}

	public Long getInprocessAssetStatusId() {
		return inprocessAssetStatusId;
	}

	public void setInprocessAssetStatusId(Long inprocessAssetStatusId) {
		this.inprocessAssetStatusId = inprocessAssetStatusId;
	}

	/**
	 * @return 表ID，主键，供其他表做外键
	 */
	public Long getScrapLineId() {
		return scrapLineId;
	}

	public void setScrapLineId(Long scrapLineId) {
		this.scrapLineId = scrapLineId;
	}

	/**
	 * @return 报废单头ID
	 */
	public Long getScrapHeaderId() {
		return scrapHeaderId;
	}

	public void setScrapHeaderId(Long scrapHeaderId) {
		this.scrapHeaderId = scrapHeaderId;
	}

	/**
	 * @return 事务处理行号
	 */
	public Integer getLineNum() {
		return lineNum;
	}

	public void setLineNum(Integer lineNum) {
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
	 * @return 当前位置
	 */
	public Long getCurrentLocationId() {
		return currentLocationId;
	}

	public void setCurrentLocationId(Long currentLocationId) {
		this.currentLocationId = currentLocationId;
	}

	/**
	 * @return 目标位置
	 */
	public Long getTargetLocationId() {
		return targetLocationId;
	}

	public void setTargetLocationId(Long targetLocationId) {
		this.targetLocationId = targetLocationId;
	}

	/**
	 * @return 资产原值
	 */
	public Long getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(Long originalCost) {
		this.originalCost = originalCost;
	}

	/**
	 * @return 剩余价值
	 */
	public Long getRemainCost() {
		return remainCost;
	}

	public void setRemainCost(Long remainCost) {
		this.remainCost = remainCost;
	}

	/**
	 * @return 资本化日期
	 */
	public Date getCapitalizationDate() {
		return capitalizationDate;
	}

	public void setCapitalizationDate(Date capitalizationDate) {
		this.capitalizationDate = capitalizationDate;
	}

	/**
	 * @return 剩余折旧日期
	 */
	public Long getRemainDepreciationMouth() {
		return remainDepreciationMouth;
	}

	public void setRemainDepreciationMouth(Long remainDepreciationMouth) {
		this.remainDepreciationMouth = remainDepreciationMouth;
	}

	/**
	 * @return 资产报废类型
	 */
	public String getScrapTypeCode() {
		return scrapTypeCode;
	}

	public void setScrapTypeCode(String scrapTypeCode) {
		this.scrapTypeCode = scrapTypeCode;
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

	public String getCurrentAssetStatus() {
		return currentAssetStatus;
	}

	public void setCurrentAssetStatus(String currentAssetStatus) {
		this.currentAssetStatus = currentAssetStatus;
	}

	public String getTargetAssetStatus() {
		return targetAssetStatus;
	}

	public void setTargetAssetStatus(String targetAssetStatus) {
		this.targetAssetStatus = targetAssetStatus;
	}

	public String getCurrentLocationName() {
		return currentLocationName;
	}

	public void setCurrentLocationName(String currentLocationName) {
		this.currentLocationName = currentLocationName;
	}

	public String getTargetLocationName() {
		return targetLocationName;
	}

	public void setTargetLocationName(String targetLocationName) {
		this.targetLocationName = targetLocationName;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public Long getObjectVersionNumber() {
		return objectVersionNumber;
	}

	@Override
	public void setObjectVersionNumber(Long objectVersionNumber) {
		this.objectVersionNumber = objectVersionNumber;
	}

	public String getLineNumDisplay() {
		return lineNumDisplay;
	}

	public void setLineNumDisplay(String lineNumDisplay) {
		this.lineNumDisplay = lineNumDisplay;
	}

	public String getScrapNum() {
		return scrapNum;
	}

	public void setScrapNum(String scrapNum) {
		this.scrapNum = scrapNum;
	}

	public String getScrapTypeCodeMeaning() {
		return scrapTypeCodeMeaning;
	}

	public void setScrapTypeCodeMeaning(String scrapTypeCodeMeaning) {
		this.scrapTypeCodeMeaning = scrapTypeCodeMeaning;
	}

	public String getDisposeTypeCodeMeaning() {
		return disposeTypeCodeMeaning;
	}

	public void setDisposeTypeCodeMeaning(String disposeTypeCodeMeaning) {
		this.disposeTypeCodeMeaning = disposeTypeCodeMeaning;
	}
}
