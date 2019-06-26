package org.hzero.halm.fam.api.controller.v1;

import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.halm.fam.domain.entity.FixedAssetsChanges;
import org.hzero.halm.fam.domain.repository.FixedAssetsChangesRepository;
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
 * 固定资产价值变动 管理 API
 *
 * @author qing.huang@hand-china.com 2019-04-10 11:23:27
 */
@RestController("fixedAssetsChangesController.v1")
@RequestMapping("/v1/fixed-assets-changes")
public class FixedAssetsChangesController extends BaseController {

}
