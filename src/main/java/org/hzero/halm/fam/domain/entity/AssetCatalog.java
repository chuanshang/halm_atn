package org.hzero.halm.fam.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hzero.halm.fam.api.dto.AssetCatalogDTO;

import io.choerodon.mybatis.domain.AuditDomain;
import java.math.BigDecimal;
import java.util.List;

import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资产目录
 *
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
@ApiModel("资产目录")
@VersionAudit
@ModifyAudit
@Table(name = "afam_asset_catalog")
public class AssetCatalog extends AuditDomain {

    public static final String FIELD_ASSET_CATALOG_ID = "assetCatalogId";
    public static final String FIELD_CATALOG_NAME = "catalogName";
    public static final String FIELD_PRODUCT_CATEGORY_ID = "productCategoryId";
    public static final String FIELD_CATALOG_CODE = "catalogCode";
    public static final String FIELD_RESIDUAL_VALUE_RATE = "residualValueRate";
    public static final String FIELD_DEPRECIATION_MONTH = "depreciationMonth";
    public static final String FIELD_DEPRECIATION_TYPE_CODE = "depreciationTypeCode";
    public static final String FIELD_ACCOUNT_TYPE_CODE = "accountTypeCode";
    public static final String FIELD_PARENT_CATALOG_ID = "parentCatalogId";
    public static final String FIELD_LEVEL_PATH = "levelPath";
    public static final String FIELD_LEVEL_NUMBER = "levelNumber";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TENANT_ID = "tenantId";

	public static final long ROOT_KEY = 0L;

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

	public static void operateAssetCatalog(AssetCatalogDTO assetCatalogDTO, Integer flag) {
		List<AssetCatalogDTO> subNodes = assetCatalogDTO.getChildren();
		assetCatalogDTO.setEnabledFlag(flag);
		if (CollectionUtils.isNotEmpty(subNodes)) {
			subNodes.forEach((node) ->operateAssetCatalog(node, flag));
		}
	}
    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long assetCatalogId;
    @ApiModelProperty(value = "名称")
    @NotBlank
    private String catalogName;
    @ApiModelProperty(value = "产品类别，URL值集，AAFM.ASSET_CLASS")
    @NotNull
    private Long productCategoryId;
   @ApiModelProperty(value = "资产目录代码")    
    private String catalogCode;
   @ApiModelProperty(value = "残值率")    
    private BigDecimal residualValueRate;
   @ApiModelProperty(value = "折旧月份")    
    private Long depreciationMonth;
   @ApiModelProperty(value = "折旧类型，独立值集，（开发人员补充名字）")    
    private String depreciationTypeCode;
   @ApiModelProperty(value = "资产入账会计科目类型,独立值集，（开发人员补充名字）")    
    private String accountTypeCode;
   @ApiModelProperty(value = "上级组织/父组织,为空时表示顶层组织")    
    private Long parentCatalogId;
    @ApiModelProperty(value = "层级结构的节点路径,递归父级id/本级id")
    private String levelPath;
    @ApiModelProperty(value = "当前所处层级")
    private Long levelNumber;
    @ApiModelProperty(value = "启用")
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
	/**
	 * 是否显示子节点
	 */
	@Transient
	@ApiModelProperty(value = "是否显示子节点，默认1")
	private Integer showChildFlag;
	@Transient
	private String productCategoryMeaning;
	//
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 表ID，主键，供其他表做外键
     */
	public Long getAssetCatalogId() {
		return assetCatalogId;
	}

	public void setAssetCatalogId(Long assetCatalogId) {
		this.assetCatalogId = assetCatalogId;
	}
    /**
     * @return 名称
     */
	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
    /**
     * @return 产品类别，URL值集，AAFM.ASSET_CLASS
     */
	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
    /**
     * @return 资产目录代码
     */
	public String getCatalogCode() {
		return catalogCode;
	}

	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}
    /**
     * @return 残值率
     */
	public BigDecimal getResidualValueRate() {
		return residualValueRate;
	}

	public void setResidualValueRate(BigDecimal residualValueRate) {
		this.residualValueRate = residualValueRate;
	}
    /**
     * @return 折旧月份
     */
	public Long getDepreciationMonth() {
		return depreciationMonth;
	}

	public void setDepreciationMonth(Long depreciationMonth) {
		this.depreciationMonth = depreciationMonth;
	}
    /**
     * @return 折旧类型，独立值集，（开发人员补充名字）
     */
	public String getDepreciationTypeCode() {
		return depreciationTypeCode;
	}

	public void setDepreciationTypeCode(String depreciationTypeCode) {
		this.depreciationTypeCode = depreciationTypeCode;
	}
    /**
     * @return 资产入账会计科目类型,独立值集，（开发人员补充名字）
     */
	public String getAccountTypeCode() {
		return accountTypeCode;
	}

	public void setAccountTypeCode(String accountTypeCode) {
		this.accountTypeCode = accountTypeCode;
	}
    /**
     * @return 上级组织/父组织,为空时表示顶层组织
     */
	public Long getParentCatalogId() {
		return parentCatalogId;
	}

	public void setParentCatalogId(Long parentCatalogId) {
		this.parentCatalogId = parentCatalogId;
	}
    /**
     * @return 层级结构的节点路径,递归父级id/本级id
     */
	public String getLevelPath() {
		return levelPath;
	}

	public void setLevelPath(String levelPath) {
		this.levelPath = levelPath;
	}
    /**
     * @return 当前所处层级
     */
	public Long getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(Long levelNumber) {
		this.levelNumber = levelNumber;
	}
    /**
     * @return 启用
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

	public Integer getShowChildFlag() {
		return showChildFlag;
	}

	public void setShowChildFlag(Integer showChildFlag) {
		this.showChildFlag = showChildFlag;
	}

	public String getProductCategoryMeaning() {
		return productCategoryMeaning;
	}

	public void setProductCategoryMeaning(String productCategoryMeaning) {
		this.productCategoryMeaning = productCategoryMeaning;
	}
}
