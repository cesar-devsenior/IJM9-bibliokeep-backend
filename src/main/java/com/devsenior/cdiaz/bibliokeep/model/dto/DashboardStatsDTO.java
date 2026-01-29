package com.devsenior.cdiaz.bibliokeep.model.dto;

public record DashboardStatsDTO(
        Long totalBooks,
        Long activeLoans,
        Long returnedLoansThisYear,
        Integer annualGoal,
        Double progressPercentage
) {
}
