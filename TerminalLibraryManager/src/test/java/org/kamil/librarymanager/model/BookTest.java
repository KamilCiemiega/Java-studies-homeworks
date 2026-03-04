package org.kamil.librarymanager.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BookTest {
    @Test
    void testBookGettersAndSetters() {
        // 1. Test the Builder (Hits most of the lines)
        Book book = Book.builder()
                .id(1L)
                .title("The Witcher")
                .author("Andrzej Sapkowski")
                .yearPublished(1993)
                .isbn("978-8375906257")
                .status(BookStatus.AVAILABLE)
                .categoryId(5L)
                .categoryName("Fantasy")
                .build();

        // Assert all fields from builder
        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("The Witcher");
        assertThat(book.getAuthor()).isEqualTo("Andrzej Sapkowski");
        assertThat(book.getYearPublished()).isEqualTo(1993);
        assertThat(book.getIsbn()).isEqualTo("978-8375906257");
        assertThat(book.getStatus()).isEqualTo(BookStatus.AVAILABLE);
        assertThat(book.getCategoryId()).isEqualTo(5L);
        assertThat(book.getCategoryName()).isEqualTo("Fantasy");

        // 2. Test Setters (Hits the @Data / @Setter lines)
        book.setId(2L);
        book.setStatus(BookStatus.RENTED);
        assertThat(book.getId()).isEqualTo(2L);
        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTED);
    }

    @Test
    void testLombokSpecialMethods() {
        // This hits the toString(), equals(), and hashCode() lines for 100% coverage
        Book book1 = Book.builder().id(1L).title("Test").build();
        Book book2 = Book.builder().id(1L).title("Test").build();
        Book book3 = Book.builder().id(2L).title("Different").build();

        // Test toString
        assertThat(book1.toString()).contains("title=Test");

        // Test equals and hashCode
        assertThat(book1).isEqualTo(book2);
        assertThat(book1).isNotEqualTo(book3);
        assertThat(book1.hashCode()).isEqualTo(book2.hashCode());

        // Test No-Args Constructor
        Book emptyBook = new Book();
        assertThat(emptyBook).isNotNull();
    }

}