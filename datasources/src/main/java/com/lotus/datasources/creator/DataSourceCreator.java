package com.lotus.datasources.creator;

import com.lotus.datasources.properties.DataSourceProperties;

import javax.sql.DataSource;

/**
 *
 *
 * @author
 */
public interface DataSourceCreator {

    /**
     * 通过属性创建数据源
     *
     * @param dataSourceProperties 数据源属性
     * @return 被创建的数据源
     */
    DataSource createDataSource(DataSourceProperties dataSourceProperties);

}
