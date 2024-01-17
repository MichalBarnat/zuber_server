package com.bbc.zuber.model.driver;


import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.driver.enums.Sex;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "drivers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    private Long id;
    private UUID uuid;
    private String name;
    private String surname;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private StatusDriver statusDriver;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private String email;
    private String location;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CAR_UUID", referencedColumnName = "uuid")
    @JsonManagedReference
    private Car car;

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", statusDriver=" + statusDriver +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", car=" + (car != null ? car.getUuid() : null) +
                '}';
    }
}