package org.kamil.librarymanager.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer yearPublished;
    private String isbn;
    private BookStatus status;
    private Long categoryId;
    private String categoryName;
}
