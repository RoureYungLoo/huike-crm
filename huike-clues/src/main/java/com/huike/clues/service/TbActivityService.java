package com.huike.clues.service;

import com.huike.clues.domain.TbActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.dto.TbActivityDTO;
import com.huike.common.core.page.TableDataInfo;

import java.util.List;

/**
 * @author 93238
 * @description 针对表【tb_activity(活动管理)】的数据库操作Service
 * @createDate 2023-10-12 06:35:46
 */
public interface TbActivityService extends IService<TbActivity> {

  /**
   * 分页查询活动管理列表
   *
   * @param tbActivityDTO
   * @return
   */
  TableDataInfo<List<TbActivity>> listPage(TbActivityDTO tbActivityDTO);

  /**
   * 新增活动
   *
   * @param tbActivityDTO
   * @return
   */
  boolean saveAct(TbActivityDTO tbActivityDTO);

  /**
   * 获取活动管理详细信息
   *
   * @param id
   * @return
   */
  TbActivity selectById(Long id);

  /**
   * 修改活动管理
   *
   * @param activityDTO
   * @return
   */
  boolean updateActById(TbActivityDTO activityDTO);

  /**
   * 删除活动
   *
   * @param id
   * @return
   */
  boolean deleteActById(Long id);
}
