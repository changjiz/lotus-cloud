package com.lotus.logger.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 系统日志
 *
 * @author Mark changji_z@163.com
 */
@Data
public class SysLogDO implements Serializable {

    private static final long serialVersionUID = -8777616762394878033L;

    private long userId;
    //租户id
    private long tenantId;
    //应用标识
    private String apply;
    //用户操作
    private String operation;
    //请求方法
    private String method;
    //请求参数
    private String params;
    //执行时长(毫秒)
    private long time;
    //IP地址
    private String ip;
    //请求时间
    private long requestTime;
    // 设备类型
    private int deviceType;

}
