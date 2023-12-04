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
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RideInfo save(RideInfo rideInfo) {
        RideInfo savedRideInfo = rideInfoRepository.save(rideInfo);
        kafkaTemplate.send("ride-info", savedRideInfo);
        return savedRideInfo;
    }

}
