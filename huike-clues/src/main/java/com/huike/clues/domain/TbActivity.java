package com.huike.clues.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动管理
 *
 * @TableName tb_activity
 */
@TableName(value = "tb_activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbActivity implements Serializable {
  /**
   * id
   */
  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 活动名称
   */
  private String name;

  /**
   * 渠道来源
   */
  private String channel;

  /**
   * 活动简介
   */
  private String info;

  /**
   * 活动类型
   */
  private String type;

  /**
   * 折扣
   */
  private Double discount;

  /**
   * 课程代金券
   */
  private Integer vouchers;

  /**
   * 状态
   * 1 提交状态
   * 2 审核通过
   * 3 拒绝（审核驳回，失败状态）
   * 4 已完成（审核通过并过期的数据）
   */
  private String status;

  /**
   * 开始时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date beginTime;

  /**
   * 结束时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date endTime;

  /**
   *
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  /**
   *
   */
  private String code;

  @TableField(exist = false)
  private static final long serialVersionUID = 1L;
}