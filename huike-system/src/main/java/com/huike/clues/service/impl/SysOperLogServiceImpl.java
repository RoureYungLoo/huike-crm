package com.huike.clues.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.SysOperLog;
import com.huike.clues.mapper.SysOperLogMapper;
import com.huike.clues.service.ISysOperLogService;
import org.springframework.stereotype.Service;

/**
 * @author luruoyang
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {
  @Override
  public boolean saveOperLogEntry(SysOperLog entry) {
    return this.save(entry);
  }
}
