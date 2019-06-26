package org.hzero.halm.rcv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
@Configuration
public class ArcvSwaggerApiConfig {


	/**
	 * 验收单类型
	 */
	public static final String ARCV_ACCEPTANCE_TYPE = "Acceptance Type";

	/**
	 * 交付清单行
	 */
	public static final String ARCV_DELIVERY_LIST = "Delivery List";

	/**
	 * 验收单资产明细
	 */
	public static final String ARCV_ACCEPTANCE_ASSET = "Acceptance Asset";
	/**
	 * 	验收单关联
	 */
	public static final String ARCV_ACCEPTANCE_RELATION = "Acceptance Relation";
	/**
	 * 	验收单行
	 */
	public static final String ARCV_ACCEPTANCE_LINE = "Acceptance Line";
	/**
	 * 	验收单
	 */
	public static final String ARCV_ACCEPTANCE_HEADER = "Acceptance Header";

	@Autowired
	public ArcvSwaggerApiConfig(Docket docket) {

		docket.tags(
				new Tag(ARCV_ACCEPTANCE_TYPE, "验收单类型"),
				new Tag(ARCV_DELIVERY_LIST, "交付清单行"),
				new Tag(ARCV_ACCEPTANCE_ASSET, "验收单资产明细"),
				new Tag(ARCV_ACCEPTANCE_RELATION, "验收单关联"),
				new Tag(ARCV_ACCEPTANCE_LINE, "验收单行"),
				new Tag(ARCV_ACCEPTANCE_HEADER, "验收单")
		);
	}

}
