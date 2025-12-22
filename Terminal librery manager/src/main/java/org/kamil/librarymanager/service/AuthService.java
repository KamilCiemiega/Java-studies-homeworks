package org.kamil.librarymanager.service;

import org.kamil.librarymanager.model.User;
import org.kamil.librarymanager.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public AuthService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;

        String hashed = passwordHasher.hash(password);
        return hashed.equals(user.getPasswordHash()) ? user : null;
    }
}
