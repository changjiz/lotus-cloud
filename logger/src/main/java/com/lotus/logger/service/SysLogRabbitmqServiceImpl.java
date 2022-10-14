package com.lotus.logger.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lotus.common.utils.Constants;
import com.lotus.common.utils.HttpContextUtils;
import com.lotus.common.utils.IPUtils;
import com.lotus.common.utils.SpringContextUtils;
import com.lotus.framework.base.BaseUtils;
import com.lotus.logger.annotation.SysLog;
import com.lotus.logger.entity.SysLogDO;
import com.lotus.logger.utils.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @program: lotus-cloud
 * @description: rabbitMQ消息保存日志
 * @author: changjiz
 * @create: 2022-09-23 14:12
 **/
@Slf4j
public class SysLogRabbitmqServiceImpl implements SysLogService {

    public final static String LOGGER_SEND_QUEUE = "logger.send.queue";
    private AmqpTemplate rabbitTemplate = SpringContextUtils.getBean(AmqpTemplate.class);

    @Override
    public boolean saveLog(ProceedingJoinPoint joinPoint, Long userId, Long tenantId, long beginTime, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = method.getAnnotation(SysLog.class);
        // 异步处理  -  防止消息发送等各因素耗时
        if (sysLog.async()) {
            ThreadPoolUtils.execute(() -> {
                saveLogToMQ(joinPoint, method, sysLog, userId, tenantId, beginTime, time);
            });
        } else {
            saveLogToMQ(joinPoint, method, sysLog, userId, tenantId, beginTime, time);
        }

        return false;
    }

    private void saveLogToMQ(ProceedingJoinPoint joinPoint, Method method, SysLog sysLog, Long userId, Long tenantId, long beginTime, long time) {
        SysLogDO sysLogDO = new SysLogDO();
        if (StringUtils.isNotEmpty(sysLog.value())) {
            //注解上的描述
            sysLogDO.setOperation(sysLog.value());
        }

        //请求的方法名
        sysLogDO.setMethod(joinPoint.getTarget().getClass().getName() + "." + method.getName() + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            JSONArray jsonArray = new JSONArray(Arrays.asList(args));
            sysLogDO.setParams(jsonArray.toString());
        } catch (Exception e) {
            log.info(" JSON parse error , original args[] field -> " + e.getMessage());
        }
        //设置IP地址
        sysLogDO.setIp(IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));
        sysLogDO.setApply(Constants.APPLY);
        sysLogDO.setUserId(userId);
        sysLogDO.setTenantId(tenantId);
        sysLogDO.setTime(time);
        sysLogDO.setRequestTime(beginTime);
        sysLogDO.setDeviceType(BaseUtils.getDeviceType());

        String message = JSONObject.toJSONString(sysLogDO);
        //保存系统日志   MQ send
        rabbitTemplate.convertAndSend(LOGGER_SEND_QUEUE, message);
        log.info("send logger  -> " + message);
    }
}
