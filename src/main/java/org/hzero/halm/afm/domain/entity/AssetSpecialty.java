package org.hzero.halm.afm.domain.entity;

import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hzero.halm.afm.domain.repository.AssetSpecialtyRepository;
import org.hzero.halm.afm.infra.constant.Constants;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 资产专业定义(头)
 *
 * @author yongchao.yu@hand-china.com 2019-02-19 17:12:37
 */
@ApiModel("资产专业定义(头)")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_asset_specialty")
public class AssetSpecialty extends AuditDomain {

    public static final String FIELD_ASSET_SPECIALTY_ID = "assetSpecialtyId";
    public static final String FIELD_ASSET_SPECIALTY_NAME = "assetSpecialtyName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------
	public void validateAssetSpecialty(Long tenantId,AssetSpecialtyRepository assetSpecialtyRepository){
		//校验名称是否重复
		validateUniqueSpecialtyName(tenantId,assetSpecialtyRepository);
	}

	public void validateUniqueSpecialtyName(Long tenantId,AssetSpecialtyRepository assetSpecialtyRepository){
		if (assetSpecialtyRepository.selectCountByCondition(
				org.hzero.mybatis.domian.Condition.builder(AssetSpecialty.class)
					.andWhere(
							org.hzero.mybatis.util.Sqls.custom()
									.andEqualTo(AssetSpecialty.FIELD_TENANT_ID,tenantId)
									.andEqualTo(AssetSpecialty.FIELD_ASSET_SPECIALTY_NAME,this.assetSpecialtyName)
									.andNotEqualTo(AssetSpecialty.FIELD_ASSET_SPECIALTY_ID,this.assetSpecialtyId,true)
					).build()) !=0 ){
			throw new CommonException(Constants.AafmErrorCode.AAFM_ASSET_SPECIALTY_NAME_DUPLICATED,this.assetSpecialtyName);
		}
	}
    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long assetSpecialtyId;
    @ApiModelProperty(value = "专业分类名称，必输，同租户下唯一")
    @NotBlank
    private String assetSpecialtyName;
    @ApiModelProperty(value = "是否启用，必输，默认：1")
    @NotNull
    private Integer enabledFlag;
   @ApiModelProperty(value = "描述")    
    private String description;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------
	@Transient
	private List<AssetToOrg> assetToOrgList;
    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getAssetSpecialtyId() {
		return assetSpecialtyId;
	}

	public void setAssetSpecialtyId(Long assetSpecialtyId) {
		this.assetSpecialtyId = assetSpecialtyId;
	}
    /**
     * @return 专业分类名称，必输，同租户下唯一
     */
	public String getAssetSpecialtyName() {
		return assetSpecialtyName;
	}

	public void setAssetSpecialtyName(String assetSpecialtyName) {
		this.assetSpecialtyName = assetSpecialtyName;
	}
    /**
     * @return 是否启用，必输，默认：1
     */
	public Integer getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
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

	public List<AssetToOrg> getAssetToOrgList() {
		return assetToOrgList;
	}

	public void setAssetToOrgList(List<AssetToOrg> assetToOrgList) {
		this.assetToOrgList = assetToOrgList;
	}
}
