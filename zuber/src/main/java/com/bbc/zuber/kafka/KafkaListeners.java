package com.bbc.zuber.kafka;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus;
import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.model.riderequest.RideRequest;
import com.bbc.zuber.model.user.User;
import com.bbc.zuber.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final UserService userService;
    private final DriverService driverService;
    private final RideRequestService rideRequestService;
    private final RideAssignmentService rideAssignmentService;
    private final RideAssignmentResponseService rideAssignmentResponseService;
    private final RideInfoService rideInfoService;


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

    @KafkaListener(topics = "ride-request")
    void rideRequestListener(RideRequest rideRequest) {
        rideRequestService.save(rideRequest);
        System.out.println("Ride request successfully saved [from zuber_user]");
        Driver driver = driverService.getFirstAvailableDriver();
        System.out.println("Driver who will be ask for ride: " + driver);

        RideAssignment rideAssignment = RideAssignment.builder()
                .rideRequestUUID(rideRequest.getUuid())
                .driverUUID(driver.getUuid())
                .pickUpLocation(rideRequest.getPickUpLocation())
                .dropOffLocation(rideRequest.getDropOffLocation())
                .status(RideAssignmentStatus.PENDING)
                .build();

        rideAssignmentService.save(rideAssignment);

    }

    @KafkaListener(topics = "ride-assignment-response")
    void rideAssignmentResponseListener(RideAssignmentResponse rideAssignmentResponse) {
        rideAssignmentResponseService.save(rideAssignmentResponse);
        rideAssignmentService.updateStatus(rideAssignmentResponse.getId(), rideAssignmentResponse.getAccepted());
        System.out.println("Successfully updated RideAssignment status!");

        RideAssignment rideAssignment = rideAssignmentService.findById(rideAssignmentResponse.getId());
        RideRequest rideRequest = rideRequestService.findByUUID(rideAssignment.getRideRequestUUID());
        Driver driver = driverService.findByUUID(rideAssignment.getDriverUUID());

        RideInfo rideInfo = RideInfo.builder()
                .userUuid(rideRequest.getUserId())
                .driverUuid(rideAssignment.getDriverUUID())
                .driverName(driver.getName())
                .driverLocation("RADOM")
                .build();

        rideInfoService.save(rideInfo);
    }



    //TODO dodac LOGGERA

}
