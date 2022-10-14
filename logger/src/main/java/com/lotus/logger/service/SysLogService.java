package com.lotus.logger.service;

import org.aspectj.lang.ProceedingJoinPoint;

public interface SysLogService {
    boolean saveLog(ProceedingJoinPoint joinPoint, Long userId, Long tenantId, long beginTime, long time);
}
