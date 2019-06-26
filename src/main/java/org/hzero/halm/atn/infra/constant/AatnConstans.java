package org.hzero.halm.atn.infra.constant;

public interface AatnConstans {

	/**
	 * 默认行编号基数
	 */
	Integer DEFAULT_LINE_NUMBER = 0;

	/**
	 * 字段变更描述文字模板
	 */
	String FIELD_CHANGE_DESCRIPTION_TEMPLATE = "field_change_description_template";

	/**
	 * 员工用户关系缓存key
	 */
	String USER_EMPLOYEE_CACHE_KEY = "hiam:user-employ:";

	/**
	 * 目标字段类型
	 */
	interface targetColumnType {
		/**
		 * LOV
		 */
		String LOV = "Lov";
		/**
		 * INPUT
		 */
		String INPUT = "Input";
		/**
		 * DATEPICKER
		 */
		String DATEPICKER = "DatePicker";
		/**
		 * CODE
		 */
		String IDP = "CODE";
		/**
		 * INPUTNUMBER
		 */
		String INPUTNUMBER = "InputNumber";
		/**
		 * INTEGER
		 */
		String INTEGER = "Integer";
		/**
		 * TEXTAREA
		 */
		String TEXTAREA = "TextArea";
		/**
		 * VALUELIST
		 */
		String VALUELIST = "ValueList";
		/**
		 * TIME
		 */
		String TIME = "TIME";
		/**
		 * BOOLEAN
		 */
		String BOOLEAN = "BOOLEAN";
	}

	/**
	 * 定义了可选范围的字段
	 */
	interface FieldsWithScope {
		/**
		 * 资产状态
		 */
		String ASSET_STATUS_ID = "asset_status_id";
		/**
		 * 所属组织
		 */
		String OWNING_ORG_ID = "owning_org_id";
		/**
		 * 资产专业分类
		 */
		String ASSET_SPECIALTY_ID = "asset_specialty_id";
	}

	interface AatnLovCode {
		/**
		 * 审批状态
		 */
		String APPROVE_STATUS = "HALM.APPROVE_STATUS";
		/**
		 * 动态字段lov
		 */
		String FIELD_LOV = "AATN.DYNAMIC_FIELD_LOV";
	}

	interface CodeRuleFields {
		/**
		 * 资产变更单编码
		 */
		String CHANGE_NUM = "changeNum";
	}

	interface CodeRules {
		/**
		 * 资产变更行编码规则
		 */
		String CHANGE_ORDER_LINE_NUM = "AATN.CHANGE_ORDER_LINE_NUM";
	}

	/**
	 * 审批状态
	 */
	interface ApproveStatus {
		/**
		 * 新建
		 */
		String NEW = "NEW";
		/**
		 * 未提交
		 */
		String DRAFT = "DRAFT";
		/**
		 * 审批中
		 */
		String APPROVING = "APPROVING";
		/**
		 * 已批准
		 */
		String APPROVED = "APPROVED";
		/**
		 * 进行中
		 */
		String PROCESSING = "PROCESSING";
		/**
		 * 拒绝
		 */
		String REJECTED = "REJECTED";
		/**
		 * 已完成
		 */
		String COMPLETED = "COMPLETED";
	}

	interface TransferOrderLineStatus {
		/**
		 * 新建
		 */
		String NEW = "NEW";
		/**
		 * 未处理
		 */
		String UNPROCESSED = "UNPROCESSED";
		/**
		 * 已处理
		 */
		String PROCESSED = "PROCESSED";
		/**
		 * 在途
		 */
		String ON_THE_WAY = "ON_THE_WAY";
		/**
		 * 已完成
		 */
		String COMPLETED = "COMPLETED";
	}

	interface ChangeOrderLineStatus {
		/**
		 * 新建
		 */
		String NEW = "NEW";
		/**
		 * 未处理
		 */
		String UNPROCESSED = "UNPROCESSED";
		/**
		 * 已处理
		 */
		String PROCESSED = "PROCESSED";
		/**
		 * 已完成
		 */
		String COMPLETED = "COMPLETED";
	}

	/**
	 * 对应值集 AATN_HANDOVER_LINE_STATUS
	 */
	interface HandoverStatus {
		/**
		 * 新建
		 */
		String NEW = "NEW";
		/**
		 * 未移交
		 */
		String NO_HANDED_OVER = "NO_HANDED_OVER";
		/**
		 * 移交未归还
		 */
		String HANDED_NO_RETURN = "HANDED_NO_RETURN";
		/**
		 * 已归还
		 */
		String RETURNED = "RETURNED";
		/**
		 * 已完成
		 */
		String COMPLETED = "COMPLETED";

	}

	/**
	 * 对应值集 AATN_SCRAP_LINE_STATUS
	 */
	interface ScrapStatus {
		/**
		 * 新建
		 */
		String NEW = "NEW";
		/**
		 * 待报废
		 */
		String WAIT_FOR_SCRAP = "WAIT_FOR_SCRAP";
		/**
		 * 移交未归还
		 */
		String SCRAPPED = "SCRAPPED";

	}

