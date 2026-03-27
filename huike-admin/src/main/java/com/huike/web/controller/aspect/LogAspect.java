package com.huike.web.controller.aspect;

import cn.hutool.json.JSONUtil;
import com.huike.clues.domain.dto.SysOperLogDTO;
import com.huike.common.core.domain.entity.SysUserDTO;
import com.huike.common.core.text.Convert;
import com.huike.common.enums.BusinessStatus;
import com.huike.common.utils.*;
import com.huike.common.utils.ip.AddressUtils;
import com.huike.common.utils.ip.IpUtils;
import com.huike.framework.manager.AsyncManager;
import com.huike.framework.manager.factory.AsyncFactory;
import com.huike.web.controller.annotation.MyLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author luruoyang
 */

@Aspect
@Component
@Slf4j
public class LogAspect {

  @Around(value = "@annotation(myLog)", argNames = "pjp,myLog")
  public Object around(ProceedingJoinPoint pjp, MyLog myLog) {
    Object retValue = null;
    SysOperLogDTO entry = new SysOperLogDTO();
    try {
      retValue = pjp.proceed();
      handleLog(entry, pjp, myLog, retValue, null);
    } catch (Throwable e) {
      handleLog(entry, pjp, myLog, retValue, e);
      // throw new RuntimeException(e);
    }
    return retValue;
  }

  private void handleLog(SysOperLogDTO entry, ProceedingJoinPoint pjp, MyLog myLog, Object retValue, Throwable e) {
    SysUserDTO sysUserDTO = SecurityUtils.getLoginUser().getUser();
    Object[] args = pjp.getArgs();
    String className = pjp.getTarget().getClass().getName();
    String methodName = pjp.getSignature().getName();
    HttpServletRequest request = ServletUtils.getRequest();
    String hostIp = IpUtils.getHostIp();
    entry.setTitle(myLog.title());
    entry.setBusinessType(myLog.businessType().ordinal());
    entry.setMethod(className + "#" + methodName);
    entry.setRequestMethod(ServletUtils.getRequest().getMethod());
    entry.setOperatorType(myLog.operatorType().ordinal());
    entry.setOperName(sysUserDTO.getNickName());
    entry.setDeptName(sysUserDTO.getDept().getDeptName());
    entry.setOperUrl(StringUtils.substring(request.getRequestURI(), 0, 255));
    entry.setOperIp(hostIp);
    entry.setOperLocation(AddressUtils.getRealAddressByIP(hostIp));
    entry.setOperParam(JSONUtil.toJsonStr(args));
    entry.setJsonResult(JSONUtil.toJsonStr(retValue));
    entry.setStatus(e == null ? BusinessStatus.SUCCESS.ordinal() : BusinessStatus.FAIL.ordinal());
    entry.setErrorMsg(e != null ? StringUtils.substring(Convert.toStr(e.getMessage(), ExceptionUtil.getExceptionMessage(e)), 0, 2000) : "");
    entry.setOperTime(DateUtils.getNowDate());
    AsyncManager.me().execute(AsyncFactory.recordOper(entry));
  }


}
