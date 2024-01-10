package com.bbc.zuber.repository;

import com.bbc.zuber.model.rideassignment.RideAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RideAssignmentRepository extends JpaRepository<RideAssignment, Long> {
    Boolean existsByUuid(UUID uuid);
    Optional<RideAssignment> findByUuid(UUID uuid);
}
