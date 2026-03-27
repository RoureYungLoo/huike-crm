package com.huike.clues.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.SysDictType;
import com.huike.common.core.domain.entity.SysDictTypeDTO;

import java.util.List;

/**
 * @author luruoyang
 */
public interface ISysDictTypeService extends IService<SysDictType> {
  List<SysDictType> listSysDictType(SysDictTypeDTO sysDictTypeDTO);

  SysDictType selectById(Long dictId);

  boolean saveDictType(SysDictTypeDTO sysDictTypeDTO);
}
