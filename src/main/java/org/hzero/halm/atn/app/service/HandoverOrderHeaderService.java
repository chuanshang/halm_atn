package org.hzero.halm.atn.app.service;

import org.hzero.halm.atn.domain.entity.HandoverOrderHeader;

/**
 * 资产移交归还单头应用服务
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:03
 */
public interface HandoverOrderHeaderService {

    /**
     * 提交按钮
     * @param handoverOrderHeader 移交归还头
     */
    void submitHandover(HandoverOrderHeader handoverOrderHeader);

    /**
     * 根据行数据，刷新头状态
     * @param headerId headerId
     */
    void updateHandoverStatus(Long headerId);
}
