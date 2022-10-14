package com.lotus.datasources.aop;

import com.lotus.datasources.context.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedElement;

/**
 * @program: datasources
 * @description:
 * @author: changjiz
 * @create: 2021-09-28 17:29
 **/
@Aspect
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataSourceAnnotationAspect {


    @Pointcut("@annotation(com.lotus.datasources.annotation.DataSource)")
    public void annotationPointCut() {
    }

    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String datasourceKey = this.findDataSourceAttribute(signature.getMethod());
        log.info(" annotationPointCut around-------> {}  key:  {} ", signature.getMethod().getName(), datasourceKey);
        DynamicDataSourceContextHolder.push(datasourceKey);
        try {
            return point.proceed();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    private String findDataSourceAttribute(AnnotatedElement ae) {
        AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ae, com.lotus.datasources.annotation.DataSource.class);
        if (attributes != null) {
            return attributes.getString("value");
        }
        return null;
    }
}
