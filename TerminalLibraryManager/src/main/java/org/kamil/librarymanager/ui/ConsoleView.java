package org.kamil.librarymanager.ui;

import org.kamil.librarymanager.model.Role;
import org.kamil.librarymanager.model.User;
import org.kamil.librarymanager.repository.RentalRepository;
import org.kamil.librarymanager.service.AuthService;
import org.kamil.librarymanager.service.BookService;

import java.util.Scanner;

public class ConsoleView {
    private final BookService bookService;
    private final AuthService authService;
    private final RentalRepository rentalRepository;
    private final Scanner scanner;
    private User currentUser;

    public ConsoleView(BookService bookService, AuthService authService, RentalRepository rentalRepository) {
        this.bookService = bookService;
        this.authService = authService;
        this.rentalRepository = rentalRepository;
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
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. List All Books");
        System.out.println("2. Search by Title");
        System.out.println("3. Rent a Book (Feature #1)");
        System.out.println("4. Filter by Category (Feature #2)");

        if (currentUser.getRole() == Role.ADMIN) {
            System.out.println("5. Add New Book");
            System.out.println("6. Delete Book");
            System.out.println("7. View Library Statistics (Feature #3)");
        }

        System.out.println("L. Logout");
        System.out.print("Choice: ");
        String choice = scanner.nextLine().toUpperCase();

        switch (choice) {
            case "1" -> listBooks();
            case "2" -> searchBooks();
            case "3" -> rentBook();
            case "4" -> filterByCategory();
            case "5" -> { if (currentUser.getRole() == Role.ADMIN) addBook(); }
            case "6" -> { if (currentUser.getRole() == Role.ADMIN) deleteBook(); }
            case "7" -> { if (currentUser.getRole() == Role.ADMIN) displayStatistics(); }
            case "L" -> {
                currentUser = null;
                System.out.println("Logged out successfully.");
            }
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private void listBooks() {
        System.out.println("\nList of Books:");
        bookService.getAll().forEach(book ->
                System.out.println("[" + book.getId() + "] " + book.getTitle() + " - " + book.getAuthor() + " (" + book.getStatus() + ")")
        );
    }

    private void addBook() {
        try {
            System.out.println("\n--- Add New Book ---");

            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Author: ");
            String author = scanner.nextLine();

            System.out.print("Publication Year: ");
            int year = Integer.parseInt(scanner.nextLine());

            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();

            System.out.print("Category ID: ");
            Long categoryId = Long.parseLong(scanner.nextLine());

            bookService.addBook(title, author, year, isbn, categoryId);

            System.out.println("Success: Book added to Database!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Year and Category ID must be numbers.");
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred: " + e.getMessage());
        }
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

    private void displayStatistics() {
        System.out.println("\nFetching latest data from database...");
        // Assuming you put the logic in bookService
        bookService.showAdminStats();
    }
}
