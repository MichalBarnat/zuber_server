package com.bbc.zuber.repository;

import com.bbc.zuber.model.riderequest.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
}
