package org.hzero.halm.rcv.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotBlank;
import io.choerodon.mybatis.domain.AuditDomain;

import java.math.BigDecimal;
import java.util.Date;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;

/**
 * 交付清单行
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-17 16:06:43
 */
@ApiModel("交付清单行")
@VersionAudit
@ModifyAudit
@Table(name = "arcv_delivery_list")
@ExcelSheet(zh = "交付清单行", en = "Delivery List")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryList extends AuditDomain {

	public static final String FIELD_DELIVERY_LIST_ID = "deliveryListId";
	public static final String FIELD_DELIVERY_LIST_NAME = "deliveryListName";
	public static final String FIELD_CONTRACT_ID = "contractId";
	public static final String FIELD_CONTRACT_LINE_ID = "contractLineId";
	public static final String FIELD_PROJECT_ID = "projectId";
	public static final String FIELD_WBS_LINE_ID = "wbsLineId";
	public static final String FIELD_BUDGET_HEADER_ID = "budgetHeaderId";
	public static final String FIELD_BUDGET_LINE_ID = "budgetLineId";
	public static final String FIELD_PRODUCT_CATEGORY_ID = "productCategoryId";
	public static final String FIELD_ASSETS_SET_ID = "assetsSetId";
	public static final String FIELD_SPECIFICATIONS = "specifications";
	public static final String FIELD_UNIT_PRICE = "unitPrice";
	public static final String FIELD_UOM_ID = "uomId";
	public static final String FIELD_NEED_DELIVERY_QUANTITY = "needDeliveryQuantity";
	public static final String FIELD_DELIVERED_QUANTITY = "deliveredQuantity";
	public static final String FIELD_DELIVERY_COMPLETE_DATE = "deliveryCompleteDate";
	public static final String FIELD_DELIVERY_COMPLETE_FLAG = "deliveryCompleteFlag";
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
	private Long deliveryListId;
	@ApiModelProperty(value = "名称")
	@ExcelColumn(zh = "名称", en = "Delivery List Name")
	@NotBlank
	private String deliveryListName;
	@ApiModelProperty(value = "合同id，URL值集")
	@NotNull
	private Long contractId;
	@ApiModelProperty(value = "合同行id，URL值集")
	private Long contractLineId;
	@ApiModelProperty(value = "项目id，URL值集(APPM.PROJECT)")
	private Long projectId;
	@ApiModelProperty(value = "WBS计划行ID，URL值集")
	private Long wbsLineId;
	@ApiModelProperty(value = "项目预算ID，URL值集")
	private Long budgetHeaderId;
	@ApiModelProperty(value = "项目预算行ID，URL值集")
	private Long budgetLineId;
	@ApiModelProperty(value = "产品类别")
	private Long productCategoryId;
	@ApiModelProperty(value = "资产组")
	private Long assetsSetId;
	@ApiModelProperty(value = "规格/型号")
	@ExcelColumn(zh = "规格/型号", en = "specifications")
	private String specifications;
	@ApiModelProperty(value = "单价")
	@NotNull
	private BigDecimal unitPrice;
	@ApiModelProperty(value = "单位Id，值集：AMDM.UOM")
	private Long uomId;
	@ApiModelProperty(value = "应交付数量")
	@ExcelColumn(zh = "应交付数量", en = "Need Delivery Quantity")
	@NotNull
	private Long needDeliveryQuantity;
	@ApiModelProperty(value = "已交付数量")
	@ExcelColumn(zh = "已交付数量", en = "Delivered Quantity")
	private Long deliveredQuantity;
	@ApiModelProperty(value = "交付完成日期")
	@ExcelColumn(zh = "交付完成日期", en = "Delivery Complete Date", pattern = BaseConstants.Pattern.DATE)
	private Date deliveryCompleteDate;
	@ApiModelProperty(value = "是否交付完成")
	@LovValue(value = "HPFM.FLAG", meaningField = "deliveryCompleteFlagMeaning")
	private Integer deliveryCompleteFlag;
	@ApiModelProperty(value = "描述")
	@ExcelColumn(zh = "描述", en = "description")
	private String description;
	@ApiModelProperty(value = "租户ID")
	@NotNull
	private Long tenantId;

	//
	// 非数据库字段
	// ------------------------------------------------------------------------------

	/**
	 * 产品类别Meaning
	 */
	@Transient
	@ExcelColumn(zh = "产品类别", en = "Product Category")
	private String productCategoryMeaning;
	/**
	 * 资产组Meaning
	 */
	@Transient
	@ExcelColumn(zh = "资产组", en = "Assets Set")
	private String assetsSetMeaning;
	/**
	 * 单位Meaning
	 */
	@Transient
	@ExcelColumn(zh = "单位", en = "uon")
	private String uomMeaning;
	/**
	 * 合同Meaning
	 */
	@Transient
	@ExcelColumn(zh = "合同", en = "contract")
	private String contractMeaning;
	/**
	 * 合同行Meaning
	 */
	@Transient
	@ExcelColumn(zh = "合同行", en = "Contract LineId")
	private String contractLineMeaning;
	/**
	 * 项目Meaning
	 */
	@Transient
	@ExcelColumn(zh = "项目", en = "project")
	private String projectMeaning;
	/**
	 * WBS计划Meaning
	 */
	@Transient
	@ExcelColumn(zh = "WBS计划", en = "WBS Plan")
	private String wbsLineMeaning;
	/**
	 * 项目预算Meaning
	 */
	@Transient
	@ExcelColumn(zh = "项目预算", en = "Budget")
	private String budgetHeaderMeaning;
	/**
	 * 项目预算行Meaning
	 */
	@Transient
	@ExcelColumn(zh = "项目预算行", en = "Budget Line")
	private String budgetLineMeaning;
	/**
	 * 是否交付完成Meaning
	 */
	@Transient
	@ExcelColumn(zh = "是否交付完成", en = "Delivery Complete")
	private String deliveryCompleteFlagMeaning;
	//
	// getter/setter
	// ------------------------------------------------------------------------------

	/**
	 * @return 表ID，主键，供其他表做外键
	 */
	public Long getDeliveryListId() {
		return deliveryListId;
	}

	public void setDeliveryListId(Long deliveryListId) {
		this.deliveryListId = deliveryListId;
	}

	/**
	 * @return 名称
	 */
	public String getDeliveryListName() {
		return deliveryListName;
	}

	public void setDeliveryListName(String deliveryListName) {
		this.deliveryListName = deliveryListName;
	}

	/**
	 * @return 合同id，URL值集
	 */
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	/**
	 * @return 合同行id，URL值集
	 */
	public Long getContractLineId() {
		return contractLineId;
	}

	public void setContractLineId(Long contractLineId) {
		this.contractLineId = contractLineId;
	}

	/**
	 * @return 项目id，URL值集
	 */
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return WBS计划行ID，URL值集
	 */
	public Long getWbsLineId() {
		return wbsLineId;
	}

	public void setWbsLineId(Long wbsLineId) {
		this.wbsLineId = wbsLineId;
	}

	/**
	 * @return 项目预算ID，URL值集
	 */
	public Long getBudgetHeaderId() {
		return budgetHeaderId;
	}

	public void setBudgetHeaderId(Long budgetHeaderId) {
		this.budgetHeaderId = budgetHeaderId;
	}

	/**
	 * @return 项目预算行ID，URL值集
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
	 * @return 应交付数量
	 */
	public Long getNeedDeliveryQuantity() {
		return needDeliveryQuantity;
	}

	public void setNeedDeliveryQuantity(Long needDeliveryQuantity) {
		this.needDeliveryQuantity = needDeliveryQuantity;
	}

	/**
	 * @return 已交付数量
	 */
	public Long getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(Long deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	/**
	 * @return 交付完成日期
	 */
	public Date getDeliveryCompleteDate() {
		return deliveryCompleteDate;
	}

	public void setDeliveryCompleteDate(Date deliveryCompleteDate) {
		this.deliveryCompleteDate = deliveryCompleteDate;
	}

	/**
	 * @return 是否交付完成
	 */
	public Integer getDeliveryCompleteFlag() {
		return deliveryCompleteFlag;
	}

	public void setDeliveryCompleteFlag(Integer deliveryCompleteFlag) {
		this.deliveryCompleteFlag = deliveryCompleteFlag;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getUomMeaning() {
		return uomMeaning;
	}

	public void setUomMeaning(String uomMeaning) {
		this.uomMeaning = uomMeaning;
	}

	public String getProjectMeaning() {
		return projectMeaning;
	}

	public void setProjectMeaning(String projectMeaning) {
		this.projectMeaning = projectMeaning;
	}

	public String getDeliveryCompleteFlagMeaning() {
		return deliveryCompleteFlagMeaning;
	}

	public void setDeliveryCompleteFlagMeaning(String deliveryCompleteFlagMeaning) {
		this.deliveryCompleteFlagMeaning = deliveryCompleteFlagMeaning;
	}

	public String getWbsLineMeaning() {
		return wbsLineMeaning;
	}

	public void setWbsLineMeaning(String wbsLineMeaning) {
		this.wbsLineMeaning = wbsLineMeaning;
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
}
