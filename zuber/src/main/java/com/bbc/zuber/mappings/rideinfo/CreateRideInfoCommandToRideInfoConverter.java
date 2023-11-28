package com.bbc.zuber.mappings.rideinfo;

import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.model.rideinfo.commands.CreateRideInfoCommand;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateRideInfoCommandToRideInfoConverter implements Converter<CreateRideInfoCommand, RideInfo> {

    @Override
    public RideInfo convert(MappingContext<CreateRideInfoCommand, RideInfo> mappingContext) {
        CreateRideInfoCommand command = mappingContext.getSource();

        return RideInfo.builder()
                .uuid(UUID.randomUUID())
                .driverName(command.getDriverName())
                .driverCar(command.getDriverCar())
                .plateNumber(command.getPlateNumber())
                .latitudeGeoDriver(command.getLatitudeGeoDriver())
                .longitudeGeoDriver(command.getLongitudeGeoDriver())
                .build();
    }
}
