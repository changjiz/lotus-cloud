package com.lotus.security.exception;


import com.lotus.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author Mark changji_z@163.com
 */
@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(InnerAuthException.class)
    public R handleRRException(InnerAuthException e) {
        log.error(e.getMessage(), e);
        return R.error(403, e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public R handlerNoFoundException(NotLoginException e) {
        log.error(e.getMessage(), e);
        return R.error(401, e.getMessage());
    }

    @ExceptionHandler(NotRoleException.class)
    public R handleException(NotRoleException e) {
        log.error(e.getMessage(), e);
        return R.error(412, "无权限访问.");
    }

    @ExceptionHandler(NotPermissionException.class)
    public R handleException(NotPermissionException e) {
        log.error(e.getMessage(), e);
        return R.error(412, "无权限访问.");
    }
}
