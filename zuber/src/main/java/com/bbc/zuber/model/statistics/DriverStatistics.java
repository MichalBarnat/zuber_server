package com.bbc.zuber.model.statistics;

import com.bbc.zuber.model.driver.Driver;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverStatistics {
    private String info;
    private Driver driver;
}
