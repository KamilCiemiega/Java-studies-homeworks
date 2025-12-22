package org.kamil.librarymanager;

import org.kamil.librarymanager.model.Role;
import org.kamil.librarymanager.model.User;
import org.kamil.librarymanager.repository.BookRepository;
import org.kamil.librarymanager.repository.InMemoryBookRepository;
import org.kamil.librarymanager.repository.InMemoryUserRepository;
import org.kamil.librarymanager.repository.UserRepository;
import org.kamil.librarymanager.service.AuthService;
import org.kamil.librarymanager.service.BookService;
import org.kamil.librarymanager.service.PasswordHasher;
import org.kamil.librarymanager.service.Sha256PasswordHasher;
import org.kamil.librarymanager.ui.ConsoleUI;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        PasswordHasher hasher = new Sha256PasswordHasher();

        User admin = new User("admin", hasher.hash("admin123"), Role.ADMIN);
        User user = new User("user", hasher.hash("user123"), Role.USER);

        UserRepository userRepository =
                new InMemoryUserRepository(List.of(admin, user));

        BookRepository bookRepository = new InMemoryBookRepository();

        AuthService authService = new AuthService(userRepository, hasher);
        BookService bookService = new BookService(bookRepository);

        ConsoleUI ui = new ConsoleUI(authService, bookService);
        ui.start();
    }
}