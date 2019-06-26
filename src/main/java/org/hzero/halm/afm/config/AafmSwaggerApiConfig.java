package org.hzero.halm.afm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class AafmSwaggerApiConfig {

    /**
     * 资产
     */
    public static final String AFM_ASSET = "Assets";

    /**
     * 资产专业分类
     */
    public static final String AAFM_ASSET_SPECIALTY = "Aafm Asset Specialty";

    /**
     * 产品分类
     */
    public static final String AAFM_PRODUCT_CATEGORY = "Aafm Product Category";
    /**
     * 资产组
     */
    public static final String AAFM_ASSETSET = "AssetsSet";
	/**
	 * 资产状态
	 */
	public static final String ASSET_STATUS = "Asset Status";
    /**
     * 属性组
     */
    public static final String ATTRIBUTE_SET = "Attribute Set";
    /**
     * 事件类型
     */
    public static final String TRANSACTION_TYPES = "Transaction Types";
    /**
     * 资产动态字段
     */
    public static final String ASSET_DYNAMIC_COLUMN = "Asset Dynamic Column";

    @Autowired
    public AafmSwaggerApiConfig(Docket docket) {

        docket.tags(
                new Tag(AAFM_ASSETSET, "资产组模块"),
                new Tag(AFM_ASSET, "资产"),
                new Tag(AAFM_PRODUCT_CATEGORY, "产品类别/资产分类"),
                new Tag(AAFM_ASSET_SPECIALTY,"资产专业分类"),
				new Tag(ASSET_STATUS, "资产状态"),
                new Tag(ATTRIBUTE_SET, "属性组"),
                new Tag(AAFM_ASSET_SPECIALTY,"资产专业分类"),
                new Tag(TRANSACTION_TYPES, "事件类型"),
                new Tag(ASSET_DYNAMIC_COLUMN, "资产动态字段")
                );
    }
}
