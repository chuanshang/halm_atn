package org.hzero.halm.atn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class AatnSwaggerApiConfig {
    /**
     * 资产调拨单
     */
    public static final String AATN_TRANSFER_ORDER = "Transfer Order";
    /**
     * 资产移交归还单
     */
    public static final String AATN_HANDOVER_ORDER = "handover order";
    /**
     * 资产变更单
     */
    public static final String AATN_CHANGE_ORDER = "Change Order";
    /**
     * 资产报废单
     */
    public static final String AATN_SCRAP_ORDER = "Scrap Order";
    /**
     * 资产处置单
     */
    public static final String AATN_DISPOSE_ORDER = "Aatn Dispose Order";
    /**
     * 资产事务履历
     */
    public static final String AATN_TRANSACTION_HISTORY = "Aatn transaction history";
    /**
     * 单据动态字段
     */
    public static final String AATN_DYNAMIC_COLUMN = "Dynamic Column";

    @Autowired
    public AatnSwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(AATN_TRANSFER_ORDER, "资产调拨转移单"),
                new Tag(AATN_HANDOVER_ORDER, "资产移交归还单"),
                new Tag(AATN_CHANGE_ORDER, "资产变更单"),
                new Tag(AATN_SCRAP_ORDER, "资产报废单"),
                new Tag(AATN_DISPOSE_ORDER, "资产处置单"),
                new Tag(AATN_HANDOVER_ORDER, "资产移交归还单"),
                new Tag(AATN_TRANSACTION_HISTORY, "资产事务履历"),
                new Tag(AATN_DYNAMIC_COLUMN, "单据动态字段")
        );
    }
}
