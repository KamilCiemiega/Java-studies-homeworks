package org.kamil.librarymanager.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASS = "rootpassword";

    private static final String H2_URL = "jdbc:h2:~/library_db;AUTO_SERVER=TRUE";
    private static final String H2_USER = "sa";
    private static final String H2_PASS = "";

    public static Connection getConnection() throws SQLException {
        String dbType = System.getenv("DB_TYPE");

        if (dbType == null || dbType.equalsIgnoreCase("H2")) {
            System.out.println("DEBUG: Connecting to H2 In-Memory Database...");
            return DriverManager.getConnection(H2_URL, H2_USER, H2_PASS);
        }

        System.out.println("Using Docker MySQL database...");
        return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASS);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            // Only run this if we are using H2
            if (conn.getMetaData().getDatabaseProductName().equals("H2")) {
                InputStream is = DatabaseConfig.class.getClassLoader().getResourceAsStream("schema.sql");
                if (is == null) throw new RuntimeException("schema.sql not found!");

                String schema = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(schema);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

