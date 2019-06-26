package org.hzero.halm.afm.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.domain.AuditDomain;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

/**
 * 送货单明细VO
 *
 * @author jiaxu.cui@hand-china.com 2018/12/7 17:25
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ExcelSheet(zh = "属性组行明细信息", en = "Attribute Line Details Info")
public class AttributeLineDetailExportVO extends AuditDomain {

    /**
     * 属性组行Id
     */
    private Long lineId;
    /**
     * 属性组Id
     */
    private Long attributeSetId;
    @ExcelColumn(zh = "编号", en = "line number")
    private Long lineNum;
    @ExcelColumn(zh = "属性名", en = "attribute name")
    private String attributeName;
    @LovValue(lovCode = "HPFM.FLAG", meaningField = "requiredFlagMeaning")
    private Integer requiredFlag;
    @ExcelColumn(zh = "是否必须", en = "Is Required")
    private String requiredFlagMeaning;
    @LovValue(lovCode = "AAFM.FIELD_TYPE", meaningField = "attributeTypeMeaning")
    private String attributeType;
    @ExcelColumn(zh = "字段类型", en = "Attribute Type")
    private String attributeTypeMeaning;
    @LovValue(lovCode = "HPFM.FLAG", meaningField = "enabledFlagMeaning")
    private Integer enabledFlag;
    @ExcelColumn(zh = "启用标识", en = "Enabled Flag")
    private String enabledFlagMeaning;
    @LovValue(lovCode = "AMDM.UOM", meaningField = "uomMeaning")
    private Long uomId;
    @ExcelColumn(zh = "单位", en = "UOM")
    private String uomMeaning;
    @ExcelColumn(zh = "描述", en = "description")
    private String description;
    @ExcelColumn(zh = "值集编码", en = "lovValue")
    private String lovValue;

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(Long attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    public Long getLineNum() {
        return lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Integer getRequiredFlag() {
        return requiredFlag;
    }

    public void setRequiredFlag(Integer requiredFlag) {
        this.requiredFlag = requiredFlag;
    }

    public String getRequiredFlagMeaning() {
        return requiredFlagMeaning;
    }

    public void setRequiredFlagMeaning(String requiredFlagMeaning) {
        this.requiredFlagMeaning = requiredFlagMeaning;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeTypeMeaning() {
        return attributeTypeMeaning;
    }

    public void setAttributeTypeMeaning(String attributeTypeMeaning) {
        this.attributeTypeMeaning = attributeTypeMeaning;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getEnabledFlagMeaning() {
        return enabledFlagMeaning;
    }

    public void setEnabledFlagMeaning(String enabledFlagMeaning) {
        this.enabledFlagMeaning = enabledFlagMeaning;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public String getUomMeaning() {
        return uomMeaning;
    }

    public void setUomMeaning(String uomMeaning) {
        this.uomMeaning = uomMeaning;
    }

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
}
