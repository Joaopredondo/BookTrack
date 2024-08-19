package com.booktrack.core.dto;

import lombok.Data;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class BookDTO {
    @NotNull(message = "Book ID cannot be null")
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Author cannot be blank")
    private String author;
    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;
    @NotNull(message = "Publication date cannot be null")
    private LocalDate publicationDate;
    @NotBlank(message = "Category cannot be blank")
    private String category;
}