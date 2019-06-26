package org.hzero.halm.fam.infra.constant;

/**
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
public interface Constants {

	Long DEFAULT_LEVEL_NUMBER = 0L;
	Integer ONE = Integer.valueOf(1);
	Integer TWELVE = Integer.valueOf(12);

	interface AafmErrorCode {
		/**
		 * PARENT ID 对应的层级不存在
		 */
		String PARENT_ID_HAS_NO_LEVEL = "parent_id_has_no_level";
		/**
		 * 当前记录下含有子记录，不可以删除
		 */
		String THE_RECORD_CANNOT_BE_DELETED = "the_record_cannot_be_deleted";
		/**
		 * 父级未启用
		 */
		String PARENT_NOT_ENABLE = "parent_not_enable";
		/**
		 * 残值率应在0到100范围内
		 */
		String RESIDUAL_VALUE_RATE_NOT_IN_SCOPE = "residual_value_rate_not_in_scope";
		/**
		 * 折旧月份应在1到12范围内
		 */
		String DEPRECIATION_MONTH_NOT_IN_SCOPE = "depreciation_month_not_in_scope";

	}
	interface AfamErrorCode {
		/**
		 * 固定资产名称重复
		 */
		String FIXED_ASSETS_NAME_DUPLICATED = "fixed_assets_name_duplicated";
	}

	/**
	 * 固定资产价值变动类型 对应值集 AFAM.ASSET_CHANGE_TYPE
	 */
	interface FixedAssetsChangeTypeCode{
		/**
		 * 增加
		 */
		String ADD = "ADD";
		/**
		 * 追加
		 */
		String APPEND = "APPEND";
		/**
		 * 折旧
		 */
		String DEPRECIATION = "DEPRECIATION";
		/**
		 * 计划外折旧
		 */
		String UNPLANNED_DEPRECIATION = "UNPLANNED_DEPRECIATION";
		/**
		 * 转移
		 */
		String TRANSFER = "TRANSFER";
		/**
		 * 报废
		 */
		String SCRAP = "SCRAP";

	}
}