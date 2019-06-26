package org.hzero.halm.atn.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
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

/**
 * 资产信息变更单行
 *
 * @author like.zhang@hand-china.com 2019-03-26 11:49:44
 */
@ApiModel("资产信息变更单行")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_change_order_line")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeOrderLine extends TransactionLine {

    public static final String FIELD_CHANGE_LINE_ID = "changeLineId";
    public static final String FIELD_CHANGE_HEADER_ID = "changeHeaderId";
    public static final String FIELD_ASSET_ID = "assetId";
    public static final String FIELD_PROCESS_STATUS = "processStatus";
    public static final String FIELD_CURRENT_ASSET_STATUS_ID = "currentAssetStatusId";
    public static final String FIELD_TARGET_ASSET_STATUS_ID = "targetAssetStatusId";
    public static final String FIELD_CURRENT_LOCATION_ID = "currentLocationId";
    public static final String FIELD_TARGET_LOCATION_ID = "targetLocationId";
    public static final String FIELD_CURRENT_OWNING_PERSON_ID = "currentOwningPersonId";
    public static final String FIELD_TARGET_OWNING_PERSON_ID = "targetOwningPersonId";
    public static final String FIELD_CURRENT_USING_PERSON_ID = "currentUsingPersonId";
    public static final String FIELD_TARGET_USING_PERSON_ID = "targetUsingPersonId";
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
    private Long changeLineId;
    @ApiModelProperty(value = "处置单头ID")
    private Long changeHeaderId;
    @ApiModelProperty(value = "设备/资产ID")
    @NotNull
    private Long assetId;
    @ApiModelProperty(value = "资产信息变更行编号")
    private Integer lineNum;
    @ApiModelProperty(value = "处理状态，值集")
    @Length(max = 30)
    @LovValue(lovCode = "AATN.CHANGE_ORDER_LINE_STATUS")
    private String processStatus;
    @ApiModelProperty(value = "当前资产状态")
    @FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.CURRENT)
    private Long currentAssetStatusId;

    @ApiModelProperty(value = "目标资产状态")
    @FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private Long targetAssetStatusId;

    @ApiModelProperty(value = "当前位置")
    @FieldMessage(tag = Asset.FIELD_ASSET_LOCATION_ID,des = AatnConstans.FieldMessageDes.CURRENT)
    private Long currentLocationId;

    @ApiModelProperty(value = "目标位置")
    @FieldMessage(tag = Asset.FIELD_ASSET_LOCATION_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private Long targetLocationId;

    @ApiModelProperty(value = "当前所属人")
    @FieldMessage(tag = Asset.FIELD_OWNING_PERSON_ID,des = AatnConstans.FieldMessageDes.CURRENT)
    private Long currentOwningPersonId;

    @ApiModelProperty(value = "目标所属人")
    @FieldMessage(tag = Asset.FIELD_OWNING_PERSON_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private Long targetOwningPersonId;

    @ApiModelProperty(value = "当前使用人")
    @FieldMessage(tag = Asset.FIELD_USER_PERSON_ID,des = AatnConstans.FieldMessageDes.CURRENT)
    private Long currentUsingPersonId;

    @ApiModelProperty(value = "目标使用人")
    @FieldMessage(tag = Asset.FIELD_USER_PERSON_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private Long targetUsingPersonId;

    @ApiModelProperty(value = "描述")
    @Length(max = 240)
    private String description;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    @Transient
    private String assetNum;
    @Transient
    private String assetDesc;
    @Transient
    private String name;
    @Transient
    private String processStatusMeaning;
    @Transient
    @FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.CURRENT_MEANING)
    private String currentAssetStatusName;
    @Transient
    @FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.TARGET_MEANING)
    private String targetAssetStatusName;
    @Transient
    @FieldMessage(tag = Asset.FIELD_ASSET_LOCATION_ID,des = AatnConstans.FieldMessageDes.CURRENT_MEANING)
    private String currentLocationName;
    @Transient
    @FieldMessage(tag = Asset.FIELD_ASSET_LOCATION_ID,des = AatnConstans.FieldMessageDes.TARGET_MEANING)
    private String targetLocationName;
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
    /**
     * 需要前端特殊传入
     */
    @Transient
    private Long transactionTypeId;
    @Transient
    private String changeNum;


    @Transient
    private String lineNumber;

    //
    // getter/setter
    // ------------------------------------------------------------------------------


    public String getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(String changeNum) {
        this.changeNum = changeNum;
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

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }


    public Long getTargetUsingPersonId() {
        return targetUsingPersonId;
    }

    public void setTargetUsingPersonId(Long targetUsingPersonId) {
        this.targetUsingPersonId = targetUsingPersonId;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getChangeLineId() {
		return changeLineId;
	}

	public void setChangeLineId(Long changeLineId) {
		this.changeLineId = changeLineId;
	}
    /**
     * @return 处置单头ID
     */
	public Long getChangeHeaderId() {
		return changeHeaderId;
	}

	public void setChangeHeaderId(Long changeHeaderId) {
		this.changeHeaderId = changeHeaderId;
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

}
