package com.huike.clues.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.SysOperLog;

/**
 * @author luruoyang
 */
public interface ISysOperLogService extends IService<SysOperLog> {
  boolean saveOperLogEntry(SysOperLog entry);
}
