package com.huike.web.controller.clues;

import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.dto.TbActivityDTO;
import com.huike.clues.service.TbActivityService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动管理Controller
 *
 * @date 2023-04-01
 */

@RestController
@RequestMapping("/clues/activity")
@Api(tags = "活动管理")
public class TbActivityController extends BaseController {
  @Autowired
  private TbActivityService tbActivityService;

  @GetMapping("/list")
  @ApiOperation("分页查询活动管理列表")
  public TableDataInfo list(TbActivityDTO tbActivityDTO) {
    startPage();
    TableDataInfo tableDataInfo = tbActivityService.listPage(tbActivityDTO);
    return tableDataInfo;
  }

  @PostMapping
  @ApiOperation("新增活动")
  public AjaxResult save(@RequestBody TbActivityDTO tbActivityDTO) {
    return tbActivityService.saveAct(tbActivityDTO) ? success() : error();
  }

  @GetMapping("/{id:\\d+}")
  @ApiOperation("获取活动管理详细信息")
  public AjaxResult getById(@PathVariable Long id) {
    TbActivity activity = tbActivityService.selectById(id);
    return activity != null ? AjaxResult.success(activity) : error();
  }

  @PutMapping
  @ApiOperation("修改活动管理")
  public AjaxResult updateActById(@RequestBody TbActivityDTO activityDTO) {
    return tbActivityService.updateActById(activityDTO) ? success() : error();
  }

  @DeleteMapping("/{id:\\d+}")
  @ApiOperation("删除活动")
  public AjaxResult deleteActById(@PathVariable Long id) {
    return tbActivityService.deleteActById(id) ? success() : error();
  }
}
