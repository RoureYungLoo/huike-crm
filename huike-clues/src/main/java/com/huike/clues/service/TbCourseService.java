package com.huike.clues.service;

import com.huike.clues.domain.TbCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.dto.TbCourseDTO;

import java.util.List;

/**
 * @author 93238
 * @description 针对表【tb_course(课程管理)】的数据库操作Service
 * @createDate 2023-10-12 06:35:46
 */
public interface TbCourseService extends IService<TbCourse> {

  /**
   * 分页查询课程
   *
   * @param courseDTO
   * @return
   */
  List<TbCourse> listPage(TbCourseDTO courseDTO);

  /**
   * 新增课程
   *
   * @param courseDTO
   * @return
   */
  boolean saveCourse(TbCourseDTO courseDTO);
}
