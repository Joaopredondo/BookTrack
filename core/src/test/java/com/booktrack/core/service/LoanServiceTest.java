package com.booktrack.core.service;

import com.booktrack.core.dto.LoanDTO;
import com.booktrack.core.model.Loan;
import com.booktrack.core.repository.LoanRepository;
import com.booktrack.core.repository.UserRepository;
import com.booktrack.core.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanService loanService;

    public LoanServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLoan() {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setStatus("ACTIVE");

        Loan loan = new Loan();
        loan.setStatus("ACTIVE");

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        LoanDTO createdLoan = loanService.createLoan(loanDTO);

        assertEquals("ACTIVE", createdLoan.getStatus());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void testGetLoanById() {
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setStatus("ACTIVE");

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        Optional<LoanDTO> foundLoan = loanService.getLoanById(1L);

        assertEquals("ACTIVE", foundLoan.get().getStatus());
        verify(loanRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateLoanNotFound() {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setStatus("RETURNED");

        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            loanService.updateLoan(1L, loanDTO);
        } catch (RuntimeException e) {
            assertEquals("Loan not found with id 1", e.getMessage());
        }

        verify(loanRepository, times(1)).findById(1L);
    }
}