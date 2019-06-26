package org.hzero.halm.rcv.api.controller.v1;

import io.swagger.annotations.Api;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.halm.rcv.config.ArcvSwaggerApiConfig;
import org.hzero.halm.rcv.domain.entity.AcceptanceRelation;
import org.hzero.halm.rcv.domain.repository.AcceptanceRelationRepository;
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

import java.util.List;

/**
 * 验收单关联 管理 API
 *
 * @author zhisheng.zhang@hand-china.com 2019-04-26 11:09:10
 */
@RestController("acceptanceRelationController.v1")
@RequestMapping("/v1/{organizationId}/acceptance-relations")
@Api(tags = ArcvSwaggerApiConfig.ARCV_ACCEPTANCE_RELATION)
public class AcceptanceRelationController extends BaseController {

    @Autowired
    private AcceptanceRelationRepository acceptanceRelationRepository;

    @ApiOperation(value = "创建验收单关联")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<AcceptanceRelation>> create(@RequestBody List<AcceptanceRelation> acceptanceRelationList) {
        acceptanceRelationRepository.batchInsertSelective(acceptanceRelationList);
        return Results.success(acceptanceRelationList);
    }

    @ApiOperation(value = "删除验收单关联")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<AcceptanceRelation> remove(@RequestBody AcceptanceRelation acceptanceRelation) {
        SecurityTokenHelper.validToken(acceptanceRelation);
        acceptanceRelationRepository.deleteByPrimaryKey(acceptanceRelation);
        return Results.success();
    }

}
