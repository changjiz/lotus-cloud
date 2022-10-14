package com.lotus.framework.configure;

import com.lotus.framework.aop.HeaderInterceptor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * 拦截器配置
 *
 * @author changjiz
 */
@Configuration
public class BaseWebMvcConfiguration implements WebMvcConfigurer {
    /**
     * 不需要拦截地址
     */
    @Value("auth.transfer.ignores")
    private List<String> ignores;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        if (CollectionUtils.isEmpty(ignores)) {
            ignores = Arrays.asList("/login", "/logout");
        }

        // 自定义请求头拦截器
        registry.addInterceptor(new HeaderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(ignores)
                .order(-10);
    }
}
