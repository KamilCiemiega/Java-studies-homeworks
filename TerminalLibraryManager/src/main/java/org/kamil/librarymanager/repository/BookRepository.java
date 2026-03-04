package org.kamil.librarymanager.repository;

import org.kamil.librarymanager.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();
    void save(Book book);
    void deleteById(Long id);
    Book findById(Long id);
    void printStatistics();
    void printFullStatistics();
}
