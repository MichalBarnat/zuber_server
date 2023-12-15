package com.bbc.zuber.service;

import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.repository.RideInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RideInfoService {

    private final RideInfoRepository rideInfoRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Transactional
    public RideInfo save(RideInfo rideInfo) throws JsonProcessingException {
        RideInfo savedRideInfo = rideInfoRepository.save(rideInfo);
        String rideInfoJson = objectMapper.writeValueAsString(savedRideInfo);
        kafkaTemplate.send("ride-info", rideInfoJson);
        return savedRideInfo;
    }

}
