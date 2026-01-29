package com.devsenior.cdiaz.bibliokeep.service;

import com.devsenior.cdiaz.bibliokeep.model.dto.LoanRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.LoanResponseDTO;

import java.util.List;
import java.util.UUID;

public interface LoanService {

    LoanResponseDTO createLoan(LoanRequestDTO loanRequestDTO, UUID userId);

    LoanResponseDTO getLoanById(Long id, UUID userId);

    List<LoanResponseDTO> getAllLoansByUser(UUID userId);

    LoanResponseDTO updateLoan(Long id, LoanRequestDTO loanRequestDTO, UUID userId);

    void deleteLoan(Long id, UUID userId);

    LoanResponseDTO returnLoan(Long id, UUID userId);
}
