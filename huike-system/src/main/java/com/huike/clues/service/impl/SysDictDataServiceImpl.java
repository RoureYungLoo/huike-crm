package com.huike.clues.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.SysDictData;
import com.huike.clues.mapper.SysDictDataMapper;
import com.huike.clues.service.ISysDictDataService;
import com.huike.common.core.domain.entity.SysDictDataDTO;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luruoyang
 */
@Service
@Log4j2
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {
  @Autowired
  private SysDictDataMapper sysDictDataMapper;

  @Override
  public List<SysDictData> getSysDictDataListByType(String dictType) {
    List<SysDictData> list = this.list(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictType, dictType));
    log.info("---> {}", list);
    return list;
  }

  @Override
  public List<SysDictData> getDataListByType(SysDictDataDTO sysDictDataDTO) {
    String dictLabel = sysDictDataDTO.getDictLabel();
    String dictType = sysDictDataDTO.getDictType();
    String status = sysDictDataDTO.getStatus();

    LambdaQueryWrapper<SysDictData> wrapper = Wrappers.lambdaQuery();
    wrapper.eq(StringUtils.isNotEmpty(dictLabel), SysDictData::getDictLabel, dictLabel)
        .eq(StringUtils.isNotEmpty(dictType), SysDictData::getDictType, dictType)
        .eq(StringUtils.isNotEmpty(status), SysDictData::getStatus, status);
    List<SysDictData> list = this.list(wrapper);

    return list;
  }

  @Override
  public boolean saveDictData(SysDictDataDTO sysDictDataDTO) {
    SysDictData sysDictData = BeanUtil.toBean(sysDictDataDTO, SysDictData.class);
    sysDictData.setCreateBy(SecurityUtils.getUserId().toString());
    sysDictData.setCreateTime(DateUtils.getNowDate());
    return this.save(sysDictData);
  }

  @Override
  public boolean updateByDictCode(SysDictDataDTO sysDictDataDTO) {
    SysDictData sysDictData = BeanUtil.toBean(sysDictDataDTO, SysDictData.class);
    sysDictData.setUpdateBy(SecurityUtils.getUsername());
    sysDictData.setUpdateTime(DateUtils.getNowDate());
    return this.updateById(sysDictData);
  }

  @Override
  public boolean deleteByDictCodes(List<Long> dictCodes) {
    return this.removeByIds(dictCodes);
  }
}
