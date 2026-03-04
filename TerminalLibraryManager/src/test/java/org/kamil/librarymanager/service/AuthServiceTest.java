package org.kamil.librarymanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        userRepository = mock(UserRepository.class);
        passwordHasher = mock(PasswordHasher.class);
        authService = new AuthService(userRepository, passwordHasher);
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsAreValid() {
        // Arrange
        String username = "kamil";
        String password = "password123";
        String hashedPassword = "hashed_password123";

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPasswordHash(hashedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(passwordHasher.hash(password)).thenReturn(hashedPassword);

        // Act
        Optional<User> result = authService.login(username, password);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo(username);
    }

    @Test
    void login_ShouldReturnEmpty_WhenUserNotFound() {
        // Arrange
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = authService.login("unknown", "any_password");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void login_ShouldReturnEmpty_WhenPasswordIsIncorrect() {
        // Arrange
        String username = "kamil";
        User mockUser = new User();
        mockUser.setPasswordHash("correct_hash");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(passwordHasher.hash("wrong_password")).thenReturn("wrong_hash");

        // Act
        Optional<User> result = authService.login(username, "wrong_password");

        // Assert
        assertThat(result).isEmpty();
    }
}