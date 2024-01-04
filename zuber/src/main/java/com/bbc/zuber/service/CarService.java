package com.bbc.zuber.service;

import com.bbc.zuber.exceptions.CarNotFoundException;
import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public Car save(Car car) {
        return carRepository.save(car);
    }

    //Page zamiast List
    @Transactional(readOnly = true)
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(String.format("Car with id %d not found!", id)));
    }

    @Transactional(readOnly = true)
    public Car findByUuid(UUID uuid) {
        return carRepository.findByUuid(uuid).orElseThrow(() -> new CarNotFoundException(String.format("Car with uuid %s not found!", uuid)));
    }

    //todo CarService metody na:
    // auta mlodsze niz x lat
    // auta starsze niz x lat
    // auta o danym Typie
    // auta o danym typie silnika
    // auto najmlodszego/najstarszego kierowcy
    // auto kierowcy ktory najwiecej/najmniej zarobil

}
