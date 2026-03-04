package org.kamil.librarymanager.repository;

import org.kamil.librarymanager.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    void save(User user);
}
