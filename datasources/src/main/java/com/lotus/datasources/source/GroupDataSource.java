package com.lotus.datasources.source;

import com.lotus.datasources.strategy.DynamicDataSourceStrategy;
import lombok.Data;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组数据源
 *
 * @author
 */
@Data
public class GroupDataSource {

    private String groupName;

    private DynamicDataSourceStrategy dynamicDataSourceStrategy;

    private Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public GroupDataSource(String groupName, DynamicDataSourceStrategy dynamicDataSourceStrategy) {
        this.groupName = groupName;
        this.dynamicDataSourceStrategy = dynamicDataSourceStrategy;
    }

    /**
     * add a new datasource to this group
     *
     * @param ds         the name of the datasource
     * @param dataSource datasource
     */
    public DataSource addDatasource(String ds, DataSource dataSource) {
        return dataSourceMap.put(ds, dataSource);
    }

    /**
     * @param ds the name of the datasource
     */
    public DataSource removeDatasource(String ds) {
        return dataSourceMap.remove(ds);
    }

    public String determineDsKey() {
        return dynamicDataSourceStrategy.determineKey(new ArrayList<>(dataSourceMap.keySet()));
    }

    public DataSource determineDataSource() {
        return dataSourceMap.get(determineDsKey());
    }

    public int size() {
        return dataSourceMap.size();
    }
}