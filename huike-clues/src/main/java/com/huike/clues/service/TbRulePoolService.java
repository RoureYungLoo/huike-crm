package com.huike.clues.service;

import com.huike.clues.domain.TbRulePool;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.dto.TbRulePoolDTO;

/**
 * @author 93238
 * @description 针对表【tb_rule_pool(线索池规则)】的数据库操作Service
 * @createDate 2023-10-12 06:35:46
 */
public interface TbRulePoolService extends IService<TbRulePool> {

  /**
   * 获取规则基础信息
   *
   * @param type
   * @return
   */
  TbRulePool findRulePoolByType(String type);

  /**
   * 修改线索或商机池规则
   *
   * @param rulePoolDTO
   * @return
   */
  boolean updateRulePoolById(TbRulePoolDTO rulePoolDTO);

}
