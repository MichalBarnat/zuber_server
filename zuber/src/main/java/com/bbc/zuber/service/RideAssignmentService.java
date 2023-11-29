package com.bbc.zuber.service;

import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.repository.RideAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideAssignmentService {

    private final RideAssignmentRepository rideAssignmentRepository;
    private final KafkaTemplate<String, RideAssignment> rideAssignmentKafkaTemplate;

    public RideAssignment save(RideAssignment rideAssignment) {
        rideAssignmentKafkaTemplate.send("ride-assignment", rideAssignment);
        return rideAssignmentRepository.save(rideAssignment);
    }

}
