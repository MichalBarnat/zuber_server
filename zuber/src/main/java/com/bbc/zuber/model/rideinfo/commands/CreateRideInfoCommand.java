package com.bbc.zuber.model.rideinfo.commands;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRideInfoCommand {
    String driverName;
    String driverCar;
    String plateNumber;
    String latitudeGeoDriver;
    String longitudeGeoDriver;
}
