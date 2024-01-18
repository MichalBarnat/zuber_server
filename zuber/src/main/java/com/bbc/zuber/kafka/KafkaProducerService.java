package com.bbc.zuber.kafka;

import com.bbc.zuber.exceptions.KafkaMessageProcessingException;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.fundsavailabilityresponse.FundsAvailabilityResponse;
import com.bbc.zuber.model.message.Message;
import com.bbc.zuber.model.message.dto.MessageDto;
import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.model.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
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

    public void sendMessageToDriver(Message message, Driver driver) {
        try {
            MessageDto dto = modelMapper.map(message, MessageDto.class);
            dto.setSenderName(driver.getName());
            dto.setSenderSurname(driver.getSurname());
            String messageToDriverJson = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("message-to-driver", messageToDriverJson);
        } catch (JsonProcessingException e) {
            throw new KafkaMessageProcessingException("Problem with sending to message-to-driver topic.");
        }
    }

    public void sendMessageToUser(Message message, User user) {
        try {
            MessageDto dto = modelMapper.map(message, MessageDto.class);
            dto.setSenderName(user.getName());
            dto.setSenderSurname(user.getSurname());
            String messageToUserJson = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("message-to-user", messageToUserJson);
        } catch (JsonProcessingException e) {
            throw new KafkaMessageProcessingException("Problem with sending to message-to-user topic.");
        }
    }
}
