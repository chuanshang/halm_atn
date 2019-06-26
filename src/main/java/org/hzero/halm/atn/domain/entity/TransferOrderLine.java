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
 * 调拨转移单行
 *
 * @author like.zhang@hand-china.com 2019-03-20 14:56:33
 */
@ApiModel("调拨转移单行")
@VersionAudit
@ModifyAudit
@Table(name = "aatn_transfer_order_line")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferOrderLine extends TransactionLine {

    public static final String FIELD_TRANSFER_LINE_ID = "transferLineId";
    public static final String FIELD_TRANSFER_HEADER_ID = "transferHeaderId";
    public static final String FIELD_ASSET_ID = "assetId";
    public static final String FIELD_PROCESS_STATUS = "processStatus";
    public static final String FIELD_CURRENT_ASSET_STATUS_ID = "currentAssetStatusId";
    public static final String FIELD_TARGET_ASSET_STATUS_ID = "targetAssetStatusId";
    public static final String FIELD_CURRENT_OWNING_ORG_ID = "currentOwningOrg";
    public static final String FIELD_TARGET_OWNING_ORG_ID = "targetOwningOrg";
    public static final String FIELD_CURRENT_COST_CENTER = "currentCostCenter";
    public static final String FIELD_TARGET_COST_CENTER = "targetCostCenter";
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
    private Long transferLineId;
    @ApiModelProperty(value = "调拨单头ID")
    private Long transferHeaderId;
    @ApiModelProperty(value = "设备/资产ID")
    @NotNull
    private Long assetId;
    @ApiModelProperty(value = "处理状态，值集AATN.TRANSFER_ORDER_LINE_STATUS")
    @Length(max = 30)
    @LovValue(lovCode = "AATN.TRANSFER_ORDER_LINE_STATUS")
    private String processStatus;

    @ApiModelProperty(value = "当前资产状态")
    @FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.CURRENT)
    private Long currentAssetStatusId;

    @ApiModelProperty(value = "目标资产状态")
    @FieldMessage(tag = Asset.FIELD_ASSET_STATUS_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private Long targetAssetStatusId;

    @ApiModelProperty(value = "当前所属组织")
    @FieldMessage(tag = Asset.FIELD_OWNING_ORG_ID,des = AatnConstans.FieldMessageDes.CURRENT)
    private Long currentOwningOrg;

    @ApiModelProperty(value = "目标所属组织")
    @FieldMessage(tag = Asset.FIELD_OWNING_ORG_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private Long targetOwningOrg;

    @ApiModelProperty(value = "当前成本中心")
    @Length(max = 30)
    @FieldMessage(tag = Asset.FIELD_COST_CENTER_ID,des = AatnConstans.FieldMessageDes.CURRENT)
    private String currentCostCenter;

    @ApiModelProperty(value = "目标成本中心")
    @Length(max = 30)
    @FieldMessage(tag = Asset.FIELD_COST_CENTER_ID,des = AatnConstans.FieldMessageDes.TARGET)
    private String targetCostCenter;
    @ApiModelProperty(value = "备注")
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
    @FieldMessage(tag = Asset.FIELD_OWNING_ORG_ID,des = AatnConstans.FieldMessageDes.CURRENT_MEANING)
    private String currentOwningOrgName;
    @Transient
    @FieldMessage(tag = Asset.FIELD_OWNING_ORG_ID,des = AatnConstans.FieldMessageDes.TARGET_MEANING)
    private String targetOwningOrgName;
    @Transient
    private String transferNum;
    @Transient
    private Long transactionTypeId;


    /**
     * 过程中资产状态
     */
    @Transient
    private String inprocessAssetStatus;
    @Transient
    private Long inprocessAssetStatusId;

    //
    // getter/setter
    // ------------------------------------------------------------------------------


    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(String transferNum) {
        this.transferNum = transferNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCurrentOwningOrgName() {
        return currentOwningOrgName;
    }

    public void setCurrentOwningOrgName(String currentOwningOrgName) {
        this.currentOwningOrgName = currentOwningOrgName;
    }

    public String getTargetOwningOrgName() {
        return targetOwningOrgName;
    }

    public void setTargetOwningOrgName(String targetOwningOrgName) {
        this.targetOwningOrgName = targetOwningOrgName;
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

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getTransferLineId() {
		return transferLineId;
	}

	public void setTransferLineId(Long transferLineId) {
		this.transferLineId = transferLineId;
	}
    /**
     * @return 调拨单头ID
     */
	public Long getTransferHeaderId() {
		return transferHeaderId;
	}

	public void setTransferHeaderId(Long transferHeaderId) {
		this.transferHeaderId = transferHeaderId;
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
     * @return 当前所属组织
     */
	public Long getCurrentOwningOrg() {
		return currentOwningOrg;
	}

	public void setCurrentOwningOrg(Long currentOwningOrg) {
		this.currentOwningOrg = currentOwningOrg;
	}
    /**
     * @return 目标所属组织
     */
	public Long getTargetOwningOrg() {
		return targetOwningOrg;
	}

	public void setTargetOwningOrg(Long targetOwningOrg) {
		this.targetOwningOrg = targetOwningOrg;
	}
    /**
     * @return 当前成本中心
     */
	public String getCurrentCostCenter() {
		return currentCostCenter;
	}

	public void setCurrentCostCenter(String currentCostCenter) {
		this.currentCostCenter = currentCostCenter;
	}
    /**
     * @return 目标成本中心
     */
	public String getTargetCostCenter() {
		return targetCostCenter;
	}

	public void setTargetCostCenter(String targetCostCenter) {
		this.targetCostCenter = targetCostCenter;
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

}
