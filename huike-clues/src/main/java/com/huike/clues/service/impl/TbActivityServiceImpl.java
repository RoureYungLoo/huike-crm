package com.huike.clues.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.huike.clues.domain.TbActivity;
import com.huike.clues.domain.dto.TbActivityDTO;
import com.huike.clues.service.TbActivityService;
import com.huike.clues.mapper.TbActivityMapper;
import com.huike.common.constant.Constants;
import com.huike.common.constant.HttpStatus;
import com.huike.common.core.page.TableDataInfo;
import com.huike.common.exception.BaseException;
import com.huike.common.utils.DateUtils;
import com.huike.common.utils.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

/**
 * @author 93238
 * @description 针对表【tb_activity(活动管理)】的数据库操作Service实现
 * @createDate 2023-10-12 06:35:46
 */
@Service
public class TbActivityServiceImpl extends ServiceImpl<TbActivityMapper, TbActivity>
    implements TbActivityService {

  @Autowired
  private TbActivityMapper tbActivityMapper;

  /**
   * 分页查询活动管理列表
   *
   * @param tbActivityDTO
   * @return
   */
  @Override
  public TableDataInfo<List<TbActivity>> listPage(TbActivityDTO tbActivityDTO) {
    String code = tbActivityDTO.getCode();
    String channel = tbActivityDTO.getChannel();
    Map<String, Object> params = tbActivityDTO.getParams();
    String beginCreateTime = (String) params.get("beginCreateTime");
    String endCreateTime = (String) params.get("endCreateTime");
    String beginTime = (String) params.get("beginTime");
    String endTime = (String) params.get("endTime");

    LambdaQueryWrapper<TbActivity> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(StringUtils.isNotEmpty(code), TbActivity::getCode, code)
        .eq(StringUtils.isNotEmpty(channel), TbActivity::getChannel, channel)
        .between(StringUtils.isNotEmpty(beginCreateTime) && StringUtils.isNotEmpty(endCreateTime),
            TbActivity::getCreateTime, beginCreateTime, endCreateTime)
        .ge(StringUtils.isNotEmpty(beginTime), TbActivity::getBeginTime, beginTime)
        .le(StringUtils.isNotEmpty(endTime), TbActivity::getEndTime, endTime);

    List<TbActivity> list = this.list(wrapper);


    TableDataInfo<List<TbActivity>> rspData = new TableDataInfo<>();
    rspData.setCode(HttpStatus.SUCCESS);
    rspData.setMsg("查询成功");
    rspData.setRows(list);
    rspData.setTotal(new PageInfo(list).getTotal());

    // 活动状态 Map
    Map<String, String> statusMap = Map.of(
        Constants.ACTIVITY_TO_AUDIT, "toAudit",
        Constants.ACTIVITY_PASS, "pass",
        Constants.ACTIVITY_REJECT, "reject",
        Constants.ACTIVITY_FINISH, "finish"
    );

    Map<String, Object> p = new HashMap<>();
    list.forEach(act -> {
      String status = act.getStatus();
      String resKey = statusMap.get(status);
      p.computeIfAbsent(resKey, k -> 0);
      p.put(resKey, Integer.parseInt(p.get(resKey).toString()) + 1);
    });
    rspData.setParams(p);
    return rspData;
  }

  /**
   * 新增活动
   *
   * @param tbActivityDTO
   * @return
   */
  @Override
  public boolean saveAct(TbActivityDTO tbActivityDTO) {
    TbActivity bean = BeanUtil.toBean(tbActivityDTO, TbActivity.class);
    bean.setCreateTime(DateUtils.getNowDate());
    bean.setStatus(Constants.ACTIVITY_PASS);
    String code = RandomUtil.randomString(8).toLowerCase(Locale.CHINA);
    int count = this.count(Wrappers.<TbActivity>lambdaQuery().eq(TbActivity::getCode, code));
    if (count != 0) {
      throw new BaseException("系统异常,请重试");
    }
    bean.setCode(code);
    return this.save(bean);
  }

  /**
   * 获取活动管理详细信息
   *
   * @param id
   * @return
   */
  @Override
  public TbActivity selectById(Long id) {
    return this.getById(id);
  }

  /**
   * 修改活动管理
   *
   * @param activityDTO
   * @return
   */
  @Override
  public boolean updateActById(TbActivityDTO activityDTO) {
    Long id = activityDTO.getId();
    TbActivity activityDb = this.getById(id);
    if (ObjectUtil.isNull(activityDb)) {
      throw new BaseException("活动不存在");
    }
    TbActivity bean = BeanUtil.toBean(activityDTO, TbActivity.class);
    return this.updateById(bean);
  }

  /**
   * 删除活动
   *
   * @param id
   * @return
   */
  @Override
  public boolean deleteActById(Long id) {
    TbActivity activityDb = this.getById(id);
    if (ObjectUtil.isNull(activityDb)) {
      throw new BaseException("活动不存在");
    }

    return this.removeById(id);
  }
}




