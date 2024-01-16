package com.bbc.zuber.model.rideinfo.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class RideInfoDto {
    Long id;
    UUID rideAssignmentUuid;
    UUID userUuid;
    UUID driverUuid;
    String driverName;
    String driverLocation;
    String pickUpLocation;
    String dropUpLocation;
    LocalDateTime orderTime;
    LocalDateTime estimatedArrivalTime;
    BigDecimal costOfRide;
    String timeToArrivalInMinutes;
    String rideLengthInKilometers;
}
