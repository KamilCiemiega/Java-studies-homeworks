package org.kamil.librarymanager.repository;

import org.kamil.librarymanager.model.User;

public interface UserRepository {
    User findByUsername(String username);
}
