package org.hzero.halm.rcv.infra.constant;


/**
 * @author zhiguang.guo@hand-china.com 2019-04-10 12:45:35
 */
public interface ArcvConstants {


	interface ArcvErrorCode {
		/**
		 * 事件完整名称重复
		 */
		String ARCV_DUPLICATE_FULL_NAME = "acceptance_type_duplicate_full_name";
		/**
		 * 同租户内验收单编号重复
		 */
		String ARCV_DUPLICATE_ACCEPTANCE_NUM = "arcv_duplicate_acceptance_num";
		/**
		 * 编号不能为空，请检查编号规则
		 */
		String ARCV_ACCEPTANCE_NUM_IS_NULL = "arcv_acceptance_num_is_null";
		/**
		 * 验收单头ID不能为空
		 */
		String ARCV_ACCEPTANCE_HEADER_ID_IS_NULL = "arcv_acceptance_header_id_is_null";
		/**
		 * 资产编号不能为空
		 */
		String ARCV_ASSET_NUM_IS_NULL = "arcv_asset_num_is_null";
		/**
		 * 资产明细行数据未补全
		 */
		String ARCV_ASSET_DETAIL_DATA_NOT_COMPLETED = "arcv_asset_detail_data_not_completed";
		/**
		 * 资产编号在同租户内资产中重复
		 */
		String ARCV_DUPLICATE_ASSET_NUM_FROM_ASSET="arcv_duplicate_asset_num_from_asset";
		/**
		 * 资产编号在同租户内资产明细行中重复
		 */
		String ARCV_DUPLICATE_ASSET_NUM_FROM_ACCEPTANCE_ASSET="arcv_duplicate_asset_num_from_acceptance_asset";

	}

	interface ArcvLovCode {
		/**
		 * 验收基础类型
		 */
		String ACCEPTANCE_TYPE = "ARCV.ACCEPTANCE_TYPE";
		/**
		 * 是否启用
		 */
		String ENABLED_FLAG = "HPFM.ENABLED_FLAG";

	}

	interface acceptanceStatus {
		/**
		 * 新建
		 */
		String NEW = "NEW";
		/**
		 * 审批中
		 */
		String APPROVING = "APPROVING";
		/**
		 * 审批通过
		 */
		String APPROVED = "APPROVED";
		/**
		 * 审批拒绝
		 */
		String REFUSE = "REFUSE";
		/**
		 * 待完善资产
		 */
		String SUPPLEMENT = "SUPPLEMENT";
		/**
		 * 已完成
		 */
		String COMPLETED = "COMPLETED";
	}

}
