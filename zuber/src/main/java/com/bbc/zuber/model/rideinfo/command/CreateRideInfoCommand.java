package com.bbc.zuber.model.rideinfo.command;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRideInfoCommand {
    @Min(value = 0, message = "PAGE_NOT_NEGATIVE")
    private int pageNumber = 0;
    @Min(value = 1, message = "PAGE_SIZE_NOT_LESS_THAN_ONE")
    private int pageSize = 5;
    private String sortDirection = "ASC";
    @Pattern(regexp = "id|rideAssignmentUuid|userUuid|driverUuid|driverName|driverLocation|pickUpLocation|dropUpLocation|orderTime|estimatedArrivalTime|costOfRide|timeToArrivalInMinutes|rideLengthInKilometers", message = "INVALID_SORT_BY_VALUE")
    private String sortBy = "id";
}
