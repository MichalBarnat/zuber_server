package com.bbc.zuber.repository;

import com.bbc.zuber.model.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
