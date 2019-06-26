package org.hzero.halm.afm.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.mybatis.domain.AuditDomain;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

/**
 * description
 *
 * @author 51276 2019/02/22 17:15
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ExcelSheet(zh = "组织分配明细信息", en = "Asset To Org Details Info")
public class AssetToOrgDetailExportVO extends AuditDomain {


    private Long assetOrgId;
    private Long assetSpecialtyId;

    @ExcelColumn(zh = "服务区域",en = "maintsite name")
    private String maintSitesName;
    @ExcelColumn(zh = "专业归口部门",en = "major department name")
    private String majorDepartmentName;
    @ExcelColumn(zh = "管理部门",en = "manage department name")
    private String manageDepartmentName;
    @ExcelColumn(zh = "优先级",en = "priority")
    private Long priority;
    @LovValue(lovCode = "HPFM.FLAG",meaningField = "enabledFlagMeaning")
    private Integer enabledFlag;
    @ExcelColumn(zh = "启用标识",en = "enabled flag")
    private String enabledFlagMeaning;

    public Long getAssetOrgId() {
        return assetOrgId;
    }

    public void setAssetOrgId(Long assetOrgId) {
        this.assetOrgId = assetOrgId;
    }

    public Long getAssetSpecialtyId() {
        return assetSpecialtyId;
    }

    public void setAssetSpecialtyId(Long assetSpecialtyId) {
        this.assetSpecialtyId = assetSpecialtyId;
    }

    public String getMaintSitesName() {
        return maintSitesName;
    }

    public void setMaintSitesName(String maintSitesName) {
        this.maintSitesName = maintSitesName;
    }

    public String getMajorDepartmentName() {
        return majorDepartmentName;
    }

    public void setMajorDepartmentName(String majorDepartmentName) {
        this.majorDepartmentName = majorDepartmentName;
    }

    public String getManageDepartmentName() {
        return manageDepartmentName;
    }

    public void setManageDepartmentName(String manageDepartmentName) {
        this.manageDepartmentName = manageDepartmentName;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
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
}
