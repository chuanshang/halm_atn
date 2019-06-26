package org.hzero.halm.afm.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.annotation.ModifyAudit;
import io.choerodon.mybatis.annotation.VersionAudit;
import io.choerodon.mybatis.domain.AuditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.api.dto.ProductCategoriesDTO;
import org.hzero.halm.afm.domain.repository.ProductCategoriesRepository;
import org.hzero.halm.afm.infra.constant.Constants;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品类别/资产分类
 *
 * @author like.zhang@hand-china.com 2019-01-11 10:42:38
 */
@ApiModel("产品类别/资产分类")
@VersionAudit
@ModifyAudit
@Table(name = "aafm_product_categories")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProductCategories extends AuditDomain {

    public static final String FIELD_PRODUCT_CATEGORY_ID = "productCategoryId";
    public static final String FIELD_PRODUCT_CATEGORY_NAME = "productCategoryName";
    public static final String FIELD_PRODUCT_CATEGORY_CODE = "productCategoryCode";
    public static final String FIELD_PARENT_CATEGORY_ID = "parentCategoryId";
    public static final String FIELD_CATEGORY_DESCRIPTION = "categoryDescription";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_NEED_CODE_RULE_FLAG = "needCodeRuleFlag";
    public static final String FIELD_CODE_RULE = "codeRule";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_LEVEL_PATH = "levelPath";
    public static final String FIELD_LEVEL_NUMBER = "levelNumber";


    public static final long ROOT_KEY = 0L;

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    /**
     * 唯一索引校验
     *
     * @param tenantId 租户ID
     */
    public void validUniqueIndex(Long tenantId, ProductCategoriesRepository productCategoriesRepository) {
        ProductCategories productCategories = new ProductCategories();
        productCategories.setProductCategoryName(this.productCategoryName);
        productCategories.setTenantId(tenantId);
        if (productCategoriesRepository.selectCount(productCategories) != 0) {
            throw new CommonException(Constants.AafmErrorCode.CATEGORY_NAME_DUPLICATED, this.productCategoryName);
        }
    }

    /**
     * 启用字段校验非空和唯一
     *
     * @param tenantId                    租户ID
     * @param productCategoriesRepository ProductCategoriesRepository
     */
    public void validUniqueFields(Long tenantId, ProductCategoriesRepository productCategoriesRepository) {
        // 是否启用代码
        if (!Objects.isNull(this.productCategoryCode)) {
            ProductCategories productCategories = new ProductCategories();
            productCategories.setTenantId(tenantId);
            productCategories.setProductCategoryCode(this.productCategoryCode);
            if (productCategoriesRepository.selectCount(productCategories) != 0) {
                throw new CommonException(Constants.AafmErrorCode.CATEGORY_CODE_DUPLICATED, this.productCategoryCode);
            }
        }
    }


    /**
     * 递归启用操作
     *
     * @param productCategoriesDTO 树状数据
     */
    public static void enableCategory(ProductCategoriesDTO productCategoriesDTO) {
        List<ProductCategoriesDTO> subNodes = productCategoriesDTO.getChildren();
        productCategoriesDTO.setEnabledFlag(BaseConstants.Flag.YES);
        if (CollectionUtils.isNotEmpty(subNodes)) {
            subNodes.forEach(ProductCategories::enableCategory);
        }
    }

    /**
     * 递归禁用操作
     *
     * @param productCategoriesDTO 树状数据
     */
    public static void disableCategory(ProductCategoriesDTO productCategoriesDTO) {
        List<ProductCategoriesDTO> subNodes = productCategoriesDTO.getChildren();
        productCategoriesDTO.setEnabledFlag(BaseConstants.Flag.NO);
        if (CollectionUtils.isNotEmpty(subNodes)) {
            subNodes.forEach(ProductCategories::disableCategory);
        }
    }

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    private Long productCategoryId;
    @ApiModelProperty(value = "名称，必输，租户内唯一")
    @Length(max = 120)
    @NotBlank
    private String productCategoryName;
    @Length(max = 30)
    @ApiModelProperty(value = "代码，必输且租户内唯一 或 非必输")
    private String productCategoryCode;
    @ApiModelProperty(value = "父级类别ID，父级类别ID为空时表示顶层类别")
    private Long parentCategoryId;
    @Length(max = 240)
    @ApiModelProperty(value = "全称，非必输")
    private String categoryDescription;
    @ApiModelProperty(value = "启用标记，默认：1（启用）")
    @Range(max = 1)
    private Integer enabledFlag;
    @ApiModelProperty(value = "编号规则ID")
    private String codeRule;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;
    private String levelPath;
    private Integer levelNumber;


    //
    // 非数据库字段
    // ------------------------------------------------------------------------------


    //
    // getter/setter
    // ------------------------------------------------------------------------------

    public Integer getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(Integer levelNumber) {
        this.levelNumber = levelNumber;
    }

    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }

    /**
     * @return 表ID，主键，供其他表做外键
     */
    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    /**
     * @return 名称，必输，租户内唯一
     */
    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    /**
     * @return 代码，必输且租户内唯一 或 非必输
     */
    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode;
    }

    /**
     * @return 父级类别ID，父级类别ID为空时表示顶层类别
     */
    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    /**
     * @return 全称，非必输
     */
    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    /**
     * @return 启用标记，默认：1（启用）
     */
    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    /**
     * @return 编号规则ID
     */
    public String getCodeRule() {
        return codeRule;
    }

    public void setCodeRule(String codeRule) {
        this.codeRule = codeRule;
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

    @Override
    @JsonIgnore
    public Date getCreationDate() {
        return super.getCreationDate();
    }

    @Override
    @JsonIgnore
    public Long getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    @JsonIgnore
    public Date getLastUpdateDate() {
        return super.getLastUpdateDate();
    }

    @Override
    @JsonIgnore
    public Long getLastUpdatedBy() {
        return super.getLastUpdatedBy();
    }

}
