package com.devsenior.cdiaz.bibliokeep.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.cdiaz.bibliokeep.model.dto.LoanRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.LoanResponseDTO;
import com.devsenior.cdiaz.bibliokeep.service.LoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponseDTO createLoan(
            @Valid @RequestBody LoanRequestDTO loanRequestDTO) {
        return loanService.createLoan(loanRequestDTO);
    }

    @GetMapping("/{id}")
    public LoanResponseDTO getLoanById(
            @PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @GetMapping
    public List<LoanResponseDTO> getAllLoans() {
        return loanService.getAllLoansByUser();
    }

    @PutMapping("/{id}")
    public LoanResponseDTO updateLoan(
            @PathVariable Long id,
            @Valid @RequestBody LoanRequestDTO loanRequestDTO) {
        return loanService.updateLoan(id, loanRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(
            @PathVariable Long id) {
        loanService.deleteLoan(id);
    }

    @PatchMapping("/{id}/return")
    public LoanResponseDTO returnLoan(
            @PathVariable Long id) {
        return loanService.returnLoan(id);
    }
}
