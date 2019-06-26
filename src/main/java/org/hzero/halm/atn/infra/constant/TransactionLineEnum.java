package org.hzero.halm.atn.infra.constant;

import io.choerodon.core.exception.CommonException;
import org.hzero.halm.atn.domain.entity.*;

/**
 * 事务处理状态值集的枚举
 *
 * @author wangsen
 */
public enum TransactionLineEnum {

    /**
     * 资产信息变更
     */
    CHANGE_ORDER_LINE("AATN.CHANGE_ORDER_LINE_STATUS", "changeHeaderId", "changeLineId"),
    /**
     * 资产处置处理
     */
    DISPOSE_LINE("AATN.DISPOSE_LINE_STATUS", "disposeHeaderId", "disposeLineId"),
    /**
     * 移交归还处理
     */
    HANDOVER_LINE("AATN.HANDOVER_LINE_STATUS", "handoverHeaderId", "handoverLineId"),
    /**
     * 资产报废处理
     */
    SCRAP_ORDER_LINE("AATN.SCRAP_ORDER_LINE_STATUS", "scrapHeaderId", "scrapLineId"),
    /**
     * 调拨转移处理
     */
    TRANSFER_ORDER_LINE("AATN.TRANSFER_ORDER_LINE_STATUS", "transferHeaderId", "transferLineId");


    private String processLovCode;
    private String headerIdFieldName;
    private String lineIdFieldName;

    static String errorMsg = "找不到对应事务处理行的类型";

    TransactionLineEnum(String processLovCode, String headerIdFieldName, String lineIdFieldName) {
        this.processLovCode = processLovCode;
        this.headerIdFieldName = headerIdFieldName;
        this.lineIdFieldName = lineIdFieldName;
    }


    public String getProcessLovCode() {
        return processLovCode;
    }

    public String getHeaderIdFieldName() {
        return headerIdFieldName;
    }

    public String getLineIdFieldName() {
        return lineIdFieldName;
    }


    public static TransactionLineEnum getTheLineEnum(Class c) {
        if (c == ChangeOrderLine.class) {
            return CHANGE_ORDER_LINE;
        } else if (c == DisposeOrderLine.class) {
            return DISPOSE_LINE;
        } else if (c == HandoverOrderLine.class) {
            return HANDOVER_LINE;
        } else if (c == ScrapOrderLine.class) {
            return SCRAP_ORDER_LINE;
        } else if (c == TransferOrderLine.class) {
            return TRANSFER_ORDER_LINE;
        } else {
            throw new CommonException(errorMsg);
        }
    }


}