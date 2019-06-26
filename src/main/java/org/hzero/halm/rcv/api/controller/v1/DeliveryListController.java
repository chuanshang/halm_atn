package org.hzero.halm.rcv.api.controller.v1;

import io.choerodon.swagger.annotation.CustomPageRequest;
import io.swagger.annotations.Api;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.boot.platform.lov.handler.LovValueHandle;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.rcv.config.ArcvSwaggerApiConfig;
import org.hzero.halm.rcv.domain.entity.DeliveryList;
import org.hzero.halm.rcv.domain.repository.DeliveryListRepository;
import org.hzero.halm.rcv.infra.mapper.DeliveryListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.hzero.mybatis.helper.SecurityTokenHelper;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 交付清单行 管理 API
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-17 16:06:43
 */
@RestController("deliveryListController.v1")
@RequestMapping("/v1/{organizationId}/delivery-list")
@Api(tags = ArcvSwaggerApiConfig.ARCV_DELIVERY_LIST)
public class DeliveryListController extends BaseController {

	@Autowired
	private DeliveryListRepository deliveryListRepository;

	@Autowired
	private DeliveryListMapper deliveryListMapper;

	@Autowired
	private LovValueHandle lovValueHandle;

	@ApiOperation(value = "创建交付清单行")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PostMapping
	public ResponseEntity<DeliveryList> createDeliveryList(@PathVariable("organizationId") Long tenantId,
														   @RequestBody DeliveryList deliveryList) {
		deliveryList.setTenantId(tenantId);
		validObject(deliveryList);
		deliveryListRepository.insertSelective(deliveryList);
		return Results.success(deliveryList);
	}

	@ApiOperation(value = "修改交付清单行")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@PutMapping
	public ResponseEntity<DeliveryList> updateDeliveryList(@PathVariable("organizationId") Long tenantId,
														   @RequestBody DeliveryList deliveryList) {
		deliveryList.setTenantId(tenantId);
		SecurityTokenHelper.validToken(deliveryList);
		deliveryListRepository.updateByPrimaryKeySelective(deliveryList);
		return Results.success(deliveryList);
	}

	@ApiOperation(value = "交付清单行列表")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@CustomPageRequest
	@GetMapping
	public ResponseEntity<Page<DeliveryList>> listDelivery(@PathVariable("organizationId") Long tenantId,
														   DeliveryList deliveryList,
														   @ApiIgnore @SortDefault(value = DeliveryList.FIELD_DELIVERY_LIST_ID,
																   direction = Sort.Direction.DESC) PageRequest pageRequest) {
		deliveryList.setTenantId(tenantId);
		Page<DeliveryList> list = deliveryListRepository.listDeliveryList(pageRequest, deliveryList);
		return Results.success(list);
	}

	@ApiOperation(value = "交付清单行全文检索")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@CustomPageRequest
	@GetMapping("/list")
	public ResponseEntity<Page<DeliveryList>> retrieveDeliveryList(@PathVariable("organizationId") Long tenantId,
																   String condition,
																   @ApiIgnore @SortDefault(value = DeliveryList.FIELD_DELIVERY_LIST_ID,
																		   direction = Sort.Direction.DESC) PageRequest pageRequest) {
		Page<DeliveryList> list = deliveryListRepository.retrieveDeliveryList(pageRequest, tenantId, condition);
		return Results.success(list);
	}

	@ApiOperation(value = "交付清单行明细")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
	@GetMapping("/{deliveryListId}")
	public ResponseEntity<DeliveryList> detailDeliveryList(@PathVariable("organizationId") Long tenantId, @PathVariable Long deliveryListId) {
		DeliveryList deliveryList = new DeliveryList();
		deliveryList.setTenantId(tenantId);
		deliveryList.setDeliveryListId(deliveryListId);
		return Results.success(deliveryListMapper.listDeliveryList(deliveryList).get(0));
	}

	@ApiOperation(value = "交付清单行导出")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@ExcelExport(DeliveryList.class)
	@GetMapping("/export")
	public ResponseEntity<List<DeliveryList>> exportDeliveryList(@PathVariable("organizationId") Long tenantId,
																 DeliveryList deliveryList,
																 HttpServletResponse httpServletResponse,
																 ExportParam exportParam) {
		deliveryList.setTenantId(tenantId);
		return Results.success((List<DeliveryList>) lovValueHandle.process(null, deliveryListMapper.listDeliveryList(deliveryList)));
	}


	@ApiOperation(value = "删除交付清单行")
	@Permission(level = ResourceLevel.ORGANIZATION)
	@DeleteMapping
	public ResponseEntity<DeliveryList> removeDeliveryList(@RequestBody DeliveryList deliveryList) {
		SecurityTokenHelper.validToken(deliveryList);
		deliveryListRepository.deleteByPrimaryKey(deliveryList);
		return Results.success();
	}

}
