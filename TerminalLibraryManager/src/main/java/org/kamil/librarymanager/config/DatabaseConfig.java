package org.kamil.librarymanager.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            System.out.println("Using local H2 database...");
            return DriverManager.getConnection(H2_URL, H2_USER, H2_PASS);
        }

        System.out.println("Using Docker MySQL database...");
        return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASS);
    }
}

