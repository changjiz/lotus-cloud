package com.lotus.framework.aop;

import com.lotus.common.utils.ServletUtils;
import com.lotus.common.utils.StringUtils;
import com.lotus.framework.base.BaseUtils;
import com.lotus.framework.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 *
 * @author changjiz
 */
public class HeaderInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        BaseUtils.setSessionId(ServletUtils.getHeader(request, BaseUtils.SESSION_ID));

        String userId = ServletUtils.getHeader(request, BaseUtils.USER_ID);
        if (StringUtils.isNotEmpty(userId)) {
            BaseUtils.setUserId(Long.valueOf(userId));
        }
        String tenantId = ServletUtils.getHeader(request, BaseUtils.TENANT_ID);
        if (StringUtils.isNotEmpty(tenantId)) {
            BaseUtils.setTenantId(Long.valueOf(tenantId));
        }
        String roleId = ServletUtils.getHeader(request, BaseUtils.ROLE_ID);
        if (StringUtils.isNotEmpty(roleId)) {
            BaseUtils.setRoleId(Long.valueOf(roleId));
        }
        String deviceType = ServletUtils.getHeader(request, BaseUtils.DEVICE_TYPE);
        if (StringUtils.isNotEmpty(deviceType)) {
            BaseUtils.setDeviceType(Integer.valueOf(deviceType));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SecurityContextHolder.remove();
    }
}
