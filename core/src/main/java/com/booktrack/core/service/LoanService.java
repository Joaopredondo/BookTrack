package com.booktrack.core.service;

import com.booktrack.core.dto.BookDTO;
import com.booktrack.core.dto.LoanDTO;
import com.booktrack.core.dto.UserDTO;
import com.booktrack.core.model.Book;
import com.booktrack.core.model.Loan;
import com.booktrack.core.model.User;
import com.booktrack.core.repository.BookRepository;
import com.booktrack.core.repository.LoanRepository;
import com.booktrack.core.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<LoanDTO> getLoanById(Long id) {
        return loanRepository.findById(id).map(this::convertToDTO);
    }

    public LoanDTO createLoan(LoanDTO loanDTO) {
        Loan loan = convertToEntity(loanDTO);
        Loan savedLoand = loanRepository.save(loan);
        return convertToDTO(savedLoand);
    }

    public LoanDTO updateLoan(Long id, LoanDTO loanDTO) {
        return loanRepository.findById(id)
            .map(loan -> {
                loan.setReturnDate(loanDTO.getReturnDate());
                loan.setStatus(loanDTO.getStatus());
                return convertToDTO(loanRepository.save(loan));
            }).orElseThrow(() -> new RuntimeException("Empréstimo não encontrado com o Id" + id));
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    public List<BookDTO> recommendBooks(Long userId) {
        List<String> categories = loanRepository.findByUserId(userId).stream()
            .map(loan -> loan.getBook().getCategory())
            .distinct()
            .toList();

        return bookRepository.findAll().stream()
            .filter(book -> categories.contains(book.getCategory()))
            .filter(book -> loanRepository.findByUserIdAndBookId(userId, book.getId()).isEmpty())
            .map(this::convertBookToDTO)
            .toList();
    }

    private LoanDTO convertToDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setLoanDate(loan.getLoanDate());
        loanDTO.setReturnDate(loan.getReturnDate());
        loanDTO.setStatus(loan.getStatus());
        loanDTO.setUser(convertUserToDTO(loan.getUser()));
        loanDTO.setBook(convertBookToDTO(loan.getBook()));
        return loanDTO;
    }

    private Loan convertToEntity(LoanDTO loanDTO) {
        Loan loan = new Loan();
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setReturnDate(loanDTO.getReturnDate());
        loan.setStatus(loanDTO.getStatus());
        loan.setUser(userRepository.findById(loanDTO.getUser().getId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com esse id " + loanDTO.getUser().getId())));
        loan.setBook(bookRepository.findById(loanDTO.getBook().getId())
            .orElseThrow(() -> new RuntimeException("Livro não encontrado com esse id  " + loanDTO.getBook().getId())));
        return loan;
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRegistrationDate(user.getRegistrationDate());
        return userDTO;
    }

    private BookDTO convertBookToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublicationDate(book.getPublicationDate());
        bookDTO.setCategory(book.getCategory());
        return bookDTO;
    }
}
