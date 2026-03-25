package com.university.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_DB_NAME = "university_portal";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private static final String HOST = env("DB_HOST", DEFAULT_HOST);
    private static final String PORT = env("DB_PORT", DEFAULT_PORT);
    private static final String DB_NAME = env("DB_NAME", DEFAULT_DB_NAME);
    private static final String USER = env("DB_USER", DEFAULT_USER);
    private static final String PASSWORD = envAllowEmpty("DB_PASSWORD", DEFAULT_PASSWORD);

    private static final String JDBC_PARAMS = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String URL = env("DB_URL", "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + JDBC_PARAMS);
    private static final String SERVER_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + JDBC_PARAMS;

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Connection getServerConnection() throws SQLException {
        return DriverManager.getConnection(SERVER_URL, USER, PASSWORD);
    }

    public static String getDbName() {
        return DB_NAME;
    }

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return (value == null || value.trim().isEmpty()) ? fallback : value.trim();
    }

    private static String envAllowEmpty(String key, String fallback) {
        String value = System.getenv(key);
        return value == null ? fallback : value;
    }
}
