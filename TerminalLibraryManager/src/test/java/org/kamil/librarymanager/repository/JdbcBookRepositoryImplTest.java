package org.kamil.librarymanager.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kamil.librarymanager.model.Book;
import org.kamil.librarymanager.model.BookStatus;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class JdbcBookRepositoryImplTest {
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = new JdbcBookRepositoryImpl();
    }

    @Test
    void shouldAchieveFullBranchCoverage() {
        // 1. BRANCH: Save ternary (status != null)
        Book b1 = new Book();
        b1.setTitle("Title 1"); b1.setAuthor("Author 1");
        b1.setStatus(BookStatus.RENTED);
        bookRepository.save(b1);

        // 2. BRANCH: Save ternary (status == null -> "AVAILABLE")
        Book b2 = new Book();
        b2.setTitle("Title 2"); b2.setAuthor("Author 2");
        b2.setStatus(null);
        bookRepository.save(b2);

        // 3. BRANCH: findAll while(rs.next()) is TRUE
        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotEmpty();
        Long validId = books.get(0).getId();

        // 4. BRANCH: findById if(rs.next()) is TRUE
        assertThat(bookRepository.findById(validId)).isNotNull();

        // 5. BRANCH: findById if(rs.next()) is FALSE
        assertThat(bookRepository.findById(-1L)).isNull();

        // 6. BRANCH: Statistics if(rs.next()) is TRUE
        bookRepository.printStatistics();
        bookRepository.printFullStatistics();

        // 7. BRANCH: deleteById (Executes the delete logic)
        bookRepository.deleteById(validId);

        try (java.sql.Connection conn = org.kamil.librarymanager.config.DatabaseConfig.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");
            stmt.execute("TRUNCATE TABLE books");
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Book> emptyBooks = bookRepository.findAll();
        assertThat(emptyBooks).isEmpty();
    }
}