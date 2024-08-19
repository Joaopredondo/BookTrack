package com.booktrack.core.controller;

import com.booktrack.core.dto.BookDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Clean Code");
        bookDTO.setAuthor("Robert C. Martin");
        bookDTO.setIsbn("9780132350884");
        bookDTO.setPublicationDate(LocalDate.parse("2008-08-01"));
        bookDTO.setCategory("Software Engineering");

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));
    }

    @Test
    void testGetBookById() throws Exception {
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));
    }

    @Test
    void testUpdateBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Refactoring");
        bookDTO.setAuthor("Martin Fowler");
        bookDTO.setIsbn("9780201485677");
        bookDTO.setPublicationDate(LocalDate.parse("1999-07-08"));
        bookDTO.setCategory("Software Engineering");

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Refactoring"))
                .andExpect(jsonPath("$.author").value("Martin Fowler"));
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());
    }
}