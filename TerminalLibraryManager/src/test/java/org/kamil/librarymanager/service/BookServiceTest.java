package org.kamil.librarymanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kamil.librarymanager.model.Book;
import org.kamil.librarymanager.model.BookStatus;
import org.kamil.librarymanager.repository.BookRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BookServiceTest {
    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void shouldReturnAllBooks() {
        List<Book> mockBooks = Arrays.asList(
                new Book(1L, "The Witcher", "Sapkowski", 1993, "ISBN1", BookStatus.AVAILABLE, 1L, "Fantasy"),
                new Book(2L, "Dune", "Herbert", 1965, "ISBN2", BookStatus.AVAILABLE, 2L, "Sci-Fi")
        );
        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> result = bookService.getAll();

        assertThat(result).hasSize(2);
        verify(bookRepository).findAll();
    }

    @Test
    void shouldSearchByTitleCorrectly() {
        Book book = new Book(1L, "Harry Potter", "Rowling", 1997, "ISBN3", BookStatus.AVAILABLE, 1L, "Fantasy");
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> result = bookService.searchByTitle("Harry");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).contains("Harry");
    }

    @Test
    void searchByTitle_ShouldHandleEmptyOrNullTitle() {
        bookService.searchByTitle("");
        bookService.searchByTitle(null);
        verify(bookRepository, times(2)).findAll();
    }

    @Test
    void shouldCallSaveWhenAddingBook() {
        bookService.addBook("New Book", "Author", 2024, "ISBN999", 1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
        // Annotation was missing previously
    void shouldDeleteBook() {
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldGetBookById() {
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(book);

        Book result = bookService.getBookById(1L);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldCallStatsInRepository() {
        bookService.showAdminStats();
        verify(bookRepository, times(1)).printStatistics();
    }

    @Test
    void getBooksByCategory_ShouldHandleAllBranches() {
        // This covers Null category, Non-matching category, and Matching category (6/6 branches)
        Book b1 = new Book(); b1.setCategoryName(null);
        Book b2 = new Book(); b2.setCategoryName("Science");
        Book b3 = new Book(); b3.setCategoryName("History");

        when(bookRepository.findAll()).thenReturn(List.of(b1, b2, b3));

        List<Book> result = bookService.getBooksByCategory("History");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoryName()).isEqualTo("History");
    }
    @Test
    void searchByTitle_ShouldHandleNonMatchingTitle() {
        Book book = new Book();
        book.setTitle("Java");
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> result = bookService.searchByTitle("Python");

        assertThat(result).isEmpty();
    }

    @Test
    void searchByTitle_ShouldHandleNullBookTitle() {
        Book book = new Book();
        book.setTitle(null);
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> result = bookService.searchByTitle("Any");

        assertThat(result).isEmpty();
    }
}