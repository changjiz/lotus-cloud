package com.lotus.datasources.configure;

import com.lotus.datasources.aop.DataSourceAnnotationAspect;
import com.lotus.datasources.creator.AbstractDataSourceCreator;
import com.lotus.datasources.creator.HikariDataSourceCreator;
import com.lotus.datasources.properties.DynamicDataSourceProperties;
import com.lotus.datasources.routing.DynamicRoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 动态数据源核心自动配置类
 *
 * @author changjz
 * @since 1.0.1
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(value = DataSourceAutoConfiguration.class, name = "com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure")
@ConditionalOnProperty(prefix = DynamicDataSourceProperties.PREFIX, name = "enabled", havingValue = "true")
public class DynamicDataSourceAutoConfiguration implements InitializingBean {

    private final DynamicDataSourceProperties properties;


    public DynamicDataSourceAutoConfiguration(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }

    @Configuration
    static class HikariDataSourceCreatorConfiguration {
        @Bean
        public HikariDataSourceCreator hikariDataSourceCreator() {
            return new HikariDataSourceCreator();
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(AbstractDataSourceCreator dataSourceCreator) {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setDataSourcePropertiess(properties.getDatasource());
        dataSource.setPrimary(properties.getPrimary());
        dataSource.setStrategy(properties.getStrategy());
        dataSource.setDataSourceCreator(dataSourceCreator);
        return dataSource;
    }

    @Bean
    public DataSourceAnnotationAspect dataSourceAnnotationAspect() {
        return new DataSourceAnnotationAspect();
    }

    @Override
    public void afterPropertiesSet() {

    }

}
