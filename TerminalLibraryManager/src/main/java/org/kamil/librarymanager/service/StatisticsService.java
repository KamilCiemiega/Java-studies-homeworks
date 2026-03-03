package org.kamil.librarymanager.service;

import org.kamil.librarymanager.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsService {
    public void showAdminStats() {
        String sql = "SELECT " +
                "(SELECT COUNT(*) FROM books) as total_books, " +
                "(SELECT COUNT(*) FROM books WHERE status = 'RENTED') as rented_books, " +
                "(SELECT COUNT(*) FROM users) as total_users";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                System.out.println("\n=== LIBRARY STATISTICS ===");
                System.out.println("Total Books:      " + rs.getInt("total_books"));
                System.out.println("Books Rented:     " + rs.getInt("rented_books"));
                System.out.println("Total Registered: " + rs.getInt("total_users"));
                System.out.println("===========================");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching stats: " + e.getMessage());
        }
    }

    public void displayLibraryStatistics() {
        String sql = "SELECT " +
                "(SELECT COUNT(*) FROM books) as total_books, " +
                "(SELECT COUNT(*) FROM books WHERE status = 'RENTED') as rented_count, " +
                "(SELECT COUNT(*) FROM users) as total_users";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                System.out.println("\n===============================");
                System.out.println("   LIBRARY STATISTICS (ADMIN)  ");
                System.out.println("===============================");
                System.out.println("Total Books:      " + rs.getInt("total_books"));
                System.out.println("Currently Rented: " + rs.getInt("rented_count"));
                System.out.println("Total Users:      " + rs.getInt("total_users"));
                System.out.println("===============================");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching statistics: " + e.getMessage());
        }
    }
}
