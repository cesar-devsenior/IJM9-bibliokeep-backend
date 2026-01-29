package com.devsenior.cdiaz.bibliokeep.repository;

import com.devsenior.cdiaz.bibliokeep.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByBook_OwnerId(UUID ownerId);

    @Query("SELECT l FROM Loan l WHERE l.book.ownerId = :ownerId AND l.returned = false")
    List<Loan> findActiveLoansByOwnerId(@Param("ownerId") UUID ownerId);

    @Query("SELECT l FROM Loan l WHERE l.returned = false AND l.dueDate < :currentDate")
    List<Loan> findOverdueLoans(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT COUNT(l) FROM Loan l WHERE l.book.ownerId = :ownerId AND l.returned = true AND YEAR(l.loanDate) = :year")
    Long countReturnedLoansByOwnerAndYear(@Param("ownerId") UUID ownerId, @Param("year") int year);
}
