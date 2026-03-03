package org.kamil.librarymanager;

import org.kamil.librarymanager.repository.BookRepository;
import org.kamil.librarymanager.repository.JdbcBookRepositoryImpl;
import org.kamil.librarymanager.repository.JdbcUserRepositoryImpl;
import org.kamil.librarymanager.repository.UserRepository;
import org.kamil.librarymanager.service.AuthService;
import org.kamil.librarymanager.service.BookService;
import org.kamil.librarymanager.service.PasswordHasher;
import org.kamil.librarymanager.service.Sha256PasswordHasher;
import org.kamil.librarymanager.ui.ConsoleView;


public class Main {
    public static void main(String[] args) {
        BookRepository bookRepository = new JdbcBookRepositoryImpl();
        UserRepository userRepository = new JdbcUserRepositoryImpl();


        PasswordHasher passwordHasher = new Sha256PasswordHasher();

        BookService bookService = new BookService(bookRepository);
        AuthService authService = new AuthService(userRepository, passwordHasher);

        ConsoleView view = new ConsoleView(bookService, authService);
        view.start();

    }
}