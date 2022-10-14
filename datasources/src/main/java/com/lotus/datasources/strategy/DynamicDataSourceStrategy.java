package com.lotus.datasources.strategy;

import java.util.List;


public interface DynamicDataSourceStrategy {

    /**
     * determine a database from the given dataSources
     *
     * @param dsNames given dataSources
     * @return final dataSource
     */
    String determineKey(List<String> dsNames);
}
