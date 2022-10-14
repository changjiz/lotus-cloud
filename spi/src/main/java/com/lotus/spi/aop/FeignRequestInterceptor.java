package com.lotus.spi.aop;

import com.lotus.common.utils.IPUtils;
import com.lotus.common.utils.ServletUtils;
import com.lotus.common.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * feign 请求拦截器
 *
 * @author changji
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (StringUtils.isNotNull(httpServletRequest)) {
            Map<String, String> headers = ServletUtils.getHeaders(httpServletRequest);
            // 传递用户信息请求头，防止丢失
            String sessionId = headers.get("session_id");
            if (StringUtils.isNotEmpty(sessionId)) {
                requestTemplate.header("session_id", sessionId);
            }
            String userId = headers.get("user_id");
            if (StringUtils.isNotEmpty(userId)) {
                requestTemplate.header("user_id", userId);
            }
            String companyId = headers.get("tenant_id");
            if (StringUtils.isNotEmpty(companyId)) {
                requestTemplate.header("tenant_id", companyId);
            }
            String roleId = headers.get("role_id");
            if (StringUtils.isNotEmpty(roleId)) {
                requestTemplate.header("role_id");
            }
            String deviceType = headers.get("device_type");
            if (StringUtils.isNotEmpty(deviceType)) {
                requestTemplate.header("device_type", deviceType);
            }

            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IPUtils.getIpAddr(ServletUtils.getRequest()));
        }
    }
}