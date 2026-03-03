package org.kamil.librarymanager.service;

import org.kamil.librarymanager.model.Book;
import org.kamil.librarymanager.model.BookStatus;
import org.kamil.librarymanager.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public void addBook(String title, String author) {
        Book newBook = new Book(null, title, author, BookStatus.AVAILABLE);
        bookRepository.save(newBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id);
    }
}
