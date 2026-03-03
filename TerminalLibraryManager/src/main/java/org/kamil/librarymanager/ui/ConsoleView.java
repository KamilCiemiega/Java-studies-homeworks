package org.kamil.librarymanager.ui;

import org.kamil.librarymanager.model.Role;
import org.kamil.librarymanager.model.User;
import org.kamil.librarymanager.service.AuthService;
import org.kamil.librarymanager.service.BookService;

import java.util.Scanner;

public class ConsoleUI {
    private final AuthService authService;
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(AuthService authService, BookService bookService) {
        this.authService = authService;
        this.bookService = bookService;
    }

    public void start() {
        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = authService.login(login, password);

        if (user == null) {
            System.out.println("Incorrect login details");
            return;
        }

        System.out.println("Logged in as: " + user.getRole());

        if (user.getRole() == Role.ADMIN) {
            adminMenu();
        } else {
            userMenu();
        }
    }

    private void userMenu() {
        System.out.println("1. List of books");
        System.out.println("2. Search by title");
        System.out.println("3. Search by author");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> bookService.getAll().forEach(System.out::println);
            case 2 -> {
                System.out.print("Title: ");
                bookService.searchByTitle(scanner.nextLine()).forEach(System.out::println);
            }
            case 3 -> {
                System.out.print("Author: ");
                bookService.searchByAuthor(scanner.nextLine()).forEach(System.out::println);
            }
        }
    }

    private void adminMenu() {
        System.out.println("1. List of books");
        System.out.println("2. Add Book");
        System.out.println("3. Delete Book");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> bookService.getAll().forEach(System.out::println);
            case 2 -> {
                System.out.print("Title: ");
                String title = scanner.nextLine();
                System.out.print("Author: ");
                String author = scanner.nextLine();
                bookService.addBook(title, author);
            }
            case 3 -> {
                System.out.print("ID: ");
                bookService.deleteBook(Long.parseLong(scanner.nextLine()));
            }
        }
    }
}
