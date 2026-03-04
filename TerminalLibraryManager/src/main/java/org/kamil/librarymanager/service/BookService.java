package org.kamil.librarymanager.service;

import org.kamil.librarymanager.config.DatabaseConfig;
import org.kamil.librarymanager.model.Book;
import org.kamil.librarymanager.model.BookStatus;
import org.kamil.librarymanager.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public void addBook(String title, String author, int year, String isbn, Long categoryId) {
        Book book = Book.builder()
                .title(title)
                .author(author)
                .yearPublished(year)
                .isbn(isbn)
                .status(BookStatus.AVAILABLE)
                .categoryId(categoryId)
                .build();

        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBooksByCategory(String categoryName) {
        return bookRepository.findAll().stream()
                .filter(b -> b.getCategoryName() != null && b.getCategoryName().equalsIgnoreCase(categoryName))
                .collect(Collectors.toList());
    }

    public void printAdminStats() {
        String sql = "SELECT (SELECT COUNT(*) FROM books) as total, " +
                "(SELECT COUNT(*) FROM books WHERE status = 'RENTED') as rented";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                System.out.println("--- STATISTICS ---");
                System.out.println("Total books: " + rs.getInt("total"));
                System.out.println("Loaned: " + rs.getInt("rented"));
            }
        } catch (SQLException e) {
            System.err.println("Statistics error: " + e.getMessage());
        }
    }

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
}
