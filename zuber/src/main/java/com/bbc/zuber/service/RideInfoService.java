package com.bbc.zuber.service;

import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.repository.RideInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideInfoService {

    private final RideInfoRepository rideInfoRepository;
    private final KafkaTemplate<String, RideInfo> rideInfoKafkaTemplate;

    public RideInfo save(RideInfo rideInfo) {
        RideInfo savedRideInfo = rideInfoRepository.save(rideInfo);
        rideInfoKafkaTemplate.send("ride-info", savedRideInfo);
        return savedRideInfo;
    }

}
