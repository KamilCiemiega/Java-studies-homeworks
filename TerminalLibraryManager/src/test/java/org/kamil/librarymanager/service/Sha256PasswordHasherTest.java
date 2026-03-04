package org.kamil.librarymanager.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Sha256PasswordHasherTest {
    private final PasswordHasher hasher = new Sha256PasswordHasher();

    @Test
    void shouldGenerateConsistentHash() {
        String password = "admin123";
        String hash = hasher.hash(password);

        assertThat(hash).isEqualTo("240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9");
    }
}