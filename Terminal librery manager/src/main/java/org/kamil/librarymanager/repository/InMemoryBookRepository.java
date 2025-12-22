package org.kamil.librarymanager.repository;

import org.kamil.librarymanager.model.Book;

import java.util.ArrayList;
import java.util.List;

public class InMemoryBookRepository implements BookRepository {

    private final List<Book> books = new ArrayList<>();
    private long idCounter = 1;

    @Override
    public List<Book> finAll() {
        return books;
    }

    @Override
    public void save(Book book) {
        if (book.getId() == null) {
            book.setId(idCounter++);
            books.add(book);
        }
    }

    @Override
    public void deleteBId(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    @Override
    public Book finById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
