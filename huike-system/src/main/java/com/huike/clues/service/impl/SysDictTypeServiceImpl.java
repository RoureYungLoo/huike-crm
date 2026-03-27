package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.SysDictType;
import com.huike.clues.mapper.SysDictTypeMapper;
import com.huike.clues.service.ISysDictTypeService;
import com.huike.common.core.domain.entity.SysDictTypeDTO;
import com.huike.common.exception.BaseException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.CharSequenceUtils;
import org.apache.commons.lang3.CharUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author luruoyang
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {
  @Autowired
  private ISysDictTypeService sysDictTypeService;

  @Override
  public List<SysDictType> listSysDictType(SysDictTypeDTO sysDictTypeDTO) {
    String dictName = sysDictTypeDTO.getDictName();
    String dictType = sysDictTypeDTO.getDictType();
    String status = sysDictTypeDTO.getStatus();
    Map<String, Object> params = sysDictTypeDTO.getParams();
    Object beginTime = params.get("beginTime");
    Object endTime = params.get("endTime");

    LambdaQueryWrapper<SysDictType> wrapper = Wrappers.lambdaQuery();
    wrapper.like(StringUtils.isNotEmpty(dictName), SysDictType::getDictName, dictName)
        .eq(StringUtils.isNotEmpty(dictType), SysDictType::getDictType, dictType)
        .eq(StringUtils.isNotEmpty(status), SysDictType::getStatus, status)
        .between(Objects.nonNull(beginTime) && Objects.nonNull(endTime), SysDictType::getCreateTime, beginTime, endTime);

    List<SysDictType> list = this.list(wrapper);
    return list;
  }

  @Override
  public SysDictType selectById(Long dictId) {
    SysDictType sysDictType = this.getById(dictId);
    return sysDictType;
  }

  @Override
  public boolean saveDictType(SysDictTypeDTO sysDictTypeDTO) {
    String dictType = sysDictTypeDTO.getDictType();
    List<SysDictType> list = sysDictTypeService.list(Wrappers.<SysDictType>lambdaQuery()
        .eq(StringUtils.isNotEmpty(dictType), SysDictType::getDictType, dictType));

    if (CollectionUtils.isNotEmpty(list)) {
      throw new BaseException("字典类型已经存在");
    }
    SysDictType sysDictType = new SysDictType();
    BeanUtils.copyProperties(sysDictTypeDTO, sysDictType);
    sysDictType.setCreateBy(SecurityUtils.getUserId().toString());
    sysDictType.setCreateTime(DateUtils.getNowDate());
    return this.save(sysDictType);
  }
}