	interface AatnErrorCode {
		/**
		 * 当前租户下资产变更单编码重复
		 */
		String AATN_DUPLICATE_CHANGE_NUM = "aatn_duplicate_change_num";
		/**
		 * 当前租户下调拨转移单编码重复
		 */
		String AATN_DUPLICATE_TRANSFER_NUM = "aatn_duplicate_transfer_num";
		/**
		 * 当前租户下资产处置单编码重复
		 */
		String AATN_DUPLICATE_DISPOSE_NUM = "aatn_duplicate_dispose_num";
		/**
		 * 当前状态不可提交
		 */
		String AATN_DISPOSE_CURRENT_STATUS_IS_UNCOMMITTABLE = "current_status_is_uncommittable";
		/**
		 * 所选当前资产状态超出范围
		 */
		String AATN_CURRENT_ASSET_STATUS_OUT_OF_RANGE = "aatn_current_asset_status_out_of_range";
		/**
		 * 所选当前资产状态超出范围
		 */
		String AATN_ASSET_STATUS_OUT_OF_RANGE = "aatn_asset_status_out_of_range";
		/**
		 * 处置单当前资产状态超出范围
		 */
		String AATN_DISPOSE_CURRENT_ASSET_STATUS_OUT_OF_RANGE = "dispose_current_asset_status_out_of_range";
		/**
		 * 所选目标所属组织超出范围
		 */
		String AATN_OWNING_ORG_OUT_OF_RANGE = "aatn_owning_org_out_of_range";
		/**
		 * 所选目标资产专业超出范围
		 */
		String AATN_OWNING_MAJOR_OUT_OF_RANGE = "aatn_owning_major_out_of_range";
		/**
		 * 所属特殊资产不在范围内
		 */
		String AATN_SPECIAL_ASSET_OUT_OF_RANGE = "aatn_special_asset_out_of_range";
		/**
		 * 字段变更不符合规则
		 */
		String AATN_FIELD_CHANGE_MISMATCH_RULE = "aatn_field_change_mismatch_rule";
		/**
		 * 提交确认的行处理状态不正确
		 */
		String AATN_PROCESS_STATUS_INVALID = "aatn_process_status_invalid";
		/**
		 * 该状态下的数据不能被执行
		 */
		String AATN_HANDOVER_STATUS_INVALID = "aatn_handover_status_invalid";
		/**
		 * 该移交归还行下明细数据错误
		 */
		String AATN_HANDOVER_DETAIL_INVALID = "aatn_handover_detail_invalid";
		/**
		 * 该状态下的移交归还行不能删除
		 */
		String AATN_HANDOVER_DELETE_INVALID = "aatn_handover_delete_invalid";
		/**
		 * 该移交归还行选择的资产不在事务类型的范围内
		 */
		String AATN_HANDOVER_ASSET_INVALID = "aatn_handover_asset_invalid";
		String AATN_HANDOVER_ASSET_NOT_EXISTS = "aatn_handover_asset_not_exists";
		/**
		 * 单据编码为空
		 */
		String AATN_NUM_IS_EMPTY = "aatn_num_is_empty";
		/**
		 * 当前事务处理单据行所选择资产已存在
		 */
		String AATN_DUPLICATE_ASSET_ID = "aatn_duplicate_asset_id";
		/**
		 * 剩余折旧日期不能为空
		 */
		String AATN_SCRAP_ORDER_LINE_REMAIN_DEPRECIATION_MOUTH_IS_NULL = "remain_depreciation_mouth_is_null";
		/**
		 * 行描述不能为空
		 */
		String AATN_SCRAP_ORDER_LINE_DESCRIPTION_IS_NULL = "description_is_null";
		/**
		 * 行处理状态不能为空
		 */
		String AATN_SCRAP_ORDER_LINE_PROCESS_STATUS_IS_NULL = "process_status_is_null";
		/**
		 * 剩余价值不能为空
		 */
		String AATN_SCRAP_ORDER_LINE_REMAIN_COST_IS_NULL = "remain_cost_is_null";
		/**
		 * 当前资产处置单行处理状态不能被删除
		 */
		String AATN_DISPOSE_LINE_PROCESS_STATUS_INVALID = "dispose_line_process_status_invalid";
		/**
		 * 资产事务类型的状态范围不能为空
		 */
		String AATN_TRANSACTION_TYPE_STATUS_SCOPE_IS_NULL = "transaction_type_status_scope_is_null";
		/**
		 * 当前资产报废单行处理状态不能被删除
		 */
		String AATN_SCRAP_LINE_PROCESS_STATUS_INVALID = "scrap_line_process_status_invalid";
		/**
		 * 事务类型没有设置对应目标状态
		 */
		String TRANSACTION_TYPE_NO_TARGET_STATUS = "transaction_type_no_target_status";
	}

	/**
	 * 字段变更规则
	 */
	interface AatnChangeRule {
		/**
		 * 仅显示,禁止使用或修改此字段
		 */
		String READ_ONLY = "READ_ONLY";
		/**
		 * 必须修改
		 */
		String MUST_MODIFY = "MUST_MODIFY";
		/**
		 * 任意,可按业务场景需要修改
		 */
		String ARBITRARY_MODIFY = "ARBITRARY_MODIFY";
		/**
		 * 清空
		 */
		String CLEAN = "CLEAN";
	}

	/**
	 * 单据类型
	 */
	interface TransactionType {
		/**
		 * 资产调拨转移
		 */
		String TRANSFER = "TRANSFER";
		/**
		 * 资产移出归还
		 */
		String RETURN = "RETURN";
		/**
		 * 资产报废及处置
		 */
		String SCRAP = "SCRAP";
		/**
		 * 资产信息变更
		 */
		String UPDATE = "UPDATE";
		/**
		 * 资产处置
		 */
		String DISPOSE = "DISPOSE";
	}

	interface FieldMessageDes{
		String CURRENT = "current";
		String CURRENT_MEANING = "current_meaning";
		String TARGET = "target";
		String TARGET_MEANING = "target_meaning";
	}
}
