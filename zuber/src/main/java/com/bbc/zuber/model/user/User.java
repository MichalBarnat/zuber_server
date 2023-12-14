package com.bbc.zuber.model.user;

import com.bbc.zuber.model.user.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long id;
    private UUID uuid;
    private String name;
    private String surname;
    private LocalDate dob;
    private Sex sex;
    private String email;
    private BigDecimal balance;
}
