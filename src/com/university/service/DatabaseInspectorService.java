package com.university.service;

import com.university.db.DBConnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseInspectorService {
    public String getDatabaseProofReport() throws SQLException {
        StringBuilder sb = new StringBuilder();

        try (Connection connection = DBConnection.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            sb.append("Database Product: ").append(metaData.getDatabaseProductName()).append(System.lineSeparator());
            sb.append("Database Version: ").append(metaData.getDatabaseProductVersion()).append(System.lineSeparator());
            sb.append("Connected URL: ").append(metaData.getURL()).append(System.lineSeparator());
            sb.append("Current Schema: ").append(connection.getCatalog()).append(System.lineSeparator());
            sb.append(System.lineSeparator()).append("Tables and Row Counts").append(System.lineSeparator());
            sb.append("---------------------").append(System.lineSeparator());

            String tableSql = """
                    SELECT table_name
                    FROM information_schema.tables
                    WHERE table_schema = DATABASE()
                    ORDER BY table_name
                    """;

            try (PreparedStatement tableStatement = connection.prepareStatement(tableSql);
                 ResultSet tables = tableStatement.executeQuery()) {
                boolean found = false;
                while (tables.next()) {
                    found = true;
                    String tableName = tables.getString("table_name");
                    long count = getRowCount(connection, tableName);
                    sb.append(tableName).append(" -> ").append(count).append(" rows").append(System.lineSeparator());
                }
                if (!found) {
                    sb.append("No tables found in current schema.").append(System.lineSeparator());
                }
            }
        }

        return sb.toString();
    }

    public String getTableStructureReport() throws SQLException {
        StringBuilder sb = new StringBuilder();

        try (Connection connection = DBConnection.getConnection()) {
            String sql = """
                    SELECT table_name, column_name, data_type, is_nullable, column_key
                    FROM information_schema.columns
                    WHERE table_schema = DATABASE()
                    ORDER BY table_name, ordinal_position
                    """;

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {
                Map<String, StringBuilder> byTable = new LinkedHashMap<>();

                while (rs.next()) {
                    String table = rs.getString("table_name");
                    String column = rs.getString("column_name");
                    String type = rs.getString("data_type");
                    String nullable = rs.getString("is_nullable");
                    String key = rs.getString("column_key");

                    StringBuilder tableBuilder = byTable.computeIfAbsent(table, t -> new StringBuilder());
                    tableBuilder.append(" - ")
                            .append(column)
                            .append(" : ")
                            .append(type)
                            .append(" | nullable=")
                            .append(nullable)
                            .append(" | key=")
                            .append(key)
                            .append(System.lineSeparator());
                }

                if (byTable.isEmpty()) {
                    return "No table structures found.";
                }

                for (Map.Entry<String, StringBuilder> entry : byTable.entrySet()) {
                    sb.append(entry.getKey()).append(System.lineSeparator());
                    sb.append(entry.getValue());
                    sb.append(System.lineSeparator());
                }
            }
        }

        return sb.toString();
    }

    private long getRowCount(Connection connection, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM `" + tableName + "`";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("total");
            }
            return 0;
        }
    }
}
