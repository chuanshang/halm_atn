package org.hzero.halm.rcv.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;

import java.math.BigDecimal;

/**
 * 验收单资产明细
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@ApiModel("验收单资产明细")
@VersionAudit
@ModifyAudit
@Table(name = "arcv_acceptance_asset")
public class AcceptanceAsset extends AuditDomain {

    public static final String FIELD_ACCEPTANCE_ASSET_ID = "acceptanceAssetId";
    public static final String FIELD_ACCEPTANCE_LINE_ID = "acceptanceLineId";
    public static final String FIELD_ASSET_NUM = "assetNum";
    public static final String FIELD_ASSET_CLASS_ID = "assetClassId";
    public static final String FIELD_ASSETS_SET_ID = "assetsSetId";
    public static final String FIELD_SUPPLIER_HEADER_ID = "supplierHeaderId";
    public static final String FIELD_BRAND = "brand";
    public static final String FIELD_MODEL = "model";
    public static final String FIELD_ASSET_STATUS_ID = "assetStatusId";
    public static final String FIELD_ASSET_LOCATION_ID = "assetLocationId";
    public static final String FIELD_OWNING_ORG_ID = "owningOrgId";
    public static final String FIELD_USING_ORG_ID = "usingOrgId";
    public static final String FIELD_OWNING_PERSON_ID = "owningPersonId";
    public static final String FIELD_USER_PERSON_ID = "userPersonId";
    public static final String FIELD_COST_CENTER_ID = "costCenterId";
    public static final String FIELD_ORIGINAL_COST = "originalCost";
    public static final String FIELD_PARENT_ASSET_ID = "parentAssetId";
    public static final String FIELD_TRANSFER_FIXED_CODE = "transferFixedCode";
    public static final String FIELD_COMPLETE_FLAG = "completeFlag";
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
    private Long acceptanceAssetId;
    @ApiModelProperty(value = "验收单行id")
    private Long acceptanceLineId;
    @ApiModelProperty(value = "资产编号")
    @NotBlank
    private String assetNum;
    @ApiModelProperty(value = "产品类别/资产分类，值集：AMDM.ASSET_CLASS")
    @NotNull
    private Long assetClassId;
    @ApiModelProperty(value = "产品组/资产组，值集：AMDM_ASSET_SET")
    @NotNull
    private Long assetsSetId;
   @ApiModelProperty(value = "供应商id")    
    private Long supplierHeaderId;
   @ApiModelProperty(value = "品牌/厂商")    
    private String brand;
   @ApiModelProperty(value = "规格型号")    
    private String model;
   @ApiModelProperty(value = "资产状态，值集：AAFM.ASSET_STATUS")    
    private Long assetStatusId;
   @ApiModelProperty(value = "资产位置，值集：AAFM.ASSET_LOCATION")    
    private Long assetLocationId;
   @ApiModelProperty(value = "所属组织，值集：AAFM.ASSET_ORG")    
    private Long owningOrgId;
   @ApiModelProperty(value = "使用组织，值集：AAFM.ASSET_ORG")    
    private Long usingOrgId;
   @ApiModelProperty(value = "资产管理员，值集：HALM.EMPLOYEE")
    private Long owningPersonId;
   @ApiModelProperty(value = "使用人，值集：HALM.EMPLOYEE")
    private Long userPersonId;
   @ApiModelProperty(value = "成本中心，值集：AAFM.ASSET_COST_CENTER")    
    private Long costCenterId;
   @ApiModelProperty(value = "获取价格")    
    private BigDecimal originalCost;
   @ApiModelProperty(value = "父资产，值集：AAFM.ASSET")
    private Long parentAssetId;
    @LovValue(lovCode = "ARCV.TRANSFER_FIXED",meaningField = "transferFixedMeaning")
    @ApiModelProperty(value = "是否转固，独立值集（ARCV.TRANSFER_FIXED）")
    @NotBlank
    private String transferFixedCode;
    @ApiModelProperty(value = "资产信息是否完整")
    @NotNull
	@LovValue(lovCode = "HPFM.FLAG",meaningField = "completeFlagMeaning")
    private Integer completeFlag;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------
	/**
	 * 产品类别/资产分类 Meaning
	 */
	@Transient
	private String assetClassMeaning;
	/**
	 * 资产组Meaning
	 */
	@Transient
	private String assetsSetMeaning;
	/**
	 * 供应商Meaning
	 */
	@Transient
	private String supplierHeaderMeaning;
	/**
	 * 资产状态Meaning
	 */
	@Transient
	private String assetStatusMeaning;
	/**
	 * 资产位置Meaning
	 */
	@Transient
	private String assetLocationMeaning;
	/**
	 * 所属组织Meaning
	 */
	@Transient
	private String owningOrgMeaning;
	/**
	 * 使用组织Meaning
	 */
	@Transient
	private String usingOrgMeaning;
	/**
	 * 资产管理员Meaning
	 */
	@Transient
	private String owningPersonMeaning;
	/**
	 * 使用人Meaning
	 */
	@Transient
	private String userPersonMeaning;
	/**
	 * 成本中心Meaning
	 */
	@Transient
	private String costCenterMeaning;
	/**
	 * 父资产Meaning
	 */
	@Transient
	private String parentAssetMeaning;
	/**
	 * 转固Meaning
	 */
	@Transient
	private String transferFixedMeaning;
	/**
	 * 资产信息是否完整Meaning
	 */
	@Transient
	private String completeFlagMeaning;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getAcceptanceAssetId() {
		return acceptanceAssetId;
	}

	public void setAcceptanceAssetId(Long acceptanceAssetId) {
		this.acceptanceAssetId = acceptanceAssetId;
	}
    /**
     * @return 验收单行id
     */
	public Long getAcceptanceLineId() {
		return acceptanceLineId;
	}

	public void setAcceptanceLineId(Long acceptanceLineId) {
		this.acceptanceLineId = acceptanceLineId;
	}
    /**
     * @return 资产编号
     */
	public String getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
	}
    /**
     * @return 产品类别/资产分类，值集：AMDM.ASSET_CLASS
     */
	public Long getAssetClassId() {
		return assetClassId;
	}

	public void setAssetClassId(Long assetClassId) {
		this.assetClassId = assetClassId;
	}
    /**
     * @return 产品组/资产组，值集：AMDM_ASSET_SET
     */
	public Long getAssetsSetId() {
		return assetsSetId;
	}

	public void setAssetsSetId(Long assetsSetId) {
		this.assetsSetId = assetsSetId;
	}
    /**
     * @return 供应商id
     */
	public Long getSupplierHeaderId() {
		return supplierHeaderId;
	}

	public void setSupplierHeaderId(Long supplierHeaderId) {
		this.supplierHeaderId = supplierHeaderId;
	}
    /**
     * @return 品牌/厂商
     */
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
    /**
     * @return 规格型号
     */
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
    /**
     * @return 资产状态，值集：AAFM.ASSET_STATUS
     */
	public Long getAssetStatusId() {
		return assetStatusId;
	}

	public void setAssetStatusId(Long assetStatusId) {
		this.assetStatusId = assetStatusId;
	}
    /**
     * @return 资产位置，值集：AAFM.ASSET_LOCATION
     */
	public Long getAssetLocationId() {
		return assetLocationId;
	}

	public void setAssetLocationId(Long assetLocationId) {
		this.assetLocationId = assetLocationId;
	}
    /**
     * @return 所属组织，值集：AAFM.ASSET_ORG
     */
	public Long getOwningOrgId() {
		return owningOrgId;
	}

	public void setOwningOrgId(Long owningOrgId) {
		this.owningOrgId = owningOrgId;
	}
    /**
     * @return 使用组织，值集：AAFM.ASSET_ORG
     */
	public Long getUsingOrgId() {
		return usingOrgId;
	}

	public void setUsingOrgId(Long usingOrgId) {
		this.usingOrgId = usingOrgId;
	}
    /**
     * @return 资产管理员，值集：AAFM.ASSET_MANAGER
     */
	public Long getOwningPersonId() {
		return owningPersonId;
	}

	public void setOwningPersonId(Long owningPersonId) {
		this.owningPersonId = owningPersonId;
	}
    /**
     * @return 使用人，值集：AAFM.ASSET_USER
     */
	public Long getUserPersonId() {
		return userPersonId;
	}

	public void setUserPersonId(Long userPersonId) {
		this.userPersonId = userPersonId;
	}
    /**
     * @return 成本中心，值集：AAFM.ASSET_COST_CENTER
     */
	public Long getCostCenterId() {
		return costCenterId;
	}

	public void setCostCenterId(Long costCenterId) {
		this.costCenterId = costCenterId;
	}
    /**
     * @return 获取价格
     */
	public BigDecimal getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(BigDecimal originalCost) {
		this.originalCost = originalCost;
	}
    /**
     * @return 父资产，值集：AAFM.ASSET
     */
	public Long getParentAssetId() {
		return parentAssetId;
	}

	public void setParentAssetId(Long parentAssetId) {
		this.parentAssetId = parentAssetId;
	}
    /**
     * @return 是否转固，独立值集（开发人员补充值集名称）
     */
	public String getTransferFixedCode() {
		return transferFixedCode;
	}

	public void setTransferFixedCode(String transferFixedCode) {
		this.transferFixedCode = transferFixedCode;
	}
    /**
     * @return 资产信息是否完整
     */
	public Integer getCompleteFlag() {
		return completeFlag;
	}

	public void setCompleteFlag(Integer completeFlag) {
		this.completeFlag = completeFlag;
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

	public String getAssetClassMeaning() {
		return assetClassMeaning;
	}

	public void setAssetClassMeaning(String assetClassMeaning) {
		this.assetClassMeaning = assetClassMeaning;
	}

	public String getAssetsSetMeaning() {
		return assetsSetMeaning;
	}

	public void setAssetsSetMeaning(String assetsSetMeaning) {
		this.assetsSetMeaning = assetsSetMeaning;
	}

	public String getSupplierHeaderMeaning() {
		return supplierHeaderMeaning;
	}

	public void setSupplierHeaderMeaning(String supplierHeaderMeaning) {
		this.supplierHeaderMeaning = supplierHeaderMeaning;
	}

	public String getAssetStatusMeaning() {
		return assetStatusMeaning;
	}

	public void setAssetStatusMeaning(String assetStatusMeaning) {
		this.assetStatusMeaning = assetStatusMeaning;
	}

	public String getAssetLocationMeaning() {
		return assetLocationMeaning;
	}

	public void setAssetLocationMeaning(String assetLocationMeaning) {
		this.assetLocationMeaning = assetLocationMeaning;
	}

	public String getOwningOrgMeaning() {
		return owningOrgMeaning;
	}

	public void setOwningOrgMeaning(String owningOrgMeaning) {
		this.owningOrgMeaning = owningOrgMeaning;
	}

	public String getUsingOrgMeaning() {
		return usingOrgMeaning;
	}

	public void setUsingOrgMeaning(String usingOrgMeaning) {
		this.usingOrgMeaning = usingOrgMeaning;
	}

	public String getOwningPersonMeaning() {
		return owningPersonMeaning;
	}

	public void setOwningPersonMeaning(String owningPersonMeaning) {
		this.owningPersonMeaning = owningPersonMeaning;
	}

	public String getUserPersonMeaning() {
		return userPersonMeaning;
	}

	public void setUserPersonMeaning(String userPersonMeaning) {
		this.userPersonMeaning = userPersonMeaning;
	}

	public String getCostCenterMeaning() {
		return costCenterMeaning;
	}

	public void setCostCenterMeaning(String costCenterMeaning) {
		this.costCenterMeaning = costCenterMeaning;
	}

	public String getParentAssetMeaning() {
		return parentAssetMeaning;
	}

	public void setParentAssetMeaning(String parentAssetMeaning) {
		this.parentAssetMeaning = parentAssetMeaning;
	}

	public String getTransferFixedMeaning() {
		return transferFixedMeaning;
	}

	public void setTransferFixedMeaning(String transferFixedMeaning) {
		this.transferFixedMeaning = transferFixedMeaning;
	}

	public String getCompleteFlagMeaning() {
		return completeFlagMeaning;
	}

	public void setCompleteFlagMeaning(String completeFlagMeaning) {
		this.completeFlagMeaning = completeFlagMeaning;
	}
}
