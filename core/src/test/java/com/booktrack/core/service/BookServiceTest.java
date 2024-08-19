package com.booktrack.core.service;

import com.booktrack.core.dto.BookDTO;
import com.booktrack.core.model.Book;
import com.booktrack.core.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    public BookServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Clean Code");
        bookDTO.setAuthor("Robert C. Martin");
        bookDTO.setIsbn("9780132350884");

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setIsbn("9780132350884");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO createdBook = bookService.createBook(bookDTO);

        assertEquals("Clean Code", createdBook.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<BookDTO> foundBook = bookService.getBookById(1L);

        assertEquals("Clean Code", foundBook.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBookNotFound() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Clean Code");

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            bookService.updateBook(1L, bookDTO);
        } catch (RuntimeException e) {
            assertEquals("Book not found with id 1", e.getMessage());
        }

        verify(bookRepository, times(1)).findById(1L);
    }
}