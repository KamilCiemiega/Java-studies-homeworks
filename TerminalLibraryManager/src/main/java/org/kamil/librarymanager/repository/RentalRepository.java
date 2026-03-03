package org.kamil.librarymanager.repository;

public interface RentalRepository {
    void rentBook(Long userId, Long bookId);
    void returnBook(Long bookId);
}
