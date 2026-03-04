package org.kamil.librarymanager.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Sha256PasswordHasherTest {
    private final PasswordHasher hasher = new Sha256PasswordHasher();

    @Test
    void shouldHashPasswordCorrectly() {
        String password = "admin";
        String hash = hasher.hash(password);

        assertThat(hash).isNotNull();
        assertThat(hash).hasSize(64);
    }

    @Test
    void shouldBeConsistent() {
        String p1 = "password123";
        assertThat(hasher.hash(p1)).isEqualTo(hasher.hash(p1));
    }

    @Test
    void shouldHandleEmptyString() {
        String hash = hasher.hash("");
        assertThat(hash).hasSize(64);
    }
}