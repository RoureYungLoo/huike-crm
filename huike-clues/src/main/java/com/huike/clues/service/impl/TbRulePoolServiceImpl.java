package com.huike.clues.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbRulePool;
import com.huike.clues.domain.dto.TbRulePoolDTO;
import com.huike.clues.service.TbRulePoolService;
import com.huike.clues.mapper.TbRulePoolMapper;
import com.huike.common.exception.BaseException;
import com.huike.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 93238
 * @description 针对表【tb_rule_pool(线索池规则)】的数据库操作Service实现
 * @createDate 2023-10-12 06:35:46
 */
@Service
public class TbRulePoolServiceImpl extends ServiceImpl<TbRulePoolMapper, TbRulePool>
    implements TbRulePoolService {

  @Override
  public TbRulePool findRulePoolByType(String type) {
    return this.getOne(Wrappers.<TbRulePool>lambdaQuery()
        .eq(StringUtils.isNotEmpty(type), TbRulePool::getType, type)
    );
  }

  @Override
  public boolean updateRulePoolById(TbRulePoolDTO rulePoolDTO) {
    TbRulePool rulePoolDb = this.getById(rulePoolDTO.getId());
    if (Objects.isNull(rulePoolDb)) {
      throw new BaseException("规则不存在");
    }
    TbRulePool entity = BeanUtil.toBean(rulePoolDTO, TbRulePool.class);
    return this.updateById(entity);
  }
}




