package com.devsenior.cdiaz.bibliokeep.service;

import com.devsenior.cdiaz.bibliokeep.model.dto.DashboardStatsDTO;

import java.util.UUID;

public interface StatsService {

    DashboardStatsDTO getDashboardStats(UUID userId);
}
