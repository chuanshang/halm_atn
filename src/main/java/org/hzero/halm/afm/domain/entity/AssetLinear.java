package org.hzero.halm.afm.domain.entity;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 资产-线性属性
 *
 * @author zhisheng.zhang@hand-china.com 2019-01-24 16:43:37
 */
@ApiModel("资产-线性属性")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_asset_linear")
public class AssetLinear extends AuditDomain {

    public static final String FIELD_LINEAR_ID = "linearId";
    public static final String FIELD_LINEAR_NUM = "linearNum";
    public static final String FIELD_ASSET_ID = "assetId";
    public static final String FIELD_LINEAR_NAME = "linearName";
    public static final String FIELD_LINEAR_START_MEASURE = "linearStartMeasure";
    public static final String FIELD_LINEAR_END_MEASURE = "linearEndMeasure";
    public static final String FIELD_LINEAR_START_DESC = "linearStartDesc";
    public static final String FIELD_LINEAR_END_DESC = "linearEndDesc";
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
    private Long linearId;
    @ApiModelProperty(value = "资产编号")
	@NotNull
    private Long assetId;
   @ApiModelProperty(value = "长度")    
    private Long linearName;
   @ApiModelProperty(value = "开始端(A)计量位，必输，保留9位小数")    
    private Long linearStartMeasure;
   @ApiModelProperty(value = "结束端(B)端计量位，必输，保留10位小数")    
    private Long linearEndMeasure;
   @ApiModelProperty(value = "开始端(A)描述")    
    private String linearStartDesc;
   @ApiModelProperty(value = "开始端(A)描述")    
    private String linearEndDesc;
   @ApiModelProperty(value = "描述")    
    private String description;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getLinearId() {
		return linearId;
	}

	public void setLinearId(Long linearId) {
		this.linearId = linearId;
	}
    /**
     * @return 资产编号
     */
	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
    /**
     * @return 长度
     */
	public Long getLinearName() {
		return linearName;
	}

	public void setLinearName(Long linearName) {
		this.linearName = linearName;
	}
    /**
     * @return 开始端(A)计量位，必输，保留9位小数
     */
	public Long getLinearStartMeasure() {
		return linearStartMeasure;
	}

	public void setLinearStartMeasure(Long linearStartMeasure) {
		this.linearStartMeasure = linearStartMeasure;
	}
    /**
     * @return 结束端(B)端计量位，必输，保留10位小数
     */
	public Long getLinearEndMeasure() {
		return linearEndMeasure;
	}

	public void setLinearEndMeasure(Long linearEndMeasure) {
		this.linearEndMeasure = linearEndMeasure;
	}
    /**
     * @return 开始端(A)描述
     */
	public String getLinearStartDesc() {
		return linearStartDesc;
	}

	public void setLinearStartDesc(String linearStartDesc) {
		this.linearStartDesc = linearStartDesc;
	}
    /**
     * @return 开始端(A)描述
     */
	public String getLinearEndDesc() {
		return linearEndDesc;
	}

	public void setLinearEndDesc(String linearEndDesc) {
		this.linearEndDesc = linearEndDesc;
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
