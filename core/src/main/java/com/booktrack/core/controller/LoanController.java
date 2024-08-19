package com.booktrack.core.controller;

import com.booktrack.core.dto.BookDTO;
import com.booktrack.core.dto.LoanDTO;
import com.booktrack.core.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public List<LoanDTO> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long id) {
        Optional<LoanDTO> loan  = loanService.getLoanById(id);
        return loan.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<List<BookDTO>> recommendBooks(@PathVariable Long userId) {
        List<BookDTO> recommendations = loanService.recommendBooks(userId);
        if (recommendations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping
    public LoanDTO createLoan(@RequestBody LoanDTO loanDTO) {
        return loanService.createLoan(loanDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long id, @RequestBody LoanDTO loanDTO) {
        try{
            LoanDTO updatedLoan = loanService.updateLoan(id, loanDTO);
            return ResponseEntity.ok(updatedLoan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }
}
