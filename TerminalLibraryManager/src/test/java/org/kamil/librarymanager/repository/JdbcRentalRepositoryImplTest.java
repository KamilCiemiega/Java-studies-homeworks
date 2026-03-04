package org.kamil.librarymanager.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kamil.librarymanager.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.Statement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class JdbcRentalRepositoryImplTest {
    private RentalRepository rentalRepository;
    private final Long TEST_USER_ID = 1L;
    private final Long TEST_BOOK_ID = 1L;

    @BeforeEach
    void setUp() {
        rentalRepository = new JdbcRentalRepositoryImpl();

        // Prepare H2 database state
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {

            // Clear tables to avoid Unique Constraint or FK issues
            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");
            stmt.execute("TRUNCATE TABLE rentals");
            stmt.execute("TRUNCATE TABLE books");
            stmt.execute("TRUNCATE TABLE users");
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");

            // Insert data needed for the Foreign Keys
            stmt.execute("INSERT INTO users (id, username, password_hash, role) " +
                    "VALUES (1, 'tester', 'hash', 'USER')");
            stmt.execute("INSERT INTO books (id, title, author, status) " +
                    "VALUES (1, 'Test Book', 'Test Author', 'AVAILABLE')");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldCompleteFullRentalAndReturnCycle() {
        // 1. Test rentBook
        // This hits: setAutoCommit(false), ps1 exec, ps2 exec, and conn.commit()
        assertThatCode(() -> {
            rentalRepository.rentBook(TEST_USER_ID, TEST_BOOK_ID);
        }).doesNotThrowAnyException();

        // 2. Test returnBook
        // This hits: psRental exec, psBook exec, and conn.commit()
        assertThatCode(() -> {
            rentalRepository.returnBook(TEST_BOOK_ID);
        }).doesNotThrowAnyException();
    }
}