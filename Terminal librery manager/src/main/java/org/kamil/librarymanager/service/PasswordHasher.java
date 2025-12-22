package org.kamil.librarymanager.service;

public interface PasswordHasher {
    String hash(String password);
}
