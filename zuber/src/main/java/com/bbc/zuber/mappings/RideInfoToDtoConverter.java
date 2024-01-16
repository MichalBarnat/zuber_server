package com.bbc.zuber.mappings;

import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.model.rideinfo.dto.RideInfoDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class RideInfoToDtoConverter implements Converter<RideInfo, RideInfoDto> {
    @Override
    public RideInfoDto convert(MappingContext<RideInfo, RideInfoDto> mappingContext) {
        RideInfo rideInfo = mappingContext.getSource();
        return RideInfoDto.builder()
                .id(rideInfo.getId())
                .rideAssignmentUuid(rideInfo.getRideAssignmentUuid())
                .userUuid(rideInfo.getUserUuid())
                .driverUuid(rideInfo.getDriverUuid())
                .driverName(rideInfo.getDriverName())
                .driverLocation(rideInfo.getDriverLocation())
                .pickUpLocation(rideInfo.getPickUpLocation())
                .dropUpLocation(rideInfo.getDropUpLocation())
                .orderTime(rideInfo.getOrderTime())
                .estimatedArrivalTime(rideInfo.getEstimatedArrivalTime())
                .costOfRide(rideInfo.getCostOfRide())
                .timeToArrivalInMinutes(rideInfo.getTimeToArrivalInMinutes())
                .rideLengthInKilometers(rideInfo.getRideLengthInKilometers())
                .build();
    }
}
