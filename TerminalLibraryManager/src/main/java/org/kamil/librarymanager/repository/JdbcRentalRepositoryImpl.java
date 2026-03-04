package org.kamil.librarymanager.repository;

import org.kamil.librarymanager.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcRentalRepositoryImpl implements RentalRepository {

    @Override
    public void rentBook(Long userId, Long bookId) {
        String insertRental = "INSERT INTO rentals (book_id, user_id) VALUES (?, ?)";
        String updateBook = "UPDATE books SET status = 'RENTED' WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(insertRental);
                 PreparedStatement ps2 = conn.prepareStatement(updateBook)) {

                ps1.setLong(1, bookId);
                ps1.setLong(2, userId);
                ps1.executeUpdate();

                ps2.setLong(1, bookId);
                ps2.executeUpdate();

                conn.commit();
                System.out.println("Book rented successfully!");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Rental Error: " + e.getMessage());
        }
    }

    @Override
    public void returnBook(Long bookId) {
        String updateRentalSql = "UPDATE rentals SET return_date = CURRENT_TIMESTAMP " +
                "WHERE book_id = ? AND return_date IS NULL";
        String updateBookSql = "UPDATE books SET status = 'AVAILABLE' WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psRental = conn.prepareStatement(updateRentalSql);
                 PreparedStatement psBook = conn.prepareStatement(updateBookSql)) {

                psRental.setLong(1, bookId);
                psRental.executeUpdate();

                psBook.setLong(1, bookId);
                psBook.executeUpdate();

                conn.commit();
                System.out.println("Success: Book ID " + bookId + " has been returned.");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error returning book: " + e.getMessage());
        }
    }
}
