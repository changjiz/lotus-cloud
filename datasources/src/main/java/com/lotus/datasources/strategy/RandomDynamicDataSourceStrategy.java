package com.lotus.datasources.strategy;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDynamicDataSourceStrategy implements DynamicDataSourceStrategy {

    @Override
    public String determineKey(List<String> dsNames) {
        return dsNames.get(ThreadLocalRandom.current().nextInt(dsNames.size()));
    }
}
