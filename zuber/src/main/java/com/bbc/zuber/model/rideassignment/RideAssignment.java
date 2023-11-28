package com.bbc.zuber.model.rideassignment;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "rideAssignments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private UUID rideRequestUUID;
    private UUID driverUUID;
    private String assignmentTime;
}
