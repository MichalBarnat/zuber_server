package com.bbc.zuber.service;

import com.bbc.zuber.exceptions.RideInfoNotFoundException;
import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.repository.RideInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideInfoService {
    private final RideInfoRepository rideInfoRepository;

    @Transactional
    public RideInfo save(RideInfo rideInfo) {
        return rideInfoRepository.save(rideInfo);
    }

    @Transactional(readOnly = true)
    public Page<RideInfo> findAll(Pageable pageable) {
        return rideInfoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<RideInfo> findByOrderTimeBetween(LocalDateTime from, LocalDateTime to) {
        return rideInfoRepository.findByOrderTimeBetween(from, to);
    }

    @Transactional(readOnly = true)
    public RideInfo findByDriverUuid(UUID driverUuid) {
        return rideInfoRepository.findByDriverUuid(driverUuid)
                .orElseThrow(() -> new RideInfoNotFoundException("Ride info with driver uuid: " + driverUuid + " not found!"));
    }
}
