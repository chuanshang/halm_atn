package org.hzero.halm.afm.domain.vo;

import io.choerodon.mybatis.domain.AuditDomain;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

import java.util.List;

/**
 * description
 * 资产专业分类VO
 * @author 51276 2019/02/22 16:56
 */
@ExcelSheet(zh = "资产专业分类信息", en = "Asset Specialty Info")
public class AssetSpecialtyDetailExportVO extends AuditDomain {

    private Long assetSpecialtyId;
    @ExcelColumn(zh = "专业分类名称",en = "asset specialty name")
    private String assetSpecialtyName;
    @LovValue(lovCode = "HPFM.FLAG",meaningField = "enabledFlagMeaning")
    private Integer enabledFlag;
    @ExcelColumn(zh = "启用标识",en = "enabled flag")
    private String enabledFlagMeaning;
    @ExcelColumn(zh = "描述",en = "description")
    private String description;
    @ExcelColumn(zh = "组织分配信息",en = "asset to org info",child = true)
    private List<AssetToOrgDetailExportVO> assetToOrgDetailExportVOList;

    public Long getAssetSpecialtyId() {
        return assetSpecialtyId;
    }

    public void setAssetSpecialtyId(Long assetSpecialtyId) {
        this.assetSpecialtyId = assetSpecialtyId;
    }

    public String getAssetSpecialtyName() {
        return assetSpecialtyName;
    }

    public void setAssetSpecialtyName(String assetSpecialtyName) {
        this.assetSpecialtyName = assetSpecialtyName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AssetToOrgDetailExportVO> getAssetToOrgDetailExportVOList() {
        return assetToOrgDetailExportVOList;
    }

    public void setAssetToOrgDetailExportVOList(List<AssetToOrgDetailExportVO> assetToOrgDetailExportVOList) {
        this.assetToOrgDetailExportVOList = assetToOrgDetailExportVOList;
    }
}
