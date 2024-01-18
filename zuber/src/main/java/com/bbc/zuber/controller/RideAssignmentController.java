package com.bbc.zuber.controller;

import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.service.RideAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/ride-assignment")
@RequiredArgsConstructor
public class RideAssignmentController {

    private final RideAssignmentService rideAssignmentService;

    @GetMapping("/{uuid}")
    public ResponseEntity<RideAssignment> findByUuid(@PathVariable UUID uuid) {
        RideAssignment rideAssignment = rideAssignmentService.findByUuid(uuid);
        return new ResponseEntity<>(rideAssignment, OK);
    }
}
