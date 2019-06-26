package org.hzero.halm.afm.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.hzero.boot.platform.lov.annotation.LovValue;
import org.hzero.core.algorithm.tree.Child;
import org.hzero.export.annotation.ExcelColumn;
import org.hzero.export.annotation.ExcelSheet;
import org.hzero.halm.afm.domain.entity.ProductCategories;
import org.hzero.mybatis.domian.SecurityToken;

/**
 * 产品类别/资产分类DTO
 *
 * @author like.zhang@hand-china.com 2019/01/11 16:05
 */
@ExcelSheet(zh = "产品类别 资产分类", en = "Product Category")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProductCategoriesDTO extends Child<ProductCategoriesDTO> implements SecurityToken, Comparable<ProductCategoriesDTO> {

    @ExcelColumn(zh = "父类别名称", en = "Parent Category Name")
    private String parentCategoryName;
    @ApiModelProperty("表ID，主键，供其他表做外键")
    private Long productCategoryId;
    @ApiModelProperty(value = "名称，必输，租户内唯一")
    @ExcelColumn(zh = "名称", en = "name")
    private String productCategoryName;
    @ApiModelProperty(value = "代码，必输且租户内唯一 或 非必输")
    @ExcelColumn(zh = "代码", en = "code")
    private String productCategoryCode;
    @ApiModelProperty(value = "父级类别ID，父级类别ID为空时表示顶层类别")
    private Long parentCategoryId;
    @ApiModelProperty(value = "全称，非必输")
    @ExcelColumn(zh = "全称", en = "Full name")
    private String categoryDescription;
    @ApiModelProperty(value = "启用标记，默认：1（启用）")
    @LovValue(lovCode = "HPFM.ENABLED_FLAG")
    private Integer enabledFlag;
    @ExcelColumn(zh = "状态", en = "Enable Status")
    private String enabledFlagMeaning;
    @ApiModelProperty(value = "编号规则")
    private String codeRule;
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;
    @ApiModelProperty(hidden = true)
    private String _token;

    private String levelPath;

    private Long objectVersionNumber;

    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getEnabledFlagMeaning() {
        return enabledFlagMeaning;
    }

    public void setEnabledFlagMeaning(String enabledFlagMeaning) {
        this.enabledFlagMeaning = enabledFlagMeaning;
    }

    public Long getObjectVersionNumber() {
        return objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    @Override
    public String get_token() {
        return _token;
    }
    @Override
    public void set_token(String _token) {
        this._token = _token;
    }

    @Override
    public Class<? extends SecurityToken> associateEntityClass() {
        return ProductCategories.class;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductCategoryCode() {
        return productCategoryCode;
    }

    public void setProductCategoryCode(String productCategoryCode) {
        this.productCategoryCode = productCategoryCode;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getCodeRule() {
        return codeRule;
    }

    public void setCodeRule(String codeRule) {
        this.codeRule = codeRule;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public int compareTo(ProductCategoriesDTO o) {
        return o.getProductCategoryId().compareTo(this.productCategoryId);
    }
}
