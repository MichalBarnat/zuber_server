package com.bbc.zuber.model.riderequest;

import com.bbc.zuber.model.riderequest.enums.Size;
import com.bbc.zuber.model.riderequest.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "rideRequests")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    @Id
    private Long id;
    private UUID uuid;
    private UUID userId;
    private String pickUpLocation;
    private String dropOffLocation;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private Size size;
    private String date;

    //TODO dodac status: w trakcie, zakonczone, odrzucone
}