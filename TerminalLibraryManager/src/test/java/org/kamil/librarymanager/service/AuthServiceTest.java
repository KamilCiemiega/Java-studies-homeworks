package org.kamil.librarymanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kamil.librarymanager.model.Role;
import org.kamil.librarymanager.model.User;
import org.kamil.librarymanager.repository.UserRepository;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordHasher passwordHasher;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        // Create the mocks
        userRepository = mock(UserRepository.class);
        passwordHasher = new Sha256PasswordHasher();
        authService = new AuthService(userRepository, passwordHasher);
    }

    @Test
    void shouldLoginSuccessfullyWithCorrectCredentials() {
        // Arrange
        String username = "admin";
        String pass = "admin123";
        User mockUser = new User(1L, username, passwordHasher.hash(pass), Role.ADMIN);

        // Standard Syntax
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        // Act
        Optional<User> result = authService.login(username, pass);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("admin");
    }

    @Test
    void shouldFailLoginWhenPasswordIsWrong() {
        // Arrange
        User mockUser = new User(1L, "admin", passwordHasher.hash("correct"), Role.ADMIN);
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(mockUser));

        // Act
        Optional<User> result = authService.login("admin", "wrong_password");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void shouldFailLoginWhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = authService.login("unknown", "any_pass");

        // Assert
        assertThat(result).isEmpty();
    }
}