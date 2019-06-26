package org.hzero.halm.atn.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.atn.domain.entity.HandoverOrderHeader;

import java.util.Map;
import java.util.Set;

/**
 * 资产移交归还单头资源库
 *
 * @author sen.wang@hand-china.com 2019-03-20 16:49:03
 */
public interface HandoverOrderHeaderRepository extends BaseRepository<HandoverOrderHeader> {

    /**
     * 根据id查询归还单的头和行的数据。
     * @param id 头id
     * @return 包含头和行的明细数据。
     */
    HandoverOrderHeader selectDetail(Long id,Long tenantId);

    /**
     * 保存归还单的头和行数据。
     * 注意的点：
     * 1.校验是否满足事务类型的范围
     * 2.校验是否满足事务类型的规则，（包含动态字段）
     * 3.保存的时候要插入动态字段的数据。
     * @param header
     * @return
     */
    HandoverOrderHeader saveHandoverOrder(HandoverOrderHeader header,Long tenantId) throws IllegalAccessException;

    /**
     * 获取动态字段的规则类型
     * @param allFieldRules 所有字段的规则类型
     * @param constantSet 固定字段的集合
     * @return 结果
     */
    Map<String,String> getDynamicFieldRulesMap(Map<String,String> allFieldRules, Set constantSet);
}
