package org.hzero.halm.atn.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.atn.domain.entity.HandoverOrderLine;

import java.util.List;

/**
 * 资产移交归还单行资源库
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:48:48
 */
public interface HandoverOrderLineRepository extends BaseRepository<HandoverOrderLine> {
    /**
     * 保存移交归还单行的数据。
     * @param line
     * @return
     */
   HandoverOrderLine saveLine(HandoverOrderLine line,Long tenantId);
}
