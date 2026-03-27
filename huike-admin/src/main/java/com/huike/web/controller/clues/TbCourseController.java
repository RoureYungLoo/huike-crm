package com.huike.web.controller.clues;

import com.huike.clues.domain.TbCourse;
import com.huike.clues.domain.dto.TbCourseDTO;
import com.huike.clues.service.TbCourseService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.page.TableDataInfo;
import com.huike.web.controller.annotation.MyLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程管理Controller
 *
 * @date 2023-04-01
 */
@RestController
@RequestMapping("/clues/course")
@Api(tags = "课程管理")
public class TbCourseController extends BaseController {

  @Autowired
  private TbCourseService courseService;

  @MyLog
  @ApiOperation("分页查询课程")
  @GetMapping("/list")
  public TableDataInfo list(TbCourseDTO courseDTO) {
    startPage();
    List<TbCourse> list = courseService.listPage(courseDTO);
    return getDataTable(list);
  }

  @ApiOperation("新增课程")
  @PostMapping
  public AjaxResult save(@RequestBody TbCourseDTO courseDTO) {
    return courseService.saveCourse(courseDTO) ? success() : error();
  }
}
