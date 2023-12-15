package com.bbc.zuber.service;

import com.bbc.zuber.exceptions.DriverNotFoundException;
import com.bbc.zuber.exceptions.DriversNotAvailableException;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import com.bbc.zuber.model.googledistancematrix.DistanceMatrixResponse;
import com.bbc.zuber.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bbc.zuber.model.googledistancematrix.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final GoogleDistanceMatrixService googleDistanceMatrixService;

    @Transactional
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    //Page zamiast List
    @Transactional(readOnly = true)
    public List<Driver> availableDrivers() {
        return driverRepository.findAllByStatusDriver(StatusDriver.AVAILABLE);
    }

    //Page zamiast List
    @Transactional(readOnly = true)
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    // zamiast findAll w repo findByUuid
    public Driver findByUUID(UUID uuid) {
        return findAll()
                .stream()
                .filter(driver -> driver.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new DriverNotFoundException(String.format("Driver with uuid: %s not found!", uuid)));
    }

    public Driver getNearestAvailableDriver(String pickUpLocation) {
        List<Driver> availableDrivers = availableDrivers();
        if (availableDrivers.isEmpty()) {
            throw new DriversNotAvailableException("No drivers are available! Sorry!");
        }

        Driver nearestDriver = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (Driver driver : availableDrivers) {
            String driverLocation = driver.getLocation();
            DistanceMatrixResponse response = googleDistanceMatrixService.getDistance(driverLocation, pickUpLocation);

            if ("OK".equals(response.getStatus())) {
                List<DistanceMatrixRow> rows = response.getRows();
                if (!rows.isEmpty()) {
                    List<DistanceMatrixElement> elements = rows.get(0).getElements();
                    if (!elements.isEmpty()) {
                        DistanceMatrixDistance distance = elements.get(0).getDistance();
                        if (distance != null) {
                            //String distanceText = distance.getText();
                            int distanceValue = distance.getValue();

                            if (distanceValue < shortestDistance) {
                                shortestDistance = distanceValue;
                                nearestDriver = driver;
                            }
                        }
                    }
                }
            }
        }

        if (nearestDriver == null) {
            throw new DriversNotAvailableException("No drivers are available! Sorry!");
        }

        return nearestDriver;
    }

}
