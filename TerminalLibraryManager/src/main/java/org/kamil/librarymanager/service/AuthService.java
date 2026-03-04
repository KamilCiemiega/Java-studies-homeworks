package org.kamil.librarymanager.service;


import org.kamil.librarymanager.model.User;
import org.kamil.librarymanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public AuthService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public Optional<User> login(String username, String password) {

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        String hashedInput = passwordHasher.hash(password);

        if (hashedInput.equals(user.getPasswordHash())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
