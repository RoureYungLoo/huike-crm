package com.huike.clues.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.TbCourse;
import com.huike.clues.domain.dto.TbCourseDTO;
import com.huike.clues.service.TbCourseService;
import com.huike.clues.mapper.TbCourseMapper;
import com.huike.common.constant.Constants;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author 93238
 * @description 针对表【tb_course(课程管理)】的数据库操作Service实现
 * @createDate 2023-10-12 06:35:46
 */
@Service
public class TbCourseServiceImpl extends ServiceImpl<TbCourseMapper, TbCourse>
    implements TbCourseService {

  /**
   * 分页查询课程
   *
   * @param courseDTO
   * @return
   */
  @Override
  public List<TbCourse> listPage(TbCourseDTO courseDTO) {

    Map<String, Object> params = courseDTO.getParams();
    String beginCreateTime = (String) params.get("beginCreateTime");
    String endCreateTime = (String) params.get("endCreateTime");
    String applicablePerson = courseDTO.getApplicablePerson();
    String code = courseDTO.getCode();
    String subject = courseDTO.getSubject();
    String name = courseDTO.getName();

    LambdaQueryWrapper<TbCourse> wrapper = Wrappers.lambdaQuery();
    wrapper
        .eq(StringUtils.isNotEmpty(code), TbCourse::getCode, code)
        .like(StringUtils.isNotEmpty(name), TbCourse::getName, name)
        .eq(StringUtils.isNotEmpty(subject), TbCourse::getSubject, subject)
        .eq(StringUtils.isNotEmpty(applicablePerson), TbCourse::getApplicablePerson, applicablePerson)
        .eq(TbCourse::getIsDelete, Constants.NOT_DELETED)
        .between(StringUtils.isNotEmpty(beginCreateTime) && StringUtils.isNotEmpty(endCreateTime),
            TbCourse::getCreateTime, beginCreateTime, endCreateTime);
    List<TbCourse> list = this.list(wrapper);
    return list;
  }

  /**
   * 新增课程
   *
   * @param courseDTO
   * @return
   */
  @Override
  public boolean saveCourse(TbCourseDTO courseDTO) {
    TbCourse course = BeanUtil.toBean(courseDTO, TbCourse.class);
    course.setCreateTime(DateUtils.getNowDate());
    course.setIsDelete(Constants.NOT_DELETED);
    course.setCode(RandomUtil.randomString(8).toLowerCase(Locale.CHINESE));
    return this.save(course);
  }
}




