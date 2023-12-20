package com.bbc.zuber.kafka;

import com.bbc.zuber.exceptions.KafkaMessageProcessingException;
import com.bbc.zuber.model.fundsavailabilityresponse.FundsAvailabilityResponse;
import com.bbc.zuber.model.rideinfo.RideInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendRideInfo(RideInfo rideInfo) {
        try {
            String rideInfoJson = objectMapper.writeValueAsString(rideInfo);
            kafkaTemplate.send("ride-info", rideInfoJson);
        } catch (JsonProcessingException e) {
            throw new KafkaMessageProcessingException("Problem with sending to ride-info topic.");
        }
    }

    public void sendFundsAvailabilityResponse(FundsAvailabilityResponse fundsAvailabilityResponse) {
        try {
            String fundsAvailabilityResponseJson = objectMapper.writeValueAsString(fundsAvailabilityResponse);
            kafkaTemplate.send("funds-availability-response", fundsAvailabilityResponseJson);
        } catch (JsonProcessingException e) {
            throw new KafkaMessageProcessingException("Problem with sending to funds-availability-response topic.");
        }
    }

}
