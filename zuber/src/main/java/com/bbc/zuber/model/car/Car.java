package com.bbc.zuber.model.car;

import com.bbc.zuber.model.car.enums.TypeOfCar;
import com.bbc.zuber.model.car.enums.TypeOfEngine;
import com.bbc.zuber.model.driver.Driver;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    @Id
    private Long id;
    private UUID uuid;
    private String brand;
    private String model;
    private int productionYear;
    @Enumerated(EnumType.STRING)
    private TypeOfEngine engine;
    @Enumerated(EnumType.STRING)
    private TypeOfCar type;
    private int size;
    private String plateNum;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DRIVER_UUID", referencedColumnName = "uuid")
    @JsonBackReference
    private Driver driver;
}