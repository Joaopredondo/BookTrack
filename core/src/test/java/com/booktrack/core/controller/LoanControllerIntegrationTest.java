package com.booktrack.core.controller;

import com.booktrack.core.dto.LoanDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateLoan() throws Exception {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setStatus("ACTIVE");

        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetLoanById() throws Exception {
        mockMvc.perform(get("/api/loans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void testUpdateLoan() throws Exception {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setStatus("RETURNED");

        mockMvc.perform(put("/api/loans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanDTO)))
                .andExpect(status().isOk());
    }
}