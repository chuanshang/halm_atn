package org.hzero.halm.atn.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzero.halm.atn.domain.entity.ScrapOrderHeader;
import io.choerodon.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 资产报废单头Mapper
 *
 * @author wen.luo@hand-china.com 2019-03-22 15:25:50
 */
public interface ScrapOrderHeaderMapper extends BaseMapper<ScrapOrderHeader> {

	/**
	 * 查询资产报废单头列表
	 *
	 * @param scrapOrderHeader 查询参数
	 * @return 查询结果
	 */
	List<ScrapOrderHeader> selectScrapOrderHeader(ScrapOrderHeader scrapOrderHeader);

	/**
	 * 全文检索查询资产报废单头列表
	 *
	 * @param tenantId 租户ID
	 * @param content  检索内容
	 * @return 所有
	 */
	List<ScrapOrderHeader> selectScrapOrderHeaderByContent(@Param("tenantId") Long tenantId, @Param("content") String content);
}
