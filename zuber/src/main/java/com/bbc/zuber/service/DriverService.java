package com.bbc.zuber.service;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import com.bbc.zuber.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    public List<Driver> availableDrivers() {
        return driverRepository.findAll().stream()
                .filter(driver -> driver.getStatusDriver() == StatusDriver.AVAILABLE)
                .toList();
    }

    public Driver getFirstAvailableDriver() {
        Driver driver;
        if(!availableDrivers().isEmpty()) {
            driver = availableDrivers().get(0);
        } else {
            throw new IllegalArgumentException("No drivers available!");
        }
        return driver;
    }

}
