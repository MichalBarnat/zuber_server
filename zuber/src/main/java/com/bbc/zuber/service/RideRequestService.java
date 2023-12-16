package com.bbc.zuber.service;

import com.bbc.zuber.model.riderequest.RideRequest;
import com.bbc.zuber.repository.RideRequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideRequestService {

    private final RideRequestRepository rideRequestRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public RideRequest save(RideRequest rideRequest) {
        return rideRequestRepository.save(rideRequest);
    }

    @Transactional(readOnly = true)
    public RideRequest findById(Long id) {
        return rideRequestRepository.findById(id).orElseThrow();
    }

    //Page zamiast List
    @Transactional(readOnly = true)
    public List<RideRequest> findAll() {
        return rideRequestRepository.findAll();
    }

    // zamiast findAll w repo findByUuid
    @Transactional(readOnly = true)
    public RideRequest findByUUID(UUID uuid) {
        return findAll()
                .stream()
                .filter(request -> request.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow();
    }

}
