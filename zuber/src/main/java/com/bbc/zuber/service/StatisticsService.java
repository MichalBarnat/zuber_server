package com.bbc.zuber.service;

import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.repository.RideInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final RideInfoRepository rideInfoRepository;

    @Transactional(readOnly = true)
    public List<RideInfo> findRidesBetweenDates(LocalDateTime from, LocalDateTime to) {
        return rideInfoRepository.findByOrderTimeBetween(from, to);
    }

}
