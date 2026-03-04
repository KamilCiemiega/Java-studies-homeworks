package org.kamil.librarymanager.repository;
import org.kamil.librarymanager.config.DatabaseConfig;
import org.kamil.librarymanager.model.Book;
import org.kamil.librarymanager.model.BookStatus;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
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
        String sql = "INSERT INTO books (title, author, year_published, isbn, status, category_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setObject(3, book.getYearPublished());
            stmt.setString(4, book.getIsbn());
            stmt.setString(5, book.getStatus() != null ? book.getStatus().name() : "AVAILABLE");
            stmt.setObject(6, book.getCategoryId());

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
        String sql = "SELECT b.*, c.name as category_name FROM books b " +
                "LEFT JOIN categories c ON b.category_id = c.id WHERE b.id = ?";
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

    @Override
    public void printStatistics() {
        String sql = "SELECT (SELECT COUNT(*) FROM books) as total_books, " +
                "(SELECT COUNT(*) FROM books WHERE status = 'RENTED') as rented_books, " +
                "(SELECT COUNT(*) FROM users) as total_users";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                System.out.println("\n=== LIBRARY STATISTICS ===");
                System.out.println("Total Books: " + rs.getInt("total_books"));
                System.out.println("Books Rented: " + rs.getInt("rented_books"));
                System.out.println("===========================");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printFullStatistics() {
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

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setYearPublished(rs.getInt("year_published")); // Check if this exists in Book.java
        book.setIsbn(rs.getString("isbn"));               // Check if this exists in Book.java
        book.setStatus(BookStatus.valueOf(rs.getString("status")));
        book.setCategoryName(rs.getString("category_name"));
        return book;
    }
}
