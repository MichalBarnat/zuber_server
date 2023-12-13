package com.bbc.zuber.service;

import com.bbc.zuber.exceptions.RideAssignmentNotFoundException;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus;
import com.bbc.zuber.repository.RideAssignmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideAssignmentService {

    private final RideAssignmentRepository rideAssignmentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public RideAssignment save(RideAssignment rideAssignment) throws JsonProcessingException {
        RideAssignment savedAssignment = rideAssignmentRepository.save(rideAssignment);
        String rideAssignmentJson = objectMapper.writeValueAsString(savedAssignment);
        kafkaTemplate.send("ride-assignment", rideAssignmentJson);
        return savedAssignment;
    }

    public RideAssignment findById(Long id) {
        return rideAssignmentRepository.findById(id).orElseThrow(
                () -> new RideAssignmentNotFoundException(String.format("RideAssignment with id %d not found!", id)));
    }

    public RideAssignment updateStatus(Long id, boolean accepted) throws JsonProcessingException {
        RideAssignment rideAssignment = findById(id);
        if (accepted) {
            rideAssignment.setStatus(RideAssignmentStatus.ACCEPTED);
        } else {
            rideAssignment.setStatus(RideAssignmentStatus.REJECTED);
        }
        return save(rideAssignment);
    }
}
