package com.devsenior.cdiaz.bibliokeep.controller;

import com.devsenior.cdiaz.bibliokeep.model.dto.LoanRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.LoanResponseDTO;
import com.devsenior.cdiaz.bibliokeep.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponseDTO createLoan(
            @Valid @RequestBody LoanRequestDTO loanRequestDTO,
            @RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        return loanService.createLoan(loanRequestDTO, userId);
    }

    @GetMapping("/{id}")
    public LoanResponseDTO getLoanById(
            @PathVariable Long id,
            @RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        return loanService.getLoanById(id, userId);
    }

    @GetMapping
    public List<LoanResponseDTO> getAllLoans(@RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        return loanService.getAllLoansByUser(userId);
    }

    @PutMapping("/{id}")
    public LoanResponseDTO updateLoan(
            @PathVariable Long id,
            @Valid @RequestBody LoanRequestDTO loanRequestDTO,
            @RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        return loanService.updateLoan(id, loanRequestDTO, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(
            @PathVariable Long id,
            @RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        loanService.deleteLoan(id, userId);
    }

    @PatchMapping("/{id}/return")
    public LoanResponseDTO returnLoan(
            @PathVariable Long id,
            @RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        return loanService.returnLoan(id, userId);
    }
}
