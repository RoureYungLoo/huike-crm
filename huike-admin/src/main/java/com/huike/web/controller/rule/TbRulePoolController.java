package com.huike.web.controller.rule;

import com.huike.clues.domain.TbRulePool;
import com.huike.clues.domain.dto.TbRulePoolDTO;
import com.huike.clues.service.TbRulePoolService;
import com.huike.common.core.controller.BaseController;
import com.huike.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 线索或商机池规则相关接口
 * @Date 2023-10-10
 */
@RestController
@RequestMapping("/rule/pool")
@Api(tags = "规则池模块")
public class TbRulePoolController extends BaseController {

  @Autowired
  private TbRulePoolService rulePoolService;

  @GetMapping("/{type}")
  @ApiOperation("获取规则基础信息")
  public AjaxResult findRulePoolByType(@PathVariable String type) {
    TbRulePool rulePool = rulePoolService.findRulePoolByType(type);
    return rulePool != null ? AjaxResult.success(rulePool) : AjaxResult.error();
  }

  @PutMapping
  @ApiOperation("修改线索或商机池规则")
  public AjaxResult updateRulePoolById(@RequestBody TbRulePoolDTO rulePoolDTO) {
    return rulePoolService.updateRulePoolById(rulePoolDTO) ? success() : error();
  }
}
