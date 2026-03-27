package com.huike.web.controller.annotation;

import com.huike.common.enums.BusinessType;
import com.huike.common.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luruoyang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface MyLog {

  /**
   * 模块
   */
  public String title() default "";

  /**
   * 功能
   */
  public BusinessType businessType() default BusinessType.OTHER;

  /**
   * 操作人类别
   */
  public OperatorType operatorType() default OperatorType.MANAGE;

  /**
   * 是否保存请求的参数
   */
  public boolean isSaveRequestData() default true;
}
