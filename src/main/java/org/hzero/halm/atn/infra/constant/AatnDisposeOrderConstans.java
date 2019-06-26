package org.hzero.halm.atn.infra.constant;

public interface AatnDisposeOrderConstans {


    interface AatnErrorCode {
        /**
         * 当前状态不能进行确认处置
         */
       String CURRENT_STATUS_CANNOT_CONFIRMED = "current_status_cannot_confirmed";
    }
    /**
     * 对应值集 AATN_DISPOSE_HEAD_STATUS
     */
    interface DisposeHeadStatus{
        /**
         * 新建
         */
        String NEW = "NEW";
        /**
         * 审批中
         */
        String APPROVING = "APPROVING";
        /**
         * 已审批
         */
        String APPROVED = "APPROVED";
        /**
         * 进行中
         */
        String PROCESSING = "PROCESSING";
        /**
         * 已完成
         */
        String COMPLETED = "COMPLETED";

    }
    /**
     * 对应值集 AATN.DISPOSE_LINE_STATUS
     */
    interface DisposeLineStatus{
        /**
         * 新建
         */
        String NEW = "NEW";
        /**
         * 待处置
         */
        String PENDING_DISPOSAL = "PENDING_DISPOSAL";
        /**
         * 已处置
         */
        String DISPOSED = "DISPOSED";
        /**
         * 已完成
         */
        String COMPLETED = "COMPLETED";

    }
    /**
     * 资产处置类型
     * 对应值集 AATN.DISPOSE_TYPE
     */
    interface DisposeType{
        /**
         * 二手变卖
         */
        String SECOND_HAND_SALE = "SECOND_HAND_SALE";
        /**
         * 废品低值
         */
        String SCRAP_LOW_VALUE = "SCRAP_LOW_VALUE";

    }
}
