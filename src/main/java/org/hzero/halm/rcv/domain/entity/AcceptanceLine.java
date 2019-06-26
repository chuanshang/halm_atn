package org.hzero.halm.rcv.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;

import java.math.BigDecimal;
import java.util.List;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 验收单行
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@ApiModel("验收单行")
@VersionAudit
@ModifyAudit
@Table(name = "arcv_acceptance_line")
public class AcceptanceLine extends AuditDomain {

	public static final String FIELD_ACCEPTANCE_LINE_ID = "acceptanceLineId";
	public static final String FIELD_ACCEPTANCE_HEADER_ID = "acceptanceHeaderId";
	public static final String FIELD_CONTRACT_ID = "contractId";
	public static final String FIELD_CONTRACT_LINE_ID = "contractLineId";
	public static final String FIELD_PROJECT_ID = "projectId";
	public static final String FIELD_WBS_LINE_ID = "wbsLineId";
	public static final String FIELD_BUDGET_HEADER_ID = "budgetHeaderId";
	public static final String FIELD_BUDGET_LINE_ID = "budgetLineId";
	public static final String FIELD_PRODUCT_CATEGORY_ID = "productCategoryId";
	public static final String FIELD_ASSETS_SET_ID = "assetsSetId";
	public static final String FIELD_ACCEPTANCE_LINE_NAME = "acceptanceLineName";
	public static final String FIELD_DELIVERY_LIST_ID = "deliveryListId";
	public static final String FIELD_SPECIFICATIONS = "specifications";
	public static final String FIELD_UNIT_PRICE = "unitPrice";
	public static final String FIELD_UOM_ID = "uomId";
	public static final String FIELD_DELIVERY_QUANTITY = "deliveryQuantity";
	public static final String FIELD_DESCRIPTION = "description";
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
	private Long acceptanceLineId;
	@ApiModelProperty(value = "验收单头id")
	private Long acceptanceHeaderId;
	@ApiModelProperty(value = "合同id，URL值集(开发人员补充值集名称)")
	private Long contractId;
	@ApiModelProperty(value = "合同行id，URL值集(开发人员补充值集名称)")
	private Long contractLineId;
	@ApiModelProperty(value = "项目id，URL值集(APPM.PROJECT)")
	private Long projectId;
	@ApiModelProperty(value = "WBS计划行ID，URL值集(APPM.PRO_WBS)")
	private Long wbsLineId;
	@ApiModelProperty(value = "项目预算ID，URL值集(开发人员补充值集名称)")
	private Long budgetHeaderId;
	@ApiModelProperty(value = "项目预算行ID，URL值集(开发人员补充值集名称)")
	private Long budgetLineId;
	@ApiModelProperty(value = "产品类别")
	@NotNull
	private Long productCategoryId;
	@ApiModelProperty(value = "资产组")
	@NotNull
	private Long assetsSetId;
	@ApiModelProperty(value = "名称")
	@NotBlank
	private String acceptanceLineName;
	@ApiModelProperty(value = "交付清单行id，URL值集(ARCV.DELIVERY_LIST)")
	private Long deliveryListId;
	@ApiModelProperty(value = "规格/型号")
	private String specifications;
	@ApiModelProperty(value = "单价")
	@NotNull
	private BigDecimal unitPrice;
	@ApiModelProperty(value = "单位Id，值集：AMDM.UOM")
	@NotNull
	private Long uomId;
	@ApiModelProperty(value = "数量")
	@NotNull
	private Long deliveryQuantity;
	@ApiModelProperty(value = "备注")
	private String description;
	@ApiModelProperty(value = "租户ID")
	private Long tenantId;

	//
	// 非数据库字段
	// ------------------------------------------------------------------------------
	/**
	 * 验收单头Meaning
	 */
	@Transient
	private String acceptanceHeaderMeaning;
	/**
	 * 合同头Meaning
	 */
	@Transient
	private String contractMeaning;
	/**
	 * 合同行Meaning
	 */
	@Transient
	private String contractLineMeaning;
	/**
	 * 项目Meaning
	 */
	@Transient
	private String projectMeaning;
	/**
	 * WBS计划Meaning
	 */
	@Transient
	private String wbsLineMeaning;
	/**
	 * 项目预算Meaning
	 */
	@Transient
	private String budgetHeaderMeaning;
	/**
	 * 项目预算行Meaning
	 */
	@Transient
	private String budgetLineMeaning;
	/**
	 * 产品类别Meaning
	 */
	@Transient
	private String productCategoryMeaning;
	/**
	 * 资产组Meaning
	 */
	@Transient
	private String assetsSetMeaning;
	/**
	 * 交付清单Meaning
	 */
	@Transient
	private String deliveryListMeaning;
	/**
	 * 单位Meaning
	 */
	@Transient
	private String uomMeaning;

	//
	// getter/setter
	// ------------------------------------------------------------------------------

	/**
	 * @return 表ID，主键，供其他表做外键
	 */
	public Long getAcceptanceLineId() {
		return acceptanceLineId;
	}

	public void setAcceptanceLineId(Long acceptanceLineId) {
		this.acceptanceLineId = acceptanceLineId;
	}

