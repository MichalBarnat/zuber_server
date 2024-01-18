package com.bbc.zuber.controller;

import com.bbc.zuber.statistics.model.DriverStatistics;
import com.bbc.zuber.statistics.model.user.UserStatistics;
import com.bbc.zuber.statistics.model.user.UsersWaitingTimeStatistics;
import com.bbc.zuber.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService service;

    @GetMapping("/topDriverByEarnings")
    public ResponseEntity<DriverStatistics> topDriverByEarnings() {
        return ResponseEntity.ok(service.findTopDriverByEarnings());
    }

    @GetMapping("/driverWithMostRides")
    public ResponseEntity<DriverStatistics> driverWithMostRides() {
        return ResponseEntity.ok(service.findDriverWithMostRides());
    }

    @GetMapping("/topUserBySpending")
    public ResponseEntity<UserStatistics> topUserBySpending() {
        return ResponseEntity.ok(service.findTopUserBySpending());
    }

    @GetMapping("/userWithMostRides")
    public ResponseEntity<UserStatistics> userWithMostRides() {
        return ResponseEntity.ok(service.findUserWithMostRides());
    }

    @GetMapping("/averageWaitingTimeByUsers")
    public ResponseEntity<UsersWaitingTimeStatistics> averageWaitingTimeByUsers() {
        return ResponseEntity.ok(service.findAverageWaitingTimeForAllRides());
    }

}
