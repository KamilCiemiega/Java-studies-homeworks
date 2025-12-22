package org.kamil.librarymanager.repository;

import org.kamil.librarymanager.model.User;

import java.util.List;

public class InMemoryUserRepository implements UserRepository {

    private final List<User> users;

    public InMemoryUserRepository(List<User> users) {
        this.users =  users;
    }

    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
