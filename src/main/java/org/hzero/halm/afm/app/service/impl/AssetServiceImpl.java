package org.hzero.halm.afm.app.service.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.hzero.core.base.BaseConstants;
import org.hzero.halm.afm.app.service.AssetService;
import org.hzero.halm.afm.domain.entity.Asset;
import org.hzero.halm.afm.domain.entity.AssetLinear;
import org.hzero.halm.afm.domain.repository.AssetLinearRepository;
import org.hzero.halm.afm.domain.repository.AssetRepository;
import org.hzero.halm.afm.infra.constant.Constants;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资产/设备基本信息应用服务默认实现
 *
 * @author zhisheng.zhang@hand-china.com 2019-01-24 16:43:37
 */
@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	private AssetRepository assetRepository;
	@Autowired
	private AssetLinearRepository assetLinearRepository;

	@Override
	public Asset insertAsset(Long tenantId, Asset asset) {
		//唯一性验证及必输校验
		validUniqueFields(asset);
		assetRepository.insertSelective(asset);
		AssetLinear assetLinear = asset.getAssetLinear();
		if (assetLinear == null) {
			assetLinear = new AssetLinear();
		}
		assetLinear.setTenantId(tenantId);
		assetLinear.setAssetId(asset.getAssetId());
		assetLinearRepository.insertSelective(assetLinear);
		asset.setAssetLinear(assetLinear);
		return asset;
	}

	@Override
	public Asset updateAsset(Asset asset) {
		//唯一性验证
		validUniqueFields(asset);
		assetRepository.updateByPrimaryKey(asset);
		AssetLinear assetLinear = asset.getAssetLinear();
		if (assetLinear != null) {
			assetLinear.setTenantId(asset.getTenantId());
			assetLinear.setAssetId(asset.getAssetId());
			assetLinearRepository.updateByPrimaryKey(assetLinear);
			asset.setAssetLinear(assetLinear);
		}
		return asset;
	}

	@Override
	public Asset deleteAsset(Asset asset) {
		assetRepository.deleteByPrimaryKey(asset);
		AssetLinear assetLinear = new AssetLinear();
		assetLinear.setTenantId(asset.getTenantId());
		assetLinear.setAssetId(asset.getAssetId());
		AssetLinear assetLinearData = assetLinearRepository.selectOne(assetLinear);
		if (assetLinearData != null) {
			assetLinearRepository.deleteByPrimaryKey(assetLinearData);
		}
		return asset;
	}

	@Override
	public Page<Asset> pageAsset(PageRequest pageRequest, Long organizationId, Asset asset) {
		asset.setTenantId(organizationId);
		return PageHelper.doPageAndSort(pageRequest, () -> {
			return assetRepository.selectAssetList(asset);
		});
	}

	@Override
	public Page<Asset> selectAssetByDetailCondition(PageRequest pageRequest, Long organizationId, String condition, String organizationScope, String statusScope, String specialtyScope) {

		return PageHelper.doPageAndSort(pageRequest, () -> {
			return assetRepository.selectAssetByDetailCondition(organizationId,condition,organizationScope,statusScope,specialtyScope);
		});
	}

	@Override
	public Asset assetDetail(Long organizationId, Long assetId) {
		Asset asset = new Asset();
		asset.setAssetId(assetId);
		asset.setTenantId(organizationId);
		List<Asset> list = assetRepository.selectAssetList(asset);

		System.out.println(list);
		//查询头 一条则返回 多条则报错
		if (list.size() == 1) {
			asset = list.get(0);
		} else {
			throw new CommonException(Constants.AafmErrorCode.SELECT_ASSET_BY_ASSET_ID_FAILED);
		}

		//查询行
		List<AssetLinear> assetLinearaList = assetLinearRepository.selectByCondition(
				Condition.builder(AssetLinear.class)
						.andWhere(
								Sqls.custom()
										.andEqualTo(AssetLinear.FIELD_ASSET_ID, asset.getAssetId())
										.andEqualTo(AssetLinear.FIELD_TENANT_ID, asset.getTenantId())
						).build()
		);
		if (assetLinearaList.size() == 1) {
			asset.setAssetLinear(assetLinearaList.get(0));
		} else {
			throw new CommonException(Constants.AafmErrorCode.SELECT_LINEAR_BY_ASSET_ID_FAILED);
		}

		return asset;
	}

	@Override
	public void validUniqueFields(Asset asset) {
		// 组装条件语句
		Sqls listAssetOfNumSql = Sqls.custom()
				.andEqualTo(Asset.FIELD_TENANT_ID, asset.getTenantId())
				.andEqualTo(Asset.FIELD_ASSET_NUM, asset.getAssetNum());
		Sqls listAssetOfNameSql = Sqls.custom()
				.andEqualTo(Asset.FIELD_TENANT_ID, asset.getTenantId())
				.andEqualTo(Asset.FIELD_ASSET_NAME, asset.getName());
		if (asset.getAssetId() != null) {
			listAssetOfNumSql = listAssetOfNumSql.andNotEqualTo(Asset.FIELD_ASSET_ID, asset.getAssetId());
			listAssetOfNameSql = listAssetOfNameSql.andNotEqualTo(Asset.FIELD_ASSET_ID, asset.getAssetId());
		}
		if (assetRepository.selectByCondition(Condition.builder(Asset.class)
				.andWhere(listAssetOfNumSql)
				.build()).size() > 0) {
			throw new CommonException(Constants.AafmErrorCode.DUPLICATE_ASSET_NUM);
		}
		if (assetRepository.selectByCondition(Condition.builder(Asset.class)
				.andWhere(listAssetOfNameSql)
				.build()).size() > 0) {
			throw new CommonException(Constants.AafmErrorCode.DUPLICATE_NAME);
		}
	}
}
