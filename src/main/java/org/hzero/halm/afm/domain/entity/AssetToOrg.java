package org.hzero.halm.afm.domain.entity;

import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.halm.afm.domain.repository.AssetToOrgRepository;
import org.hzero.halm.afm.infra.constant.Constants;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 资产专业-组织分配(行)
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:39
 */
@ApiModel("资产专业-组织分配(行)")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_asset_to_org")
public class AssetToOrg extends AuditDomain {

    public static final String FIELD_ASSET_ORG_ID = "assetOrgId";
    public static final String FIELD_ASSET_SPECIALTY_ID = "assetSpecialtyId";
    public static final String FIELD_MAINT_SITES_ID = "maintSitesId";
    public static final String FIELD_MAJOR_DEPARTMENT_ID = "majorDepartmentId";
    public static final String FIELD_MANAGE_DEPARTMENT_ID = "manageDepartmentId";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_MAINT_SITES_NAME = "maintSitesName";
    public static final String FIELD_MAJOR_DEPARTMENT_NAME = "majorDepartmentName";
    public static final String FIELD_MANAGE_DEPARTMENT_NAME = "manageDepartmentName";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    public void validateAssetToOrg(Long tenantId, Long assetSpecialtyId, AssetToOrgRepository assetToOrgRepository) {
        validateUniquePriority(tenantId, assetSpecialtyId, assetToOrgRepository);
    }

    public void validateUniquePriority(Long tenantId, Long assetSpecialtyId,
                    AssetToOrgRepository assetToOrgRepository) {
        if (assetToOrgRepository.selectCountByCondition(org.hzero.mybatis.domian.Condition.builder(AssetToOrg.class)
                        .andWhere(org.hzero.mybatis.util.Sqls.custom().andEqualTo(AssetToOrg.FIELD_TENANT_ID, tenantId)
                                        .andEqualTo(AssetToOrg.FIELD_ASSET_SPECIALTY_ID, assetSpecialtyId)
                                        .andEqualTo(AssetToOrg.FIELD_PRIORITY, this.priority)
                                        .andNotEqualTo(AssetToOrg.FIELD_ASSET_ORG_ID, this.assetOrgId, true))
                        .build()) != 0) {
        	throw new CommonException(Constants.AafmErrorCode.AAFM_ASSET_TO_ORG_PRIORITY,this.priority);
        }
    }

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long assetOrgId;
    @ApiModelProperty(value = "资产专业ID")
    /*@NotNull*/
    private Long assetSpecialtyId;
    @ApiModelProperty(value = "服务区域， AMDM.ASSET_MAINT_SITE, 关联服务区域")
    @NotNull
    private Long maintSitesId;
    @ApiModelProperty(value = "专业归口部门，AMDM.ORGANIZATION, 关联组织")
    private Long majorDepartmentId;
    @ApiModelProperty(value = "管理部门，AMDM.ORGANIZATION,关联组织")
    private Long manageDepartmentId;
    @ApiModelProperty(value = "优先级，数值越大，优先级越高，上限：100, 不可重复")
    @NotNull
    private Long priority;
    @ApiModelProperty(value = "是否启用，默认：1")
    private Integer enabledFlag;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @ApiModelProperty("服务区域")
    @Transient
    private String maintSitesName;
    @ApiModelProperty("专业归口部门")
    @Transient
    private String majorDepartmentName;
    @ApiModelProperty("管理部门")
    @Transient
    private String manageDepartmentName;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getAssetOrgId() {
        return assetOrgId;
    }

    public void setAssetOrgId(Long assetOrgId) {
        this.assetOrgId = assetOrgId;
    }

    /**
     * @return 资产专业ID
     */
    public Long getAssetSpecialtyId() {
        return assetSpecialtyId;
    }

    public void setAssetSpecialtyId(Long assetSpecialtyId) {
        this.assetSpecialtyId = assetSpecialtyId;
    }

    /**
     * @return 服务区域， AMDM.ASSET_MAINT_SITE, 关联""服务区域""
     */
    public Long getMaintSitesId() {
        return maintSitesId;
    }

    public void setMaintSitesId(Long maintSitesId) {
        this.maintSitesId = maintSitesId;
    }

    /**
     * @return 专业归口部门，AMDM.ORGANIZATION, 关联""组织""
     */
    public Long getMajorDepartmentId() {
        return majorDepartmentId;
    }

    public void setMajorDepartmentId(Long majorDepartmentId) {
        this.majorDepartmentId = majorDepartmentId;
    }

    /**
     * @return 管理部门，AMDM.ORGANIZATION,关联""组织""
     */
    public Long getManageDepartmentId() {
        return manageDepartmentId;
    }

    public void setManageDepartmentId(Long manageDepartmentId) {
        this.manageDepartmentId = manageDepartmentId;
    }

    /**
     * @return 优先级，数值越大，优先级越高，上限：100, 不可重复
     */
    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    /**
     * @return 是否启用，默认：1
     */
    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
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
}
