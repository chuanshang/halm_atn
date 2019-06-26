package org.hzero.halm.afm.api.controller.v1;

import io.choerodon.swagger.annotation.CustomPageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.hzero.boot.platform.lov.annotation.ProcessLovValue;
import org.hzero.core.base.BaseConstants;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.export.annotation.ExcelExport;
import org.hzero.export.vo.ExportParam;
import org.hzero.halm.afm.api.dto.AttributeSetDTO;
import org.hzero.halm.afm.app.service.AttributeLineService;
import org.hzero.halm.afm.app.service.AttributeSetService;
import org.hzero.halm.afm.config.AafmSwaggerApiConfig;
import org.hzero.halm.afm.domain.entity.AttributeLine;
import org.hzero.halm.afm.domain.entity.AttributeSet;
import org.hzero.halm.afm.domain.repository.AttributeLineRepository;
import org.hzero.halm.afm.domain.repository.AttributeSetRepository;
import org.hzero.halm.afm.domain.vo.AttributeSetDetailExportVO;
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
 * 属性组头信息 管理 API
 *
 * @author qing.huang@hand-china.com 2019-01-11 10:54:24
 */
@RestController("attributeSetController.v1")
@RequestMapping("/v1/{organizationId}/attribute-sets")
@Api(tags = AafmSwaggerApiConfig.ATTRIBUTE_SET)
public class AttributeSetController extends BaseController {

    @Autowired
    private AttributeSetService attributeSetService;

    @Autowired
    private AttributeSetRepository attributeSetRepository;

    @Autowired
    private AttributeLineRepository attributeLineRepository;
    @Autowired
    private AttributeLineService attributeLineService;

    @ApiOperation(value = "属性组头信息查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    //@ProcessLovValue(targetField = {BaseConstants.FIELD_BODY, "body.attributeLinesList"})
    @CustomPageRequest
    public ResponseEntity<Page<AttributeSet>> list(AttributeSetDTO attributeSet,
                                                   @ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
                                                   @ApiIgnore @SortDefault(value = AttributeSet.FIELD_ATTRIBUTE_SET_ID,
                                                           direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<AttributeSet> list = attributeSetService.pageAttributeSet(pageRequest, organizationId, attributeSet);
        return Results.success(list);
    }

    @ApiOperation(value = "属性组明细查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{attributeSetId}")
    @CustomPageRequest
    public ResponseEntity<AttributeSet> detail(@PathVariable Long attributeSetId,
                                               @ApiIgnore @SortDefault(value = AttributeSet.FIELD_ATTRIBUTE_SET_ID,
                                                       direction = Sort.Direction.DESC) PageRequest pageRequest) {
        AttributeSet attributeSet = attributeSetService.attributeSetDetail(pageRequest, attributeSetId);
        return Results.success(attributeSet);
    }

    @ApiOperation(value = "属性组头信息保存")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<List<AttributeSet>> batchCreateAndUpdate(@ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
                                                                   @RequestBody List<AttributeSet> attributeSets) {
        //验证
        SecurityTokenHelper.validTokenIgnoreInsert(attributeSets);
        this.validList(attributeSets);
        for (AttributeSet attributeSet : attributeSets) {
            this.validList(attributeSet.getAttributeLinesList());
        }
        attributeSetService.batchCreateAndUpdate(organizationId, attributeSets);
        return Results.success(attributeSets);
    }

    @ApiOperation(value = "属性组行信息列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
    @GetMapping("/{attributeSetId}/line")
    @CustomPageRequest
    public ResponseEntity<Page<AttributeLine>> lineList(@PathVariable Long attributeSetId, @ApiIgnore @SortDefault(value = AttributeLine.FIELD_LINE_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<AttributeLine> list = attributeLineService.pageAndSortBySetId(pageRequest, attributeSetId);
        return Results.success(list);
    }

    /**
     * 属性组导出\勾选导出—属性组
     *
     * @param organizationId
     * @param exportParam    excel参数
     * @param response       response
     * @return
     */
    @ApiOperation(value = "属性组导出\\勾选导出—属性组")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/export")
    @ExcelExport(AttributeSetDetailExportVO.class)
    public ResponseEntity<List<AttributeSetDetailExportVO>> exportAttributeSet(@PathVariable("organizationId") Long organizationId,
                                                                               AttributeSetDTO attributeSet,
                                                                               ExportParam exportParam,
                                                                               HttpServletResponse response
    ) {
        attributeSet.setTenantId(organizationId);
        return Results.success(attributeSetService.exportAttributeSetByIds(attributeSet));
    }

}
