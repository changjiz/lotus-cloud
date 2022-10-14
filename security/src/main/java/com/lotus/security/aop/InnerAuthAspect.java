package com.lotus.security.aop;

import com.lotus.auth.utils.AuthConstants;
import com.lotus.common.utils.ServletUtils;
import com.lotus.common.utils.StringUtils;
import com.lotus.security.annotation.InnerAuth;
import com.lotus.security.exception.InnerAuthException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 内部服务调用验证处理
 *
 * @author changjiz
 */
@Aspect
@Component
public class InnerAuthAspect implements Ordered {
    @Around("@annotation(innerAuth)")
    public Object innerAround(ProceedingJoinPoint point, InnerAuth innerAuth) throws Throwable {
        String source = ServletUtils.getRequest().getHeader(AuthConstants.FROM_SOURCE);
        // 内部请求验证
        if (!StringUtils.equals(AuthConstants.INNER, source)) {
            throw new InnerAuthException("没有内部访问权限，不允许访问");
        }

        String userId = ServletUtils.getRequest().getHeader(AuthConstants.USER_ID);
        // 用户信息验证
        if (innerAuth.isUser() && StringUtils.isEmpty(userId)) {
            throw new InnerAuthException("没有设置用户信息，不允许访问 ");
        }
        return point.proceed();
    }

    /**
     * 确保在权限认证aop执行前执行
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
