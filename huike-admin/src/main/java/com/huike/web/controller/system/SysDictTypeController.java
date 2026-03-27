package com.huike.web.controller.system;

import com.huike.clues.domain.SysDictType;
import com.huike.clues.service.ISysDictTypeService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysDictTypeDTO;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author luruoyang
 */
@Api(tags = "字典类型")
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {

  @Autowired
  private ISysDictTypeService sysDictTypeService;

  @GetMapping("/list")
  @ApiOperation("分页条件查询")
  public TableDataInfo<List<SysDictType>> list(SysDictTypeDTO sysDictTypeDTO) {
    startPage();
    List<SysDictType> list = sysDictTypeService.listSysDictType(sysDictTypeDTO);
    return getDataTable(list);
  }

  @GetMapping("/{dictId:\\d+}")
  @ApiOperation("查询字典类型详细信息")
  public AjaxResult selectById(@PathVariable Long dictId) {
    startPage();
    SysDictType sysDictType = sysDictTypeService.selectById(dictId);
    return AjaxResult.success(sysDictType);
  }

  @PostMapping
  @ApiOperation("新增字典类型")
  public AjaxResult saveDictType(@RequestBody SysDictTypeDTO sysDictTypeDTO) {
    return sysDictTypeService.saveDictType(sysDictTypeDTO) ? success() : error();
  }


}
