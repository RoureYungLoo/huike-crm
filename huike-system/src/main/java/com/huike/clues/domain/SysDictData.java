package com.huike.clues.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huike.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author luruoyang
 */
@Data
@ApiModel
public class SysDictData implements Serializable {
  private static final long serialVersionUID = 1L;

  @TableId("dict_code")
  @ApiModelProperty("")
  private Long dictCode;

  @ApiModelProperty("")

  private Integer dictSort;
  @ApiModelProperty("")

  private String dictLabel;
  @ApiModelProperty("")

  private String dictValue;
  @ApiModelProperty("")

  private String dictType;
  @ApiModelProperty("")

  private String cssClass;
  @ApiModelProperty("")

  private String listClass;
  @ApiModelProperty("")

  private char isDefault;
  @ApiModelProperty("")
  private char status;

  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  @ApiModelProperty("创建者")
  private String createBy;

  @ApiModelProperty("更新者")
  private String updateBy;

  @ApiModelProperty("更新时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;

  private String remark;
}
