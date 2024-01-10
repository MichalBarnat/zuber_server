package com.bbc.zuber.service;

import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideCancelService {
    private final Logger logger = LoggerFactory.getLogger(RideCancelService.class);
    private final RideAssignmentService rideAssignmentService;

    public void cancelRideAssignment(UUID rideCancelUUID) throws JsonProcessingException {

        if(rideAssignmentService.existsByUuid(rideCancelUUID)) {
            RideAssignment rideAssignment = rideAssignmentService.findByUuid(rideCancelUUID);

            if(rideAssignment.getStatus() == RideAssignmentStatus.PENDING) {
                rideAssignment.setStatus(RideAssignmentStatus.CANCELLED);
                rideAssignmentService.save(rideAssignment);
                logger.info("Successfully cancelled RideAssignment with uuid: {}", rideCancelUUID);
            } else if(rideAssignment.getStatus() == RideAssignmentStatus.ACCEPTED) {
                logger.info("Cannot cancel because this ride is ACCEPTED already! UUID: {}", rideCancelUUID);
            }

        } else {
            logger.info("Cannot cancel! RideAssignment with uuid {} don't exist at all!", rideCancelUUID);
        }
    }

    //todo czy dawac info do usera czy pomyslnie cancellowano przejazd?

}
