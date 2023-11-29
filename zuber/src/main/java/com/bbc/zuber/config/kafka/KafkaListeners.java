package com.bbc.zuber.config.kafka;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus;
import com.bbc.zuber.model.riderequest.RideRequest;
import com.bbc.zuber.model.user.User;
import com.bbc.zuber.service.DriverService;
import com.bbc.zuber.service.RideAssignmentService;
import com.bbc.zuber.service.RideRequestService;
import com.bbc.zuber.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final UserService userService;
    private final DriverService driverService;
    private final RideRequestService rideRequestService;
    private final RideAssignmentService rideAssignmentService;

    @KafkaListener(topics = "user-registration")
    void userRegistrationListener(User user) {
        userService.save(user);
        System.out.println("succesfully saved user from zuber_user");
    }

    @KafkaListener(topics = "driver-registration")
    void driverRegistrationListener(Driver driver) {
        driverService.save(driver);
        System.out.println("Succesfully saved driver from zuber_driver");
    }

    @KafkaListener(topics = "riderequest")
    void rideRequestListener(RideRequest rideRequest) {
        rideRequestService.save(rideRequest);
        System.out.println("Ride request successfully saved [from zuber_user]");
        Driver driver = driverService.getFirstAvailableDriver();
        System.out.println("Driver who will be ask for ride: " + driver);
        RideAssignment rideAssignment = RideAssignment.builder()
                .rideRequestUUID(rideRequest.getUuid())
                .driverUUID(driver.getUuid())
                .status(RideAssignmentStatus.PENDING)
                .build();
        rideAssignmentService.save(rideAssignment);

    }

    //TODO dodac LOGGERA

}
