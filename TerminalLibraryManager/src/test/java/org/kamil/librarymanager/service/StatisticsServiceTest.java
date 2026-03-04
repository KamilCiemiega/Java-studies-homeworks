package org.kamil.librarymanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kamil.librarymanager.repository.BookRepository;
import static org.mockito.Mockito.*;

class StatisticsServiceTest {
    private BookRepository bookRepository;
    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        // Create the mock
        bookRepository = mock(BookRepository.class);
        // Inject it into the service
        statisticsService = new StatisticsService(bookRepository);
    }

    @Test
    void shouldCallRepositoryWhenShowingAdminStats() {
        // Act
        statisticsService.showAdminStats();

        // Assert: Check if the repository method was actually called
        verify(bookRepository, times(1)).printFullStatistics();
    }

    @Test
    void shouldCallRepositoryWhenDisplayingStatistics() {
        // Act
        statisticsService.displayLibraryStatistics();

        // Assert: Verifies that both methods lead to the repository call
        verify(bookRepository, times(1)).printFullStatistics();
    }

}