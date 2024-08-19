package com.booktrack.core.service;

import com.booktrack.core.dto.BookDTO;
import com.booktrack.core.model.Book;
import com.booktrack.core.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
         this.bookRepository = bookRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
            .map(this::convertToDTO)
            .toList();
    }

    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id).map(this::convertToDTO);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookDTO.getTitle());
                    book.setAuthor(bookDTO.getTitle());
                    book.setIsbn(bookDTO.getIsbn());
                    book.setCategory(bookDTO.getCategory());
                    book.setPublicationDate(bookDTO.getPublicationDate());
                    return convertToDTO(bookRepository.save(book));
                }).orElseThrow(() -> new RuntimeException("Livro não encontrado com o Id" + id));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublicationDate(book.getPublicationDate());
        bookDTO.setCategory(book.getCategory());
        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setCategory(bookDTO.getCategory());
        return book;
    }

}
