package com.lotus.datasources.properties;

import com.lotus.datasources.strategy.DynamicDataSourceStrategy;
import com.lotus.datasources.strategy.LoadBalanceDynamicDataSourceStrategy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DynamicDataSourceProperties
 *
 * @author
 * @see org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
 * @since 1.0.0
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX)
public class DynamicDataSourceProperties {

    public static final String PREFIX = "spring.datasource.dynamic";

    /**
     * 必须设置默认的库,默认master
     */
    private String primary = "master";
    /**
     * 是否懒加载数据源
     */
    private Boolean lazy = false;

    /**
     * 每一个数据源
     */
    private Map<String, DataSourceProperties> datasource = new LinkedHashMap<>();
    /**
     * 多数据源选择算法clazz，默认负载均衡算法
     */
    private Class<? extends DynamicDataSourceStrategy> strategy = LoadBalanceDynamicDataSourceStrategy.class;
    /**
     * HikariCp全局参数配置
     */
    @NestedConfigurationProperty
    private HikariCpConfig hikari = new HikariCpConfig();
}
