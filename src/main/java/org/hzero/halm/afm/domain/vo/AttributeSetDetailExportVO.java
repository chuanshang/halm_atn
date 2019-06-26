package org.hzero.halm.afm.domain.vo;

import io.choerodon.mybatis.domain.AuditDomain;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.halm.afm.domain.entity.AttributeLine;

import java.util.List;

/**
 * 送货单维护查询数据接收VO
 *
 * @author qing.huang@hand-china.com 2019/01/14 11:06
 */
@ExcelSheet(zh = "属性组信息", en = "attribute-set Info")
public class AttributeSetDetailExportVO extends AuditDomain {

    /**
     * 属性组Id
     */
    private Long attributeSetId;
    @ExcelColumn(zh = "名称", en = "attributeSet name")
    private String attributeSetName;
    @ExcelColumn(zh = "描述", en = "description")
    private String description;
    @LovValue(lovCode = "HPFM.FLAG", meaningField = "enabledFlagMeaning")
    private Integer enabledFlag;
    @ExcelColumn(zh = "启用标识", en = "enabled flag")
    private String enabledFlagMeaning;
    @ExcelColumn(zh = "属性组行信息", en = "attribute lines Info", child = true)
    private List<AttributeLineDetailExportVO> attributeLinesList;

    public Long getAttributeSetId() {
        return attributeSetId;
    }

    public void setAttributeSetId(Long attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    public String getAttributeSetName() {
        return attributeSetName;
    }

    public void setAttributeSetName(String attributeSetName) {
        this.attributeSetName = attributeSetName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<AttributeLineDetailExportVO> getAttributeLinesList() {
        return attributeLinesList;
    }

    public void setAttributeLinesList(List<AttributeLineDetailExportVO> attributeLinesList) {
        this.attributeLinesList = attributeLinesList;
    }
}
