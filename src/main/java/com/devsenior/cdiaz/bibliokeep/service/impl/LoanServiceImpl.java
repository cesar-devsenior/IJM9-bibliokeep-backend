package com.devsenior.cdiaz.bibliokeep.service.impl;

import java.util.List;
import java.util.UUID;

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
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    @Override
    @Transactional
    public LoanResponseDTO createLoan(LoanRequestDTO loanRequestDTO, UUID userId) {
        var book = bookRepository.findById(loanRequestDTO.bookId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + loanRequestDTO.bookId()));

        if (!book.getOwnerId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para prestar este libro");
        }

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
    public LoanResponseDTO getLoanById(Long id, UUID userId) {
        var loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));

        if (!loan.getBook().getOwnerId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para acceder a este préstamo");
        }

        return loanMapper.toResponseDTO(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponseDTO> getAllLoansByUser(UUID userId) {
        var loans = loanRepository.findByBook_OwnerId(userId);
        return loans.stream()
                .map(loanMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public LoanResponseDTO updateLoan(Long id, LoanRequestDTO loanRequestDTO, UUID userId) {
        var loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));

        if (!loan.getBook().getOwnerId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para modificar este préstamo");
        }

        if (loan.getReturned()) {
            throw new RuntimeException("No se puede modificar un préstamo ya devuelto");
        }

        var book = bookRepository.findById(loanRequestDTO.bookId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + loanRequestDTO.bookId()));

        if (!book.getOwnerId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para usar este libro");
        }

        loanMapper.updateEntityFromDTO(loanRequestDTO, loan);
        loan.setBook(book);

        var updatedLoan = loanRepository.save(loan);
        return loanMapper.toResponseDTO(updatedLoan);
    }

    @Override
    @Transactional
    public void deleteLoan(Long id, UUID userId) {
        var loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));

        if (!loan.getBook().getOwnerId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para eliminar este préstamo");
        }

        if (!loan.getReturned()) {
            var book = loan.getBook();
            book.setIsLent(false);
            bookRepository.save(book);
        }

        loanRepository.delete(loan);
    }

    @Override
    @Transactional
    public LoanResponseDTO returnLoan(Long id, UUID userId) {
        var loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));

        if (!loan.getBook().getOwnerId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para devolver este préstamo");
        }

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
