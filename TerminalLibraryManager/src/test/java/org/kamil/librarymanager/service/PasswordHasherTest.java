package org.kamil.librarymanager.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PasswordHasherTest {
    private final PasswordHasher hasher = new Sha256PasswordHasher();

    @Test
    void shouldHashPasswordCorrectly() {
        // Arrange
        String pass = "admin123";

        // Act
        String hash1 = hasher.hash(pass);
        String hash2 = hasher.hash(pass);

        // Assert
        assertThat(hash1).isNotNull();
        assertThat(hash1).isEqualTo(hash2); // Consistency check
        assertThat(hash1).hasSize(64);      // SHA-256 length in Hex
    }

    @Test
    void shouldReturnNullWhenPasswordIsNull() {
        // Act & Assert
        assertThat(hasher.hash(null)).isNull();
    }

    @Test
    void shouldProduceDifferentHashesForDifferentPasswords() {
        // Act
        String hash1 = hasher.hash("password123");
        String hash2 = hasher.hash("password124");

        // Assert
        assertThat(hash1).isNotEqualTo(hash2);
    }

}