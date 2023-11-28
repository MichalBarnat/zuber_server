package com.bbc.zuber.service;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

}
