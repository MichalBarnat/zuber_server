package com.bbc.zuber.model.ridecancel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideCancel {
    private Long id;
    private UUID rideAssignmentUuid;
}
