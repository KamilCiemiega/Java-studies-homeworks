package org.kamil.librarymanager.service;

import org.kamil.librarymanager.repository.BookRepository;

public class StatisticsService {
    private final BookRepository bookRepository;

    // Constructor injection allows us to swap the real repo for a mock in tests
    public StatisticsService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void showAdminStats() {
        bookRepository.printFullStatistics();
    }

    public void displayLibraryStatistics() {
        // Calling the other method prevents code duplication and covers both paths
        showAdminStats();
    }
}
