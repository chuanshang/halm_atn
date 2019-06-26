package org.hzero.halm.fam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
@Configuration
public class AfamSwaggerApiConfig{

    /**
     * 资产目录
     */
    public static final String AFAM_ASSET_CATALOG = "Asset Catalog";
    /**
     * 固定资产
     */
    public static final String AFAM_FIXED_ASSETS = "Fixed Assets";

    @Autowired
    public AfamSwaggerApiConfig(Docket docket) {

        docket.tags(
                new Tag(AFAM_ASSET_CATALOG, "资产目录"),
                new Tag(AFAM_FIXED_ASSETS, "固定资产")
        );
    }
}