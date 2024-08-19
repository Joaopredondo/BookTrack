package com.booktrack.core.repository;

import com.booktrack.core.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);
    List<Loan> findByUserIdAndBookId(Long userId, Long bookId);
}
