package org.kamil.librarymanager.repository;
import org.kamil.librarymanager.config.DatabaseConfig;
import org.kamil.librarymanager.model.Book;
import org.kamil.librarymanager.model.BookStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookRepositoryImpl implements BookRepository {

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, c.name as category_name FROM books b " +
                "LEFT JOIN categories c ON b.category_id = c.id";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all books: " + e.getMessage());
        }
        return books;
    }

    @Override
    public void save(Book book) {
        String sql = "INSERT INTO books (title, author, status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getStatus() != null ? book.getStatus().name() : BookStatus.AVAILABLE.name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving book: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }

    @Override
    public Book findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBook(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding book by ID: " + e.getMessage());
        }
        return null;
    }
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setStatus(BookStatus.valueOf(rs.getString("status")));
        book.setCategoryName(rs.getString("category_name"));
        return book;
    }

    public void rentBook(Long bookId, Long userId) {
        String insertRental = "INSERT INTO rentals (book_id, user_id, rental_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        String updateBook = "UPDATE books SET status = 'RENTED' WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement ps1 = conn.prepareStatement(insertRental);
                 PreparedStatement ps2 = conn.prepareStatement(updateBook)) {

                ps1.setLong(1, bookId);
                ps1.setLong(2, userId);
                ps1.executeUpdate();

                ps2.setLong(1, bookId);
                ps2.executeUpdate();

                conn.commit();
                System.out.println("Book rented successfully.");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Rental Error: " + e.getMessage());
        }
    }

}
