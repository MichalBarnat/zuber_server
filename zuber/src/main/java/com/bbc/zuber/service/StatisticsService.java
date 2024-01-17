package com.bbc.zuber.service;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.rideinfo.QRideInfo;
import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.model.statistics.DriverStatistics;
import com.bbc.zuber.repository.RideInfoRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final RideInfoRepository rideInfoRepository;
    private final DriverService driverService;
    private final JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public List<RideInfo> findRidesBetweenDates(LocalDateTime from, LocalDateTime to) {
        return rideInfoRepository.findByOrderTimeBetween(from, to);
    }

    @Transactional(readOnly = true)
    public DriverStatistics findTopByEarningsQueryDSL() {
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
                    .driver(driver)
                    .info(String.format("Earnings in total: %s",earnings))
                    .build();
        }
        return null;
    }

}
