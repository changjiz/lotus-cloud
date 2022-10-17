package com.lotus.logger.aop;

import com.lotus.framework.base.BaseUtils;
import com.lotus.logger.service.SysLogRabbitmqServiceImpl;
import com.lotus.logger.service.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;


/**
 * 系统日志，切面处理类
 *
 * @author Mark changji_z@163.COM
 */
@Aspect
@Component
@DependsOn({"springContextUtils"})
public class SysLogAspect {

    private SysLogService sysLogService;

    public SysLogAspect() {
        this.sysLogService = new SysLogRabbitmqServiceImpl();
    }

    @Autowired(required = false)
    public SysLogAspect(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Pointcut("@annotation(com.lotus.logger.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        sysLogService.saveLog(point, BaseUtils.getUserId(), BaseUtils.getTenantId(), beginTime, time);

        return result;
    }


}
