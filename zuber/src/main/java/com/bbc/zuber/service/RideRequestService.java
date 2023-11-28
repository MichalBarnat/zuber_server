package com.bbc.zuber.service;

import com.bbc.zuber.model.riderequest.RideRequest;
import com.bbc.zuber.repository.RideRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    public RideRequest save(RideRequest rideRequest) {
        return rideRequestRepository.save(rideRequest);
    }

}
