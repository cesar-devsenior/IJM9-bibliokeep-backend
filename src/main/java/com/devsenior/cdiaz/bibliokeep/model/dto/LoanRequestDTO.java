package com.devsenior.cdiaz.bibliokeep.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record LoanRequestDTO(
        @NotNull(message = "El ID del libro es obligatorio")
        Long bookId,
        
        @NotBlank(message = "El nombre del contacto es obligatorio")
        String contactName,
        
        @NotNull(message = "La fecha de préstamo es obligatoria")
        LocalDate loanDate,
        
        @NotNull(message = "La fecha de vencimiento es obligatoria")
        LocalDate dueDate
) {
}
