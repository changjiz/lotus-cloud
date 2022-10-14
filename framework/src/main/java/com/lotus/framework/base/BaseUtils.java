package com.lotus.framework.base;

import cn.hutool.core.convert.Convert;
import com.lotus.framework.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: framework
 * @description
 * @author: changjiz
 * @create: 2021-10-26 10:21
 **/
@Slf4j
public class BaseUtils {

    public static String SESSION_ID = "session_id";
    public static String USER_ID = "user_id";
    public static String TENANT_ID = "tenant_id";
    public static String ROLE_ID = "role_id";
    public static String DEVICE_TYPE = "device_type";

    public static String getSessionId() {
        return Convert.toStr(SecurityContextHolder.get(SESSION_ID), null);
    }

    public static void setSessionId(String sessionId) {
        SecurityContextHolder.set(SESSION_ID, sessionId);
    }

    public static Long getUserId() {
        return Convert.toLong(SecurityContextHolder.get(USER_ID), 0L);
    }

    public static void setUserId(Long userId) {
        SecurityContextHolder.set(USER_ID, userId);
    }

    public static Long getTenantId() {
        return Convert.toLong(SecurityContextHolder.get(TENANT_ID), 0L);
    }

    public static void setTenantId(Long tenantId) {
        SecurityContextHolder.set(TENANT_ID, tenantId);
    }

    public static Long getRoleId() {
        return Convert.toLong(SecurityContextHolder.get(ROLE_ID), 0L);
    }

    public static void setRoleId(Long roleId) {
        SecurityContextHolder.set(ROLE_ID, roleId);
    }

    public static int getDeviceType() {
        return Convert.toInt(SecurityContextHolder.get(DEVICE_TYPE), 0);
    }

    public static void setDeviceType(int deviceType) {
        SecurityContextHolder.set(DEVICE_TYPE, deviceType);
    }

}
