package com.huike.clues.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 线索
 * @TableName tb_clue
 */
@TableName(value ="tb_clue")
@Data
public class TbClue implements Serializable {
    /**
     * 线索id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 1 男 0 女
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 微信
     */
    private String weixin;

    /**
     * qq
     */
    private String qq;

    /**
     * 意向等级
     */
    private String level;

    /**
     * 意向学科
     */
    private String subject;

    /**
     * 状态(已分配1  进行中2  回收3  伪线索4)
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 伪线索失败次数(最大数3次)
     */
    private Integer falseCount;

    /**
     * 
     */
    private Date nextTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 是否转派
     */
    private String transfer;

    /**
     * 线索失效时间
     */
    private Date endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}