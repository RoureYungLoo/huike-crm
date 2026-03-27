package com.huike.web.controller.system;

import com.huike.clues.domain.SysDictData;
import com.huike.clues.domain.SysDictType;
import com.huike.clues.service.ISysDictDataService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import com.huike.common.core.domain.entity.SysDictDataDTO;
import com.huike.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author luruoyang
 */
@Api
@RestController
@RequestMapping("/system/dict/data")

public class SysDictDataController extends BaseController {

  @Autowired
  private ISysDictDataService sysDictDataService;

  @GetMapping("/type/{dictType}")
  public AjaxResult getSysDictDataListByType(@PathVariable String dictType) {
    List<SysDictData> sysDictDataList = sysDictDataService.getSysDictDataListByType(dictType);
    return AjaxResult.success(sysDictDataList);
  }

  @GetMapping("/list")
  @ApiOperation("根据字典类型分页查询字典数据列表")
  public TableDataInfo getDataListByType(SysDictDataDTO sysDictDataDTO) {
    startPage();
    List<SysDictData> list = sysDictDataService.getDataListByType(sysDictDataDTO);
    return getDataTable(list);
  }

  @PostMapping
  @ApiOperation("新增字典数据")
  public AjaxResult saveDictData(@RequestBody SysDictDataDTO sysDictDataDTO) {
    return sysDictDataService.saveDictData(sysDictDataDTO) ? success() : error();
  }

  @GetMapping("/{dictCode:\\d+}")
  @ApiOperation("查询字典数据详细")
  public AjaxResult findById(@PathVariable Long dictCode) {
    SysDictData sysDictData = sysDictDataService.getById(dictCode);
    return sysDictData != null ? AjaxResult.success(sysDictData) : error();
  }

  @PutMapping
  @ApiOperation("修改字典数据")
  public AjaxResult updateById(@RequestBody SysDictDataDTO sysDictDataDTO) {
    return sysDictDataService.updateByDictCode(sysDictDataDTO) ? success() : error();
  }

  @DeleteMapping("/{dictCodes}")
  @ApiOperation("删除字典数据")
  public AjaxResult deleteByDictCodes(@PathVariable("dictCodes") List<Long> dictCodes) {
    return sysDictDataService.deleteByDictCodes(dictCodes) ? success() : error();
  }
}
