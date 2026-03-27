package com.huike.clues.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author luruoyang
 */
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class SysDictType implements Serializable {
  private static final long serialVersionUID = 1L;
  @ApiModelProperty("")
  @TableId("dict_id")
  private Long dictId;

  @ApiModelProperty("")
  private String dictName;

  @ApiModelProperty("")
  private String dictType;

  @ApiModelProperty("")
  private String status;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  /**
   * 创建者
   */
  @ApiModelProperty("创建者")
  private String createBy;

  /**
   * 更新者
   */
  @ApiModelProperty("更新者")
  private String updateBy;

  /**
   * 更新时间
   */
  @ApiModelProperty("更新时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;

  /**
   * 备注
   */
  @JsonIgnore
  private String remark;

}
