package org.kamil.librarymanager.repository;

import org.kamil.librarymanager.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> finAll();
    void save(Book book);
    void deleteBId(Long id);
    Book finById(Long id);
}
