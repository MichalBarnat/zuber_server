package com.bbc.zuber.repository;

import com.bbc.zuber.model.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByUuid(UUID uuid);
}
