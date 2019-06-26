package org.hzero.halm.afm.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import io.choerodon.core.exception.CommonException;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.halm.afm.domain.repository.AttributeLineRepository;
import org.hzero.halm.afm.infra.constant.Constants;

/**
 * 属性组行信息
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:23
 */
@ApiModel("属性组行信息")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_attribute_line")
public class AttributeLine extends AuditDomain {

    public static final String FIELD_LINE_ID = "lineId";
    public static final String FIELD_ATTRIBUTE_SET_ID = "attributeSetId";
    public static final String FIELD_LINE_NUM = "lineNum";
    public static final String FIELD_ATTRIBUTE_NAME = "attributeName";
    public static final String FIELD_REQUIRED_FLAG = "requiredFlag";
    public static final String FIELD_ATTRIBUTE_TYPE = "attributeType";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_UOM_ID = "uomId";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_LOV_VALUE = "lovValue";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    public void validateLine(Long tenantId, Long attributeSetId, AttributeLineRepository attributeLineRepository) {
        validLovValue();
        validUniqueIndex(tenantId, attributeSetId, attributeLineRepository);
        validUniqueLineNum(tenantId, attributeSetId, attributeLineRepository);
    }

    /**
     * 属性名唯一索引校验
     *
     * @param tenantId       租户ID
     * @param attributeSetId 属性组ID
     */
    public void validUniqueIndex(Long tenantId, Long attributeSetId, AttributeLineRepository attributeLineRepository) {
        if (attributeLineRepository.selectCountByCondition(
                org.hzero.mybatis.domian.Condition.builder(AttributeLine.class)
                        .andWhere(
                                org.hzero.mybatis.util.Sqls.custom()
                                        .andEqualTo(AttributeLine.FIELD_ATTRIBUTE_SET_ID, attributeSetId)
                                        .andEqualTo(AttributeLine.FIELD_TENANT_ID, tenantId)
                                        .andEqualTo(AttributeLine.FIELD_ATTRIBUTE_NAME, this.attributeName)
                                        .andNotEqualTo(AttributeLine.FIELD_LINE_ID, this.lineId, true)
                        ).build()) != 0) {
            throw new CommonException(Constants.AafmErrorCode.ATTRIBUTE_LINE_NAME_DUPLICATED, this.attributeName);
        }
    }

    /**
     * 属性行唯一索引校验
     *
     * @param tenantId       租户ID
     * @param attributeSetId 属性组ID
     */
    public void validUniqueLineNum(Long tenantId, Long attributeSetId, AttributeLineRepository attributeLineRepository) {
        if (attributeLineRepository.selectCountByCondition(
                org.hzero.mybatis.domian.Condition.builder(AttributeLine.class)
                        .andWhere(
                                org.hzero.mybatis.util.Sqls.custom()
                                        .andEqualTo(AttributeLine.FIELD_ATTRIBUTE_SET_ID, attributeSetId)
                                        .andEqualTo(AttributeLine.FIELD_TENANT_ID, tenantId)
                                        .andEqualTo(AttributeLine.FIELD_LINE_NUM, this.lineNum)
                                        .andNotEqualTo(AttributeLine.FIELD_LINE_ID, this.lineId, true)
                        ).build()) != 0) {
            throw new CommonException(Constants.AafmErrorCode.ATTRIBUTE_LINE_NUMBER_DUPLICATED);
        }
    }

    /**
     * 当attribute_type为值集时，该字段必输
     */
    public void validLovValue() {
        if ((this.attributeType == "ValueList" || this.attributeType == "Lov")
                && (this.lovValue == "" || this.lovValue == null)) {
            throw new CommonException(Constants.AafmErrorCode.ATTRIBUTE_LINE_LOV_VALUE_IS_REQUIRED);
        }
    }
    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long lineId;
    @ApiModelProperty(value = "属性组头ID")
    private Long attributeSetId;
    @ApiModelProperty(value = "编号，必输，同一属性组下唯一")
    @NotNull
    private Long lineNum;
    @ApiModelProperty(value = "属性名,必输，同一属性组下唯一")
    @Length(max = 30)
    @NotBlank
    private String attributeName;
    @ApiModelProperty(value = "是否必须，必输，默认：1")
    @Range(max = 1)
    @NotNull
    private Integer requiredFlag;
    @ApiModelProperty(value = "字段类型, 必输，值集：AAFM.FIELD_TYPE", required = false)
    @LovValue(value = "AAFM.FIELD_TYPE", meaningField = "attributeTypeMeaning")
    @Length(max = 30)
    @NotBlank
    private String attributeType;
    @ApiModelProperty("字段类型meaning")
    @Transient
    private String attributeTypeMeaning;
    @ApiModelProperty(value = "启用标识，必输，默认：1", required = false)
    @Range(max = 1)
    @NotNull
    private Integer enabledFlag;
    @ApiModelProperty(value = "单位ID，必输，值集：AMDM.UOM")
    private Long uomId;
    @ApiModelProperty("单位名称")
    @Transient
    private String uomName;
    @ApiModelProperty(value = "描述")
    @Length(max = 120)
    private String description;
    @ApiModelProperty(value = "值集编码，当attribute_type为值集时，该字段必输")
    @Length(max = 30)
    private String lovValue;
    @ApiModelProperty(value = "租户ID", required = false)
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
    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    /**
     * @return 属性组头ID
     */
    public Long getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(Long attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    /**
     * @return 编号，必输，同一属性组下唯一
     */
    public Long getLineNum() {
        return lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * @return 属性名, 必输，同一属性组下唯一
     */
    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * @return 是否必须，必输，默认：1
     */
    public Integer getRequiredFlag() {
        return requiredFlag;
    }

    public void setRequiredFlag(Integer requiredFlag) {
        this.requiredFlag = requiredFlag;
    }

    /**
     * @return 字段类型, 必输，值集：AAFM.FIELD_TYPE
     */
    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    /**
     * @return 启用标识，必输，默认：1
     */
    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    /**
     * @return 单位ID，必输，值集：AMDM.UOM
     */
    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
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

    public String getLovValue() {
        return lovValue;
    }

    public void setLovValue(String lovValue) {
        this.lovValue = lovValue;
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

    public String getAttributeTypeMeaning() {
        return attributeTypeMeaning;
    }

    public void setAttributeTypeMeaning(String attributeTypeMeaning) {
        this.attributeTypeMeaning = attributeTypeMeaning;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

}
