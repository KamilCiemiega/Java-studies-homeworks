package org.kamil.librarymanager.service;

import org.kamil.librarymanager.model.Book;
import org.kamil.librarymanager.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() {
        return bookRepository.finAll();
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.finAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.finAll().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void addBook(String title, String author) {
        bookRepository.save(new Book(null, title, author));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteBId(id);
    }
}
