package com.devsenior.cdiaz.bibliokeep.service.impl;

import com.devsenior.cdiaz.bibliokeep.model.dto.DashboardStatsDTO;
import com.devsenior.cdiaz.bibliokeep.model.entity.User;
import com.devsenior.cdiaz.bibliokeep.repository.BookRepository;
import com.devsenior.cdiaz.bibliokeep.repository.LoanRepository;
import com.devsenior.cdiaz.bibliokeep.repository.UserRepository;
import com.devsenior.cdiaz.bibliokeep.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDTO getDashboardStats(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        var totalBooks = bookRepository.countByOwnerId(userId);
        var activeLoans = (long) loanRepository.findActiveLoansByOwnerId(userId).size();
        var currentYear = LocalDate.now().getYear();
        var returnedLoansThisYear = loanRepository.countReturnedLoansByOwnerAndYear(userId, currentYear);
        var annualGoal = user.getAnnualGoal();
        
        var progressPercentage = annualGoal > 0 
                ? (returnedLoansThisYear.doubleValue() / annualGoal) * 100.0
                : 0.0;

        return new DashboardStatsDTO(
                totalBooks,
                activeLoans,
                returnedLoansThisYear,
                annualGoal,
                progressPercentage
        );
    }
}
