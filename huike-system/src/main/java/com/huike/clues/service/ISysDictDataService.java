package com.huike.clues.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.SysDictData;
import com.huike.common.core.domain.entity.SysDictDataDTO;

import java.util.List;

/**
 * @author luruoyang
 */
public interface ISysDictDataService extends IService<SysDictData> {
  List<SysDictData> getSysDictDataListByType(String dictType);

  List<SysDictData> getDataListByType(SysDictDataDTO sysDictDataDTO);

  boolean saveDictData(SysDictDataDTO sysDictDataDTO);

  boolean updateByDictCode(SysDictDataDTO sysDictDataDTO);

  boolean deleteByDictCodes(List<Long> dictCode);
}
