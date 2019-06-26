package org.hzero.halm.rcv.domain.repository;

import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.mybatis.base.BaseRepository;
import org.hzero.halm.rcv.domain.entity.AcceptanceHeader;
import io.choerodon.core.domain.Page;

import java.util.List;

/**
 * 验收单资源库
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
public interface AcceptanceHeaderRepository extends BaseRepository<AcceptanceHeader> {
	/**
	 * 验收单行查询
	 *
	 * @param pageRequest      分页参数
	 * @param acceptanceHeader 查询条件
	 * @return 返回结果
	 */
	Page<AcceptanceHeader> listAcceptanceHeader(PageRequest pageRequest, AcceptanceHeader acceptanceHeader);

	/**
	 * 全文检索模糊查询验收单列表
	 *
	 * @param pageRequest 分页参数
	 * @param tenantId 租户id
	 * @param condition 检索条件
	 * @return 验收单列表数据
	 */
	Page<AcceptanceHeader> listAcceptanceHeaderByCondition(PageRequest pageRequest, Long tenantId, String condition);

	/**
	 * 查询验收单明细
	 *
	 * @param tenantId           租户id
	 * @param acceptanceHeaderIds 验收单头id集合
	 * @return 验收单明细数据
	 */
	List<AcceptanceHeader> detailAcceptance(Long tenantId, String acceptanceHeaderIds);
}
