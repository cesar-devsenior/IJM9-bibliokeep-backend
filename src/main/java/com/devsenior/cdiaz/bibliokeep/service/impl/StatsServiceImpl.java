package com.devsenior.cdiaz.bibliokeep.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsenior.cdiaz.bibliokeep.model.dto.DashboardStatsDTO;
import com.devsenior.cdiaz.bibliokeep.model.entity.BookStatus;
import com.devsenior.cdiaz.bibliokeep.repository.BookRepository;
import com.devsenior.cdiaz.bibliokeep.repository.LoanRepository;
import com.devsenior.cdiaz.bibliokeep.repository.UserRepository;
import com.devsenior.cdiaz.bibliokeep.service.StatsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl extends TokenDataService implements StatsService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDTO getDashboardStats() {
        var userId = getUserId();
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        var totalBooks = bookRepository.countByOwnerId(userId);
        var reading = bookRepository.countByOwnerIdAndStatus(userId, BookStatus.LEYENDO);
        var activeLoans = (long) loanRepository.findActiveLoansByOwnerId(userId).size();
        var returnedLoansThisYear = bookRepository.countByOwnerIdAndStatus(userId, BookStatus.LEIDO);
        // var currentYear = LocalDate.now().getYear();
        // var returnedLoansThisYear = loanRepository.countReturnedLoansByOwnerAndYear(userId, currentYear);
        var annualGoal = user.getAnnualGoal();
        
        var progressPercentage = annualGoal > 0 
                ? ( (double) returnedLoansThisYear / annualGoal) * 100.0
                : 0.0;

        return new DashboardStatsDTO(
                totalBooks,
                reading,
                activeLoans,
                returnedLoansThisYear,
                annualGoal,
                progressPercentage
        );
    }
}
