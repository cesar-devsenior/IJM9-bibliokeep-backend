package com.devsenior.cdiaz.bibliokeep.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "El libro es obligatorio")
    private Book book;

    @Column(name = "contact_name", nullable = false)
    @NotBlank(message = "El nombre del contacto es obligatorio")
    private String contactName;

    @Column(name = "loan_date", nullable = false)
    @NotNull(message = "La fecha de préstamo es obligatoria")
    private LocalDate loanDate;

    @Column(name = "due_date", nullable = false)
    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate dueDate;

    @Column(nullable = false)
    @NotNull(message = "El campo returned es obligatorio")
    @Builder.Default
    private Boolean returned = false;
}
