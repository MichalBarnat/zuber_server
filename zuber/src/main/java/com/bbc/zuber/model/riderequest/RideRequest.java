package com.bbc.zuber.model.riderequest;

import com.bbc.zuber.model.riderequest.enums.RideRequestSize;
import com.bbc.zuber.model.riderequest.enums.RideRequestType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "rideRequests")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    @Id
    private Long id;
    private UUID uuid;
    private UUID userUuid;
    private String pickUpLocation;
    private String dropOffLocation;
    @Enumerated(EnumType.STRING)
    private RideRequestType type;
    @Enumerated(EnumType.STRING)
    private RideRequestSize size;
    private LocalDate date;
}