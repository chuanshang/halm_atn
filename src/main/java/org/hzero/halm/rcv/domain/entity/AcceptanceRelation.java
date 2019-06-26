package org.hzero.halm.rcv.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 验收单关联
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@ApiModel("验收单关联")
@VersionAudit
@ModifyAudit
@Table(name = "arcv_acceptance_relation")
public class AcceptanceRelation extends AuditDomain {

    public static final String FIELD_ACCEPTANCE_RELATION_ID = "acceptanceRelationId";
    public static final String FIELD_ACCEPTANCE_HEADER_ID = "acceptanceHeaderId";
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
    private Long acceptanceRelationId;
    @ApiModelProperty(value = "关联的验收单头id")
    private Long acceptanceHeaderId;
	@ApiModelProperty(value = "关联的验收单id")
	private Long relateAcceptanceId;
    @ApiModelProperty(value = "租户ID")
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
	public Long getAcceptanceRelationId() {
		return acceptanceRelationId;
	}

	public void setAcceptanceRelationId(Long acceptanceRelationId) {
		this.acceptanceRelationId = acceptanceRelationId;
	}
    /**
     * @return 关联的验收单id
     */
	public Long getAcceptanceHeaderId() {
		return acceptanceHeaderId;
	}

	public void setAcceptanceHeaderId(Long acceptanceHeaderId) {
		this.acceptanceHeaderId = acceptanceHeaderId;
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

	public Long getRelateAcceptanceId() {
		return relateAcceptanceId;
	}

	public void setRelateAcceptanceId(Long relateAcceptanceId) {
		this.relateAcceptanceId = relateAcceptanceId;
	}
}
