package com.lotus.datasources.creator;

import com.lotus.datasources.properties.DataSourceProperties;
import com.lotus.datasources.properties.HikariCpConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Hikari数据源创建器
 *
 * @author
 * @since 2020/1/21
 */
public class HikariDataSourceCreator extends AbstractDataSourceCreator implements DataSourceCreator, InitializingBean {

    private static final ConfigMergeCreator<HikariCpConfig, HikariConfig> MERGE_CREATOR = new ConfigMergeCreator<>("HikariCp", HikariCpConfig.class, HikariConfig.class);
    private static Method configCopyMethod = null;

    static {
        fetchMethod();
    }

    private HikariCpConfig gConfig;

    /**
     * to support springboot 1.5 and 2.x
     * HikariConfig 2.x use 'copyState' to copy config
     * HikariConfig 3.x use 'copyStateTo' to copy config
     */
    @SuppressWarnings("JavaReflectionMemberAccess")
    private static void fetchMethod() {
        try {
            configCopyMethod = HikariConfig.class.getMethod("copyState", HikariConfig.class);
            return;
        } catch (NoSuchMethodException ignored) {
        }

        try {
            configCopyMethod = HikariConfig.class.getMethod("copyStateTo", HikariConfig.class);
            return;
        } catch (NoSuchMethodException ignored) {
        }
        throw new RuntimeException("HikariConfig does not has 'copyState' or 'copyStateTo' method!");
    }

    @Override
    public DataSource doCreateDataSource(DataSourceProperties dataSourceProperties) {
        HikariConfig config = MERGE_CREATOR.create(gConfig, dataSourceProperties.getHikari());
        config.setUsername(dataSourceProperties.getUsername());
        config.setPassword(dataSourceProperties.getPassword());
        config.setJdbcUrl(dataSourceProperties.getUrl());
        config.setPoolName(dataSourceProperties.getPoolName());
        String driverClassName = dataSourceProperties.getDriverClassName();
        if (StringUtils.isNotEmpty(driverClassName)) {
            config.setDriverClassName(driverClassName);
        }
        if (Boolean.FALSE.equals(dataSourceProperties.getLazy())) {
            return new HikariDataSource(config);
        }
        config.validate();
        HikariDataSource dataSource = new HikariDataSource();
        try {
            configCopyMethod.invoke(config, dataSource);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("HikariConfig failed to copy to HikariDataSource", e);
        }
        return dataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        gConfig = properties.getHikari();
    }
}
