package com.devsenior.cdiaz.bibliokeep.model.dto;

import java.time.LocalDate;

public record LoanResponseDTO(
        Long id,
        Long bookId,
        String contactName,
        LocalDate loanDate,
        LocalDate dueDate,
        Boolean returned
) {
}
