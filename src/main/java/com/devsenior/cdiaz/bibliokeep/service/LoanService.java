package com.devsenior.cdiaz.bibliokeep.service;

import java.util.List;

import com.devsenior.cdiaz.bibliokeep.model.dto.LoanRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.LoanResponseDTO;

public interface LoanService {

    LoanResponseDTO createLoan(LoanRequestDTO loanRequestDTO);

    LoanResponseDTO getLoanById(Long id);

    List<LoanResponseDTO> getAllLoansByUser();

    LoanResponseDTO updateLoan(Long id, LoanRequestDTO loanRequestDTO);

    void deleteLoan(Long id);

    LoanResponseDTO returnLoan(Long id);
}