	/**
	 * @return 验收单头id
	 */
	public Long getAcceptanceHeaderId() {
		return acceptanceHeaderId;
	}

	public void setAcceptanceHeaderId(Long acceptanceHeaderId) {
		this.acceptanceHeaderId = acceptanceHeaderId;
	}

	/**
	 * @return 合同id，URL值集(开发人员补充值集名称)
	 */
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	/**
	 * @return 合同行id，URL值集(开发人员补充值集名称)
	 */
	public Long getContractLineId() {
		return contractLineId;
	}

	public void setContractLineId(Long contractLineId) {
		this.contractLineId = contractLineId;
	}

	/**
	 * @return 项目id，URL值集(开发人员补充值集名称)
	 */
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return WBS计划行ID，URL值集(开发人员补充值集名称)
	 */
	public Long getWbsLineId() {
		return wbsLineId;
	}

	public void setWbsLineId(Long wbsLineId) {
		this.wbsLineId = wbsLineId;
	}

	/**
	 * @return 项目预算ID，URL值集(开发人员补充值集名称)
	 */
	public Long getBudgetHeaderId() {
		return budgetHeaderId;
	}

	public void setBudgetHeaderId(Long budgetHeaderId) {
		this.budgetHeaderId = budgetHeaderId;
	}

	/**
	 * @return 项目预算行ID，URL值集(开发人员补充值集名称)
	 */
	public Long getBudgetLineId() {
		return budgetLineId;
	}

	public void setBudgetLineId(Long budgetLineId) {
		this.budgetLineId = budgetLineId;
	}

	/**
	 * @return 产品类别
	 */
	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	/**
	 * @return 资产组
	 */
	public Long getAssetsSetId() {
		return assetsSetId;
	}

	public void setAssetsSetId(Long assetsSetId) {
		this.assetsSetId = assetsSetId;
	}

	/**
	 * @return 名称
	 */
	public String getAcceptanceLineName() {
		return acceptanceLineName;
	}

	public void setAcceptanceLineName(String acceptanceLineName) {
		this.acceptanceLineName = acceptanceLineName;
	}

	/**
	 * @return 交付清单行id，URL值集(开发人员补充值集名称)
	 */
	public Long getDeliveryListId() {
		return deliveryListId;
	}

	public void setDeliveryListId(Long deliveryListId) {
		this.deliveryListId = deliveryListId;
	}

	/**
	 * @return 规格/型号
	 */
	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	/**
	 * @return 单价
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return 单位Id，值集：AMDM.UOM
	 */
	public Long getUomId() {
		return uomId;
	}

	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}

	/**
	 * @return 数量
	 */
	public Long getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Long deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	/**
	 * @return 备注
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

	public String getAcceptanceHeaderMeaning() {
		return acceptanceHeaderMeaning;
	}

	public void setAcceptanceHeaderMeaning(String acceptanceHeaderMeaning) {
		this.acceptanceHeaderMeaning = acceptanceHeaderMeaning;
	}

	public String getContractMeaning() {
		return contractMeaning;
	}

	public void setContractMeaning(String contractMeaning) {
		this.contractMeaning = contractMeaning;
	}

	public String getContractLineMeaning() {
		return contractLineMeaning;
	}

	public void setContractLineMeaning(String contractLineMeaning) {
		this.contractLineMeaning = contractLineMeaning;
	}

	public String getProjectMeaning() {
		return projectMeaning;
	}

	public void setProjectMeaning(String projectMeaning) {
		this.projectMeaning = projectMeaning;
	}

	public String getWbsLineMeaning() {
		return wbsLineMeaning;
	}

	public void setWbsLineMeaning(String wbsLineMeaning) {
		this.wbsLineMeaning = wbsLineMeaning;
	}

	public String getBudgetHeaderMeaning() {
		return budgetHeaderMeaning;
	}

	public void setBudgetHeaderMeaning(String budgetHeaderMeaning) {
		this.budgetHeaderMeaning = budgetHeaderMeaning;
	}

	public String getBudgetLineMeaning() {
		return budgetLineMeaning;
	}

	public void setBudgetLineMeaning(String budgetLineMeaning) {
		this.budgetLineMeaning = budgetLineMeaning;
	}

	public String getProductCategoryMeaning() {
		return productCategoryMeaning;
	}

	public void setProductCategoryMeaning(String productCategoryMeaning) {
		this.productCategoryMeaning = productCategoryMeaning;
	}

	public String getAssetsSetMeaning() {
		return assetsSetMeaning;
	}

	public void setAssetsSetMeaning(String assetsSetMeaning) {
		this.assetsSetMeaning = assetsSetMeaning;
	}

	public String getDeliveryListMeaning() {
		return deliveryListMeaning;
	}

	public void setDeliveryListMeaning(String deliveryListMeaning) {
		this.deliveryListMeaning = deliveryListMeaning;
	}

	public String getUomMeaning() {
		return uomMeaning;
	}

	public void setUomMeaning(String uomMeaning) {
		this.uomMeaning = uomMeaning;
	}
}
