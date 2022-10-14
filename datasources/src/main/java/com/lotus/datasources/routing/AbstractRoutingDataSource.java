package com.lotus.datasources.routing;

import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 抽象动态获取数据源
 *
 * @author
 * @since 1.0.1
 */
public abstract class AbstractRoutingDataSource extends AbstractDataSource {

    /**
     * 抽象获取连接池
     *
     * @return 连接池
     */
    protected abstract DataSource determineDataSource();

    /**
     * 获取默认数据源名称
     *
     * @return 名称
     */
    protected abstract String getPrimary();

    @Override
    public Connection getConnection() throws SQLException {
        return determineDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {

        return determineDataSource().getConnection(username, password);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineDataSource().isWrapperFor(iface));
    }
}
