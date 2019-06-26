package org.hzero.halm.afm.infra.constant;

/**
 * description
 *
 * @author zhisheng.zhang@hand-china.com
 */
public interface Constants {

	String DEFAULT_PARENT_ID = "0";
	Integer DEFAULT_LEVEL_NUMBER = 0;

	interface AssetClassify {
		/**
		 * 所有
		 */
		String ALL = "all";
		/**
		 * 基础信息
		 */
		String BASE_MSG = "base_msg";
		/**
		 * 管理跟踪
		 */
		String MANAGE_TRACK = "manage_track";
		/**
		 * 属性说明
		 */
		String ATTRIBUTE_DES = "attribute_des";
		/**
		 * 源码跟踪
		 */
		String SOURCE_TRACK = "source_track";
	}

	interface NameplateRule {
		/**
		 * 手动输入指定铭牌号
		 */
		String MANUAL_INPUT_NUM = "MANUAL_INPUT_NUM";
		/**
		 * =资产编号
		 */
		String ASSET_NUM = "ASSET_NUM";
		/**
		 * =序列号
		 */
		String SERIES_NUM = "SERIES_NUM";
		/**
		 * =车架号
		 */
		String VEHICLE_NUM = "VEHICLE_NUM";
		/**
		 * =发动机号
		 */
		String ENGINE_NUM = "ENGINE_NUM";
		/**
		 * =其他跟踪编号
		 */
		String OTHER_NUM = "OTHER_NUM";
	}

	interface AssetSource {
		/**
		 * 购置
		 */
		String PURCHASE = "PURCHASE";
		/**
		 * 捐赠获得
		 */
		String DONATE = "DONATE";
		/**
		 * 建造/开发
		 */
		String BUILD_AND_DEVELOPMENT = "BUILD_AND_DEVELOPMENT";
		/**
		 * 盘盈
		 */
		String INVENTORY_PROFIT = "INVENTORY_PROFIT";
		/**
		 * 内部调配
		 */
		String INTERNAL_DEPLOYMENT = "INTERNAL_DEPLOYMENT";
		/**
		 * 经营租赁
		 */
		String OPERATING_LEASE = "OPERATING_LEASE";
		/**
		 * 临时借入
		 */
		String TEMPORARY_BORROW = "TEMPORARY_BORROW";
		/**
		 * 厂家投放
		 */
		String FACTORY_DELIVERY = "FACTORY_DELIVERY";
		/**
		 * 其他
		 */
		String OTHERS = "OTHERS";
	}

	interface AafmErrorCode {
		/**
		 * 同租户内资产编号重复
		 */
		String DUPLICATE_ASSET_NUM = "duplicate_asset_num";
		/**
		 * 同租户内可视标签/铭牌重复
		 */
		String DUPLICATE_NAME = "duplicate_name";
		/**
		 * 查询资产失败
		 */
		String SELECT_ASSET_BY_ASSET_ID_FAILED = "select_asset_by_asset_id_failed";
		/**
		 * 查询资产行失败
		 */
		String SELECT_LINEAR_BY_ASSET_ID_FAILED = "select_linear_by_asset_id_failed";

		/**
		 * 产品类别名重复
		 */
		String CATEGORY_NAME_DUPLICATED = "category_name_duplicated";
		/**
		 * 产品类别代码重复
		 */
		String CATEGORY_CODE_DUPLICATED = "category_code_duplicated";
		/**
		 * 父级不可用
		 */
		String AAFM_PARENT_CATEGORY_NOT_ENABLE = "aafm_parent_category_not_enable";

		/**
		 * 优先级重复
		 */
		String AAFM_ASSET_TO_ORG_PRIORITY = "asset_to_org_priority_duplicated";
		/**
		 * 专业分类名称重复
		 */
		String AAFM_ASSET_SPECIALTY_NAME_DUPLICATED = "asset_specialty_name_duplicated";
		/**
		 * 启用编号后编码规则为空
		 */
		String EMPTY_CODE_RULE = "empty_code_rule";
		/**
		 * 资产状态名称重复
		 */
		String DUPLICATE_ASSET_STATUS_NAME = "duplicate_asset_status_name";
		/**
		 * 资产组编号重复
		 */
		String ASSET_SET_NUM_DUPLICATED = "asset_set_num_duplicated";
		/**
		 * 资产组名字重复
		 */
		String ASSET_SET_NAME_DUPLICATED = "asset_set_name_duplicated";

		/**
		 * 属性组名重复
		 */
		String ATTRIBUTE_SET_NAME_DUPLICATED = "attribute_set_name_duplicated";
		/**
		 * 属性名重复
		 */
		String ATTRIBUTE_LINE_NAME_DUPLICATED = "attribute_line_name_duplicated";
		/**
		 * 属性行号重复
		 */
		String ATTRIBUTE_LINE_NUMBER_DUPLICATED = "attribute_line_number_duplicated";
		/**
		 * 当attribute_type为值集时，值集编码必输
		 */
		String ATTRIBUTE_LINE_LOV_VALUE_IS_REQUIRED = "attribute_line_lov_value_is_required";
		/**
		 * 事件类型代码重复
		 */
		String TRANSACTION_TYPE_CODE_DUPLICATED = "transaction_type_code_duplicated";
		/**
		 * 事件类型代码重复
		 */
		String TRANSACTION_TYPE_ENABLED = "transaction_type_enabled";
		/**
		 * 资产动态字段重复
		 */
		String ASSET_DYNAMIC_COLUMN_DUPLICATED = "asset_dynamic_column_duplicated";
		/**
		 * 事件类型字段重复
		 */
		String TRANSACTION_TYPE_FIELD_COLUMN_DUPLICATED = "transaction_type_field_column_duplicated";
	}
}
