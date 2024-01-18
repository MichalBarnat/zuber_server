package com.bbc.zuber.statistics.service;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.rideinfo.QRideInfo;
import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.model.user.User;
import com.bbc.zuber.service.DriverService;
import com.bbc.zuber.service.RideInfoService;
import com.bbc.zuber.service.UserService;
import com.bbc.zuber.statistics.model.DriverStatistics;
import com.bbc.zuber.statistics.model.user.UserStatistics;
import com.bbc.zuber.statistics.model.user.UsersWaitingTimeStatistics;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final JPAQueryFactory queryFactory;
    private final RideInfoService rideInfoService;
    private final DriverService driverService;
    private final UserService userService;


    @Transactional(readOnly = true)
    public List<RideInfo> findRidesBetweenDates(LocalDateTime from, LocalDateTime to) {
        return rideInfoService.findByOrderTimeBetween(from, to);
    }

    //DRIVER STATISTICS

    @Transactional(readOnly = true)
    public DriverStatistics findTopDriverByEarnings() {
        QRideInfo rideInfo = QRideInfo.rideInfo;
        Tuple result = queryFactory
                .select(rideInfo.driverUuid, rideInfo.costOfRide.sum())
                .from(rideInfo)
                .groupBy(rideInfo.driverUuid)
                .orderBy(rideInfo.costOfRide.sum().desc())
                .fetchFirst();

        if (result != null) {
            UUID driverUuid = result.get(rideInfo.driverUuid);
            BigDecimal earnings = result.get(rideInfo.costOfRide.sum());
            Driver driver = driverService.findByUUID(driverUuid);
            return DriverStatistics.builder()
                    .info(String.format("%s %s earned a total of: %s", driver.getName(), driver.getSurname(), earnings))
                    .driverUuid(driverUuid)
                    .build();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public DriverStatistics findDriverWithMostRides() {
        QRideInfo rideInfo = QRideInfo.rideInfo;
        Tuple result = queryFactory
                .select(rideInfo.driverUuid, rideInfo.driverUuid.count())
                .from(rideInfo)
                .groupBy(rideInfo.driverUuid)
                .orderBy(rideInfo.driverUuid.count().desc())
                .fetchFirst();

        if (result != null) {
            UUID driverUuid = result.get(rideInfo.driverUuid);
            long rideCount = result.get(rideInfo.driverUuid.count());
            Driver driver = driverService.findByUUID(driverUuid);
            return DriverStatistics.builder()
                    .info(String.format("%s %s have the most rides with count: %d", driver.getName(), driver.getSurname(), rideCount))
                    .driverUuid(driverUuid)
                    .build();
        }
        return null;
    }

    // USER STATISTICS

    @Transactional(readOnly = true)
    public UserStatistics findTopUserBySpending() {
        QRideInfo rideInfo = QRideInfo.rideInfo;
        Tuple result = queryFactory
                .select(rideInfo.userUuid, rideInfo.costOfRide.sum())
                .from(rideInfo)
                .groupBy(rideInfo.userUuid)
                .orderBy(rideInfo.costOfRide.sum().desc())
                .fetchFirst();

        if (result != null) {
            UUID userUuid = result.get(rideInfo.userUuid);
            BigDecimal totalSpending = result.get(rideInfo.costOfRide.sum());
            User user = userService.findByUuid(userUuid);
            return UserStatistics.builder()
                    .info(String.format("%s %s spent a total of: %s", user.getName(), user.getSurname(), totalSpending))
                    .userUuid(userUuid)
                    .build();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public UserStatistics findUserWithMostRides() {
        QRideInfo rideInfo = QRideInfo.rideInfo;
        Tuple result = queryFactory
                .select(rideInfo.userUuid, rideInfo.userUuid.count())
                .from(rideInfo)
                .groupBy(rideInfo.userUuid)
                .orderBy(rideInfo.userUuid.count().desc())
                .fetchFirst();

        if (result != null) {
            UUID userUuid = result.get(rideInfo.userUuid);
            long rideCount = result.get(rideInfo.userUuid.count());
            User user = userService.findByUuid(userUuid);
            return UserStatistics.builder()
                    .info(String.format("%s %s have the most rides with count: %d", user.getName(), user.getSurname(), rideCount))
                    .userUuid(userUuid)
                    .build();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public UsersWaitingTimeStatistics findAverageWaitingTimeForAllRides() {
        QRideInfo rideInfo = QRideInfo.rideInfo;
        List<String> timeToArrivalList = queryFactory
                .select(rideInfo.timeToArrivalInMinutes)
                .from(rideInfo)
                .fetch();

        if (timeToArrivalList.isEmpty()) {
            return null;
        }

        double totalMinutes = 0;
        int count = 0;
        for (String timeToArrivalStr : timeToArrivalList) {
            if (timeToArrivalStr != null && timeToArrivalStr.endsWith(" mins")) {
                try {
                    int timeToArrival = Integer.parseInt(timeToArrivalStr.replace(" mins", "").trim());
                    totalMinutes += timeToArrival;
                    count++;
                } catch (NumberFormatException e) {
                    // Logowanie błędu lub obsługa błędu, jeśli format jest niewłaściwy
                }
            }
        }

        if (count > 0) {
            double averageTimeToArrival = totalMinutes / count;
            return UsersWaitingTimeStatistics.builder()
                    .averageWaitingTime(averageTimeToArrival)
                    .info(String.format("Średni czas oczekiwania na przejazd wynosi: %.1f minut", averageTimeToArrival))
                    .build();
        } else {
            return null;
        }
    }

}
