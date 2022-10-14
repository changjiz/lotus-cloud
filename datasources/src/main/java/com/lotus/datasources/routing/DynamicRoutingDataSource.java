package com.lotus.datasources.routing;

import com.lotus.common.exception.SysException;
import com.lotus.datasources.context.DynamicDataSourceContextHolder;
import com.lotus.datasources.creator.AbstractDataSourceCreator;
import com.lotus.datasources.properties.DataSourceProperties;
import com.lotus.datasources.source.GroupDataSource;
import com.lotus.datasources.source.ItemDataSource;
import com.lotus.datasources.strategy.DynamicDataSourceStrategy;
import com.lotus.datasources.strategy.LoadBalanceDynamicDataSourceStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 核心动态数据源组件
 *
 * @author
 * @since 1.0.1
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean, DisposableBean {

    private static final String UNDERLINE = "_";
    /**
     * 所有数据库
     */
    @Getter
    private final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();
    /**
     * 分组数据库
     */
    @Getter
    private final Map<String, GroupDataSource> groupDataSources = new ConcurrentHashMap<>();

    @Setter
    private AbstractDataSourceCreator dataSourceCreator;
    @Setter
    private Map<String, DataSourceProperties> dataSourcePropertiess;
    @Setter
    private Class<? extends DynamicDataSourceStrategy> strategy = LoadBalanceDynamicDataSourceStrategy.class;
    @Setter
    private String primary = "master";

    @Override
    protected String getPrimary() {
        return primary;
    }

    @Override
    public DataSource determineDataSource() {
        String dsKey = DynamicDataSourceContextHolder.peek();
        return getDataSource(dsKey);
    }


    private DataSource determinePrimaryDataSource() {
        log.debug("dynamic-datasource switch to the primary datasource");
        DataSource dataSource = dataSourceMap.get(primary);
        if (dataSource != null) {
            return dataSource;
        }
        GroupDataSource groupDataSource = groupDataSources.get(primary);
        if (groupDataSource != null) {
            return groupDataSource.determineDataSource();
        }
        throw new SysException("dynamic-datasource can not find primary datasource");
    }

    /**
     * 获取数据源
     *
     * @param ds 数据源名称
     * @return 数据源
     */
    public DataSource getDataSource(String ds) {
        if (StringUtils.isEmpty(ds)) {
            return determinePrimaryDataSource();
        } else if (!groupDataSources.isEmpty() && groupDataSources.containsKey(ds)) {
            log.debug("dynamic-datasource switch to the datasource named [{}]", ds);
            return groupDataSources.get(ds).determineDataSource();
        } else if (dataSourceMap.containsKey(ds)) {
            log.debug("dynamic-datasource switch to the datasource named [{}]", ds);
            return dataSourceMap.get(ds);
        }
        return determinePrimaryDataSource();
    }

    /**
     * 添加数据源
     *
     * @param ds         数据源名称
     * @param dataSource 数据源
     */
    public synchronized void addDataSource(String ds, DataSource dataSource) {
        DataSource oldDataSource = dataSourceMap.put(ds, dataSource);
        // 新数据源添加到分组
        this.addGroupDataSource(ds, dataSource);
        // 关闭老的数据源
        if (oldDataSource != null) {
            closeDataSource(ds, oldDataSource);
        }
        log.info("dynamic-datasource - add a datasource named [{}] success", ds);
    }

    /**
     * 新数据源添加到分组
     *
     * @param ds         新数据源的名字
     * @param dataSource 新数据源
     */
    private void addGroupDataSource(String ds, DataSource dataSource) {
        if (ds.contains(UNDERLINE)) {
            String group = ds.split(UNDERLINE)[0];
            GroupDataSource groupDataSource = groupDataSources.get(group);
            if (groupDataSource == null) {
                try {
                    groupDataSource = new GroupDataSource(group, strategy.getDeclaredConstructor().newInstance());
                    groupDataSources.put(group, groupDataSource);
                } catch (Exception e) {
                    throw new RuntimeException("dynamic-datasource - add the datasource named " + ds + " error", e);
                }
            }
            groupDataSource.addDatasource(ds, dataSource);
        }
    }

    /**
     * 删除数据源
     *
     * @param ds 数据源名称
     */
    public synchronized void removeDataSource(String ds) {
        if (!StringUtils.hasText(ds)) {
            throw new RuntimeException("remove parameter could not be empty");
        }
        if (primary.equals(ds)) {
            throw new RuntimeException("could not remove primary datasource");
        }
        if (dataSourceMap.containsKey(ds)) {
            DataSource dataSource = dataSourceMap.remove(ds);
            closeDataSource(ds, dataSource);
            if (ds.contains(UNDERLINE)) {
                String group = ds.split(UNDERLINE)[0];
                if (groupDataSources.containsKey(group)) {
                    DataSource oldDataSource = groupDataSources.get(group).removeDatasource(ds);
                    if (oldDataSource == null) {
                        log.warn("fail for remove datasource from group. dataSource: {} ,group: {}", ds, group);
                    }
                }
            }
            log.info("dynamic-datasource - remove the database named [{}] success", ds);
        } else {
            log.warn("dynamic-datasource - could not find a database named [{}]", ds);
        }
    }

    @Override
    public void destroy() throws Exception {
        log.info("dynamic-datasource start closing ....");
        for (Map.Entry<String, DataSource> item : dataSourceMap.entrySet()) {
            closeDataSource(item.getKey(), item.getValue());
        }
        log.info("dynamic-datasource all closed success,bye");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        DataSourceProperties tempProperties;
        // 添加并分组数据源
        for (Map.Entry<String, DataSourceProperties> dsItem : dataSourcePropertiess.entrySet()) {

            // 临时设置连接池名称
            tempProperties = dsItem.getValue();
            if (StringUtils.isEmpty(tempProperties.getPoolName())) {
                tempProperties.setPoolName(dsItem.getKey() + "-pool");
            }
            addDataSource(dsItem.getKey(), dataSourceCreator.createDataSource(tempProperties));
        }
        // 检测默认数据源是否设置
        if (groupDataSources.containsKey(primary)) {
            log.info("dynamic-datasource initial loaded [{}] datasource,primary group datasource named [{}]", dataSourcePropertiess.size(), primary);
        } else if (dataSourceMap.containsKey(primary)) {
            log.info("dynamic-datasource initial loaded [{}] datasource,primary datasource named [{}]", dataSourcePropertiess.size(), primary);
        } else {
            log.warn("dynamic-datasource initial loaded [{}] datasource,Please add your primary datasource or check your configuration", dataSourcePropertiess.size());
        }
    }

    /**
     * close db
     *
     * @param ds         dsName
     * @param dataSource db
     */
    private void closeDataSource(String ds, DataSource dataSource) {
        try {
            if (dataSource instanceof ItemDataSource) {
                ((ItemDataSource) dataSource).close();
            } else {
                Method closeMethod = ReflectionUtils.findMethod(dataSource.getClass(), "close");
                if (closeMethod != null) {
                    closeMethod.invoke(dataSource);
                }
            }
        } catch (Exception e) {
            log.warn("dynamic-datasource closed datasource named [{}] failed", ds, e);
        }
    }

}