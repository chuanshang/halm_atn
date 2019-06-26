package org.hzero.halm.afm.api.controller.v1;

import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.halm.afm.app.service.AttributeLineService;
import org.hzero.halm.afm.domain.entity.AttributeLine;
import org.hzero.halm.afm.domain.repository.AttributeLineRepository;
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

/**
 * 属性组行信息 管理 API
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:23
 */
@RestController("attributeLineController.v1")
@RequestMapping("/v1/{organizationId}/attribute-lines")
public class AttributeLineController extends BaseController {

    @Autowired
    private AttributeLineService attributeLineService;

    @Autowired
    private AttributeLineRepository attributeLineRepository;

    @ApiOperation(value = "属性组行信息列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<AttributeLine>> list(AttributeLine attributeLine, @ApiIgnore @SortDefault(value = AttributeLine.FIELD_LINE_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<AttributeLine> list = attributeLineService.pageAndSortBySetId(pageRequest,attributeLine.getAttributeSetId());
        return Results.success(list);
    }

}
