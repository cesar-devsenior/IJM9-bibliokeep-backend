package com.devsenior.cdiaz.bibliokeep.controller;

import com.devsenior.cdiaz.bibliokeep.model.dto.DashboardStatsDTO;
import com.devsenior.cdiaz.bibliokeep.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/dashboard")
    public DashboardStatsDTO getDashboardStats(@RequestHeader("user-id") String userIdHeader) {
        var userId = UUID.fromString(userIdHeader);
        return statsService.getDashboardStats(userId);
    }
}
