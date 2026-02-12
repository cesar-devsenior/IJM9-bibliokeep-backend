package com.devsenior.cdiaz.bibliokeep.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsenior.cdiaz.bibliokeep.mapper.LoanMapper;
import com.devsenior.cdiaz.bibliokeep.model.dto.LoanRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.LoanResponseDTO;
import com.devsenior.cdiaz.bibliokeep.repository.BookRepository;
import com.devsenior.cdiaz.bibliokeep.repository.LoanRepository;
import com.devsenior.cdiaz.bibliokeep.service.LoanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl extends TokenDataService implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    @Override
    @Transactional
    public LoanResponseDTO createLoan(LoanRequestDTO loanRequestDTO) {
        var book = bookRepository.findById(loanRequestDTO.bookId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + loanRequestDTO.bookId()));

        if (book.getIsLent()) {
            throw new RuntimeException("El libro ya está prestado");
        }

        var loan = loanMapper.toEntity(loanRequestDTO);
        loan.setBook(book);
        loan.setReturned(false);

        book.setIsLent(true);
        bookRepository.save(book);

        var savedLoan = loanRepository.save(loan);
        return loanMapper.toResponseDTO(savedLoan);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanResponseDTO getLoanById(Long id) {
        var loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));

        return loanMapper.toResponseDTO(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponseDTO> getAllLoansByUser() {
        var loans = loanRepository.findByBook_OwnerId(getUserId());
        return loans.stream()
                .map(loanMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public LoanResponseDTO updateLoan(Long id, LoanRequestDTO loanRequestDTO) {
        var loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));

        if (loan.getReturned()) {
            throw new RuntimeException("No se puede modificar un préstamo ya devuelto");
        }

        var book = bookRepository.findById(loanRequestDTO.bookId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + loanRequestDTO.bookId()));

        if (!book.getOwnerId().equals(getUserId())) {
            throw new RuntimeException("No tienes permiso para usar este libro");
        }

        loanMapper.updateEntityFromDTO(loanRequestDTO, loan);
        loan.setBook(book);

        var updatedLoan = loanRepository.save(loan);
        return loanMapper.toResponseDTO(updatedLoan);
    }

    @Override
    @Transactional
    public void deleteLoan(Long id) {
        var loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));

        if (!loan.getReturned()) {
            var book = loan.getBook();
            book.setIsLent(false);
            bookRepository.save(book);
        }

        loanRepository.delete(loan);
    }

    @Override
    @Transactional
    public LoanResponseDTO returnLoan(Long id) {
        var loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));

        if (loan.getReturned()) {
            throw new RuntimeException("El préstamo ya fue devuelto");
        }

        loan.setReturned(true);
        var book = loan.getBook();
        book.setIsLent(false);
        bookRepository.save(book);

        var returnedLoan = loanRepository.save(loan);
        return loanMapper.toResponseDTO(returnedLoan);
    }
}
