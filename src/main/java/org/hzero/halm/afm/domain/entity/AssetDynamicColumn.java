package org.hzero.halm.afm.domain.entity;

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

/**
 * 资产动态字段配置
 *
 * @author wen.luo@hand-china.com 2019-04-02 16:06:20
 */
@ApiModel("资产动态字段配置")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_asset_dynamic_column")
public class AssetDynamicColumn extends AuditDomain {

    public static final String FIELD_COLUMN_ID = "columnId";
    public static final String FIELD_COLUMN_CODE = "columnCode";
    public static final String FIELD_COLUMN_NAME = "columnName";
    public static final String FIELD_COLUMN_CLASS = "columnClass";
    public static final String FIELD_DESC_CODE = "descCode";
    public static final String FIELD_DESC_SOURCE_TYPE = "descSourceType";
    public static final String FIELD_DESC_SOURCE = "descSource";
    public static final String FIELD_LOV_NAME = "lovName";
    public static final String FIELD_LOV_TYPE = "lovType";
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
    private Long columnId;
    @ApiModelProperty(value = "字段，来自INFORMATION_SCHEMA.COLUMNS")
    @NotBlank
    private String columnCode;
    @ApiModelProperty(value = "字段名称，作为动态字段的显示名称")
    @NotBlank
    private String columnName;
    @ApiModelProperty(value = "分类，必输，独立值集（BASIC/基本信息、TRACK/跟踪信息）")
	@LovValue(value = "AAFM.COLUMN_CLASS", meaningField = "columnClassMeaning")
    @NotBlank
    private String columnClass;
    @ApiModelProperty(value = "描述名称")
    @NotBlank
    private String descCode;
    @ApiModelProperty(value = "描述来源类型，独立值集（TABLE/表、IDP/独立值集）")
	@LovValue(value = "AAFM.DESC_SOURCE_TYPE", meaningField = "descSourceTypeMeaning")
    @NotBlank
    private String descSourceType;
    @ApiModelProperty(value = "描述来源，填写表名或者值集名")
    @NotBlank
    private String descSource;
    @ApiModelProperty(value = "值集，必输，SQL值集，来自HZERO_PLATFORM.HPFM_LOV")
    @NotBlank
    private String lovName;
    @ApiModelProperty(value = "取值类型，独立值集（SQL、IDP、URL【待前端补充】）")
	@LovValue(value = "AAFM.LOV_TYPE", meaningField = "lovTypeMeaning")
    @NotBlank
    private String lovType;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

	@ApiModelProperty(value = "描述来源类型描述")
	@Transient
	private String descSourceTypeMeaning;

	@ApiModelProperty(value = "分类描述")
	@Transient
	private String columnClassMeaning;

	@ApiModelProperty(value = "取值类型描述")
	@Transient
	private String lovTypeMeaning;
    //
    // getter/setter
    // ------------------------------------------------------------------------------


	public String getDescSourceTypeMeaning() {
		return descSourceTypeMeaning;
	}

	public void setDescSourceTypeMeaning(String descSourceTypeMeaning) {
		this.descSourceTypeMeaning = descSourceTypeMeaning;
	}

	public String getColumnClassMeaning() {
		return columnClassMeaning;
	}

	public void setColumnClassMeaning(String columnClassMeaning) {
		this.columnClassMeaning = columnClassMeaning;
	}

	public String getLovTypeMeaning() {
		return lovTypeMeaning;
	}

	public void setLovTypeMeaning(String lovTypeMeaning) {
		this.lovTypeMeaning = lovTypeMeaning;
	}

	/**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
    /**
     * @return 字段，来自INFORMATION_SCHEMA.COLUMNS
     */
	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
    /**
     * @return 字段名称，作为动态字段的显示名称
     */
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
    /**
     * @return 分类，必输，独立值集（BASIC/基本信息、TRACK/跟踪信息）
     */
	public String getColumnClass() {
		return columnClass;
	}

	public void setColumnClass(String columnClass) {
		this.columnClass = columnClass;
	}
    /**
     * @return 描述名称
     */
	public String getDescCode() {
		return descCode;
	}

	public void setDescCode(String descCode) {
		this.descCode = descCode;
	}
    /**
     * @return 描述来源类型，独立值集（TABLE/表、IDP/独立值集）
     */
	public String getDescSourceType() {
		return descSourceType;
	}

	public void setDescSourceType(String descSourceType) {
		this.descSourceType = descSourceType;
	}
    /**
     * @return 描述来源，填写表名或者值集名
     */
	public String getDescSource() {
		return descSource;
	}

	public void setDescSource(String descSource) {
		this.descSource = descSource;
	}
    /**
     * @return 值集，必输，SQL值集，来自HZERO_PLATFORM.HPFM_LOV
     */
	public String getLovName() {
		return lovName;
	}

	public void setLovName(String lovName) {
		this.lovName = lovName;
	}
    /**
     * @return 取值类型，独立值集（SQL、IDP、URL【待前端补充】）
     */
	public String getLovType() {
		return lovType;
	}

	public void setLovType(String lovType) {
		this.lovType = lovType;
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
