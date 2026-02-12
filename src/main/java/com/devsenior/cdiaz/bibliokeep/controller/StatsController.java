package com.devsenior.cdiaz.bibliokeep.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.cdiaz.bibliokeep.model.dto.DashboardStatsDTO;
import com.devsenior.cdiaz.bibliokeep.service.StatsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/dashboard")
    public DashboardStatsDTO getDashboardStats() {
        return statsService.getDashboardStats();
    }
}
