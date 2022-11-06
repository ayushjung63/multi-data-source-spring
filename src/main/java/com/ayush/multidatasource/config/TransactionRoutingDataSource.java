package com.ayush.multidatasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.*;

public class TransactionRoutingDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<DataSourceType> currentDataSource = new ThreadLocal<>();

    public TransactionRoutingDataSource(DataSource readDB, DataSource writeDB) {
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(DataSourceType.READ_WRITE, writeDB);
        dataSources.put(DataSourceType.READ_ONLY, readDB);

        super.setTargetDataSources(dataSources);
        super.setDefaultTargetDataSource(writeDB);
    }

    static void setReadonlyDataSource(boolean isReadonly) {
        currentDataSource.set(isReadonly ? DataSourceType.READ_ONLY : DataSourceType.READ_WRITE);
    }

    public static void unload() {
        currentDataSource.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return currentDataSource.get();
    }

    private enum DataSourceType {
        READ_ONLY,
        READ_WRITE;
    }
}