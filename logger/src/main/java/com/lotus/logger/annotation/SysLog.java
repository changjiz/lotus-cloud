package com.lotus.logger.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author Mark changji_z@163.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";

    boolean async() default false;

}
