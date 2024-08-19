package com.booktrack.core.dto;

import lombok.Data;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Data
public class LoanDTO {
    @NotNull(message = "Loan ID cannot be null")
    private Long id;
    @NotNull(message = "User cannot be null")
    private UserDTO user;
    @NotNull(message = "Book cannot be null")
    private BookDTO book;
    @NotNull(message = "Loan date cannot be null")
    @PastOrPresent(message = "Loan date cannot be in the future")
    private LocalDate loanDate;
    @NotNull(message = "Return date cannot be null")
    private LocalDate returnDate;
    @NotNull(message = "Status cannot be null")
    private String status;
}