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
 * 资产事务处理类型扩展控制字段
 *
 * @author qing.huang@hand-china.com 2019-03-19 19:42:58
 */
@ApiModel("资产事务处理类型扩展控制字段")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_transaction_type_fields")
public class TransactionTypeFields extends AuditDomain {

    public static final String FIELD_FIELD_ID = "fieldId";
    public static final String FIELD_TRANSATION_TYPE_ID = "transationTypeId";
    public static final String FIELD_FIELD_COLUMN = "fieldColumn";
    public static final String FIELD_FIELD_TYPE = "fieldType";
    public static final String FIELD_TENANT_ID = "tenantId";

    public static final String SAVE_TYPE_INSERT = "insert";
    public static final String SAVE_TYPE_UPDATE = "update";

    //分类
    /**
     * 涉及基本信息变更分类
     */
    public static final String CLASS_BASIC_ASSET_COLUMN = "BASIC";
    /**
     * 涉及属性描述变更分类
     */
    public static final String CLASS_ATTRIBUTE_COLUMN = "ATTRIBUTE";
    /**
     * 涉及跟踪与管理变更分类
     */
    public static final String CLASS_TRACKING_AND_MANAGEMENT_COLUMN = "TRACK";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long fieldId;
    @ApiModelProperty(value = "事务处理类型ID")
    @NotNull
    private Long transationTypeId;
    @ApiModelProperty(value = "字段")
    @NotNull
    private String fieldColumn;
    @ApiModelProperty(value = "字段名meaning")
    @Transient
    private String fieldColumnMeaning;
    @ApiModelProperty(value = "字段分类")
    @Transient
    private String fieldClass;
    @ApiModelProperty(value = "类型,独立值集：AAFM.ASSET_COLUMN_PROPERTY")
    @LovValue(value = "AAFM.ASSET_COLUMN_PROPERTY", meaningField = "fieldTypeMeaning")
    @NotBlank
    private String fieldType;
    @ApiModelProperty(value = "类型meaning")
    @Transient
    private String fieldTypeMeaning;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;


    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    @ApiModelProperty(value = "描述名称")
    @Transient
    private String descCode;
    @ApiModelProperty(value = "描述来源类型")
    @Transient
    private String descSourceType;
    @ApiModelProperty(value = "描述来源")
    @Transient
    private String descSource;
    @ApiModelProperty(value = "取值类型")
    @Transient
    private String lovType;
    @ApiModelProperty(value = "值集")
    @Transient
    private String lovName;


    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return 事务处理类型ID
     */
    public Long getTransationTypeId() {
        return transationTypeId;
    }

    public void setTransationTypeId(Long transationTypeId) {
        this.transationTypeId = transationTypeId;
    }


    /**
     * @return 类型
     */
    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
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

    public String getFieldTypeMeaning() {
        return fieldTypeMeaning;
    }

    public void setFieldTypeMeaning(String fieldTypeMeaning) {
        this.fieldTypeMeaning = fieldTypeMeaning;
    }

    public String getFieldColumn() {
        return fieldColumn;
    }

    public void setFieldColumn(String fieldColumn) {
        this.fieldColumn = fieldColumn;
    }

    public String getFieldColumnMeaning() {
        return fieldColumnMeaning;
    }

    public void setFieldColumnMeaning(String fieldColumnMeaning) {
        this.fieldColumnMeaning = fieldColumnMeaning;
    }

    public String getFieldClass() {
        return fieldClass;
    }

    public void setFieldClass(String fieldClass) {
        this.fieldClass = fieldClass;
    }

    public String getDescCode() {
        return descCode;
    }

    public void setDescCode(String descCode) {
        this.descCode = descCode;
    }

    public String getDescSourceType() {
        return descSourceType;
    }

    public void setDescSourceType(String descSourceType) {
        this.descSourceType = descSourceType;
    }

    public String getDescSource() {
        return descSource;
    }

    public void setDescSource(String descSource) {
        this.descSource = descSource;
    }

    public String getLovType() {
        return lovType;
    }

    public void setLovType(String lovType) {
        this.lovType = lovType;
    }

    public String getLovName() {
        return lovName;
    }

    public void setLovName(String lovName) {
        this.lovName = lovName;
    }
}
