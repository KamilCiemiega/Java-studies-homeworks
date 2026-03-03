package org.kamil.librarymanager.ui;

import org.kamil.librarymanager.model.Role;
import org.kamil.librarymanager.model.User;
import org.kamil.librarymanager.service.AuthService;
import org.kamil.librarymanager.service.BookService;

import java.util.Scanner;

public class ConsoleView {
    private final BookService bookService;
    private final AuthService authService;
    private final Scanner scanner;
    private User currentUser;

    public ConsoleView(BookService bookService, AuthService authService) {
        this.bookService = bookService;
        this.authService = authService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Welcome to Library Manager ===");
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n1. Login\n2. Exit");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            currentUser = authService.login(username, password);
            if (currentUser != null) {
                System.out.println("Login successful! Welcome " + currentUser.getUsername());
            } else {
                System.out.println("Invalid credentials.");
            }
        } else {
            System.exit(0);
        }
    }

    private void showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. View All Books");
        System.out.println("2. Search for a Book");

        // Role-based Access Control (Requirement!)
        if (currentUser.getRole() == Role.ADMIN) {
            System.out.println("3. Add New Book (Admin Only)");
            System.out.println("4. Delete Book (Admin Only)");
        }

        System.out.println("L. Logout");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().toUpperCase();

        switch (choice) {
            case "1" -> listBooks();
            case "2" -> searchBooks();
            case "3" -> { if (currentUser.getRole() == Role.ADMIN) addBook(); }
            case "4" -> { if (currentUser.getRole() == Role.ADMIN) deleteBook(); }
            case "L" -> currentUser = null;
            default -> System.out.println("Invalid option.");
        }
    }

    private void listBooks() {
        System.out.println("\nList of Books:");
        bookService.getAll().forEach(book ->
                System.out.println("[" + book.getId() + "] " + book.getTitle() + " - " + book.getAuthor() + " (" + book.getStatus() + ")")
        );
    }

    private void addBook() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        bookService.addBook(title, author);
        System.out.println("Book added to Database!");
    }

    private void deleteBook() {
        System.out.print("Enter ID to delete: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            bookService.deleteBook(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void searchBooks() {
        System.out.print("Enter title snippet: ");
        String query = scanner.nextLine();
        bookService.searchByTitle(query).forEach(System.out::println);
    }
}
