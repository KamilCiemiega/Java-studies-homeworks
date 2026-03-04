package org.kamil.librarymanager.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/library_db?allowMultiQueries=true";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASS = "rootpassword";

    private static final String H2_URL = "jdbc:h2:~/library_db;AUTO_SERVER=TRUE";
    private static final String H2_USER = "sa";
    private static final String H2_PASS = "";

    public static Connection getConnection() throws SQLException {
        String dbType = System.getenv("DB_TYPE");

        if (dbType == null || dbType.equalsIgnoreCase("H2")) {
            return DriverManager.getConnection(H2_URL, H2_USER, H2_PASS);
        }

        return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASS);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            // Requirement: Use schema.sql to create tables
            InputStream is = DatabaseConfig.class.getClassLoader().getResourceAsStream("schema.sql");
            if (is == null) throw new RuntimeException("schema.sql not found!");

            String schema = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(schema);
            }
        } catch (Exception e) {
            System.err.println("DB Init Warning: " + e.getMessage());
        }
    }
}

