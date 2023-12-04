package com.bbc.zuber.service;

import com.bbc.zuber.model.riderequest.RideRequest;
import com.bbc.zuber.repository.RideRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideRequestService {

    private final RideRequestRepository rideRequestRepository;

    public RideRequest save(RideRequest rideRequest) {
        return rideRequestRepository.save(rideRequest);
    }

    public RideRequest findById(Long id) {
        return rideRequestRepository.findById(id).orElseThrow();
    }

    public List<RideRequest> findAll() {
        return rideRequestRepository.findAll();
    }

    public RideRequest findByUUID(UUID uuid) {
        return findAll()
                .stream()
                .filter(request -> request.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow();
    }
}
