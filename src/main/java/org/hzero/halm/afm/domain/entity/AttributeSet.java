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
import org.hzero.halm.afm.domain.repository.AttributeSetRepository;
import org.hzero.halm.afm.infra.constant.Constants;

import java.util.List;

/**
 * 属性组头信息
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:24
 */
@ApiModel("属性组头信息")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_attribute_set")
public class AttributeSet extends AuditDomain {

    public static final String FIELD_ATTRIBUTE_SET_ID = "attributeSetId";
    public static final String FIELD_ATTRIBUTE_SET_NAME = "attributeSetName";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

	/**
	 * 校验
	 * @param tenantId
	 * @param attributeSetRepository
	 */
	public void validateAttribute(Long tenantId, AttributeSetRepository attributeSetRepository){
		//校验名称是否重复
		validUniqueIndex(tenantId,attributeSetRepository);

	}
	/**
	 * 唯一索引校验
	 *
	 * @param tenantId 租户ID
	 */
	public void validUniqueIndex(Long tenantId, AttributeSetRepository attributeSetRepository) {
		if (attributeSetRepository.selectCountByCondition(
				org.hzero.mybatis.domian.Condition.builder(AttributeSet.class)
						.andWhere(
								org.hzero.mybatis.util.Sqls.custom()
										.andEqualTo(AttributeSet.FIELD_TENANT_ID,tenantId)
										.andEqualTo(AttributeSet.FIELD_ATTRIBUTE_SET_NAME,this.attributeSetName)
										.andNotEqualTo(AttributeSet.FIELD_ATTRIBUTE_SET_ID,this.attributeSetId,true)
						).build()) != 0) {
			throw new CommonException(Constants.AafmErrorCode.ATTRIBUTE_SET_NAME_DUPLICATED,this.attributeSetName);
		}
	}

    //
    // 数据库字段
    // ------------------------------------------------------------------------------

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long attributeSetId;
    @ApiModelProperty(value = "名称，必输，租户内唯一")
	@Length(max = 30)
    @NotBlank
    private String attributeSetName;
    @ApiModelProperty(value = "描述")
    @Length(max = 120)
    private String description;
    @ApiModelProperty(value = "启用标识")
	@Range(max = 1)
    @NotNull
    private Integer enabledFlag;
    @ApiModelProperty(value = "租户ID")
    @NotNull
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------
	@Transient
	private List<AttributeLine> attributeLinesList;

	//
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getAttributeSetId() {
		return attributeSetId;
	}

	public void setAttributeSetId(Long attributeSetId) {
		this.attributeSetId = attributeSetId;
	}
    /**
     * @return 名称，必输，租户内唯一
     */
	public String getAttributeSetName() {
		return attributeSetName;
	}

	public void setAttributeSetName(String attributeSetName) {
		this.attributeSetName = attributeSetName;
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
     * @return 启用标识
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


	public List<AttributeLine> getAttributeLinesList() {
		return attributeLinesList;
	}

	public void setAttributeLinesList(List<AttributeLine> attributeLinesList) {
		this.attributeLinesList = attributeLinesList;
	}
}
