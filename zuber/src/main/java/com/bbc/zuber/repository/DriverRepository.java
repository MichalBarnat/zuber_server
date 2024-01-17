package com.bbc.zuber.repository;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByStatusDriver(StatusDriver statusDriver);
    Optional<Driver> findByUuid(UUID uuid);
}
