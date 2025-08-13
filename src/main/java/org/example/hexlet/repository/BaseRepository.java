package org.example.hexlet.repository;

import com.zaxxer.hikari.HikariDataSource;

public class BaseRepository {
    protected static HikariDataSource dataSource;

    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    public static void setDataSource(HikariDataSource installedDataSource) {
        dataSource = installedDataSource;
    }
}
