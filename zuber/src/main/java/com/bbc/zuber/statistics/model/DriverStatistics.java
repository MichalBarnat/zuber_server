package com.bbc.zuber.statistics.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DriverStatistics {
    private String info;
    private UUID driverUuid;
}
