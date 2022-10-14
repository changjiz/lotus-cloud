package com.lotus.datasources.creator;

import com.lotus.datasources.properties.DataSourceProperties;
import com.lotus.datasources.properties.DynamicDataSourceProperties;
import com.lotus.datasources.source.ItemDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * 抽象连接池创建器
 * <p>
 * 这里主要处理一些公共逻辑，如脚本和事件等
 *
 * @author
 */
@Slf4j
public abstract class AbstractDataSourceCreator implements DataSourceCreator {

    @Autowired
    protected DynamicDataSourceProperties properties;


    /**
     * 子类去实际创建连接池
     *
     * @param dataSourceProperties 数据源信息
     * @return 实际连接池
     */
    public abstract DataSource doCreateDataSource(DataSourceProperties dataSourceProperties);

    @Override
    public DataSource createDataSource(DataSourceProperties dataSourceProperties) {
        Boolean lazy = dataSourceProperties.getLazy();
        if (lazy == null) {
            lazy = properties.getLazy();
            dataSourceProperties.setLazy(lazy);
        }
        DataSource dataSource = doCreateDataSource(dataSourceProperties);
        return wrapDataSource(dataSource, dataSourceProperties);
    }

    private DataSource wrapDataSource(DataSource dataSource, DataSourceProperties dataSourceProperties) {
        String name = dataSourceProperties.getPoolName();
        return new ItemDataSource(name, dataSource);
    }
}
