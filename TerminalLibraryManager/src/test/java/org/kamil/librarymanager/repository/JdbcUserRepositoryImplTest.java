package org.kamil.librarymanager.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kamil.librarymanager.model.Role;
import org.kamil.librarymanager.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcUserRepositoryImplTest {
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {

        userRepository = new JdbcUserRepositoryImpl();
        org.kamil.librarymanager.config.DatabaseConfig.initializeDatabase();
    }

    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {

        String username = "admin";

        // Act
        Optional<User> result = userRepository.findByUsername(username);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("admin");
        assertThat(result.get().getRole()).isNotNull();

        System.out.println("Success: User found " + result.get().getUsername());
    }

    @Test
    void findByUsername_ShouldReturnEmpty_WhenUserDoesNotExist() {
        // Arrange
        String nonExistentUser = "non-existent user 999";

        // Act
        Optional<User> result = userRepository.findByUsername(nonExistentUser);

        // Assert
        assertThat(result).isEmpty();

        System.out.println("Success: Correctly returned Optional.empty() for no user.");
    }

    @Test
    void save_ShouldInsertNewUser() {
        // Arrange
        User newUser = User.builder()
                .username("new_test_user")
                .passwordHash("hashed_pw_123")
                .role(Role.USER)
                .build();

        // Act
        userRepository.save(newUser);

        // Assert
        Optional<User> retrieved = userRepository.findByUsername("new_test_user");
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getUsername()).isEqualTo("new_test_user");
    }

}