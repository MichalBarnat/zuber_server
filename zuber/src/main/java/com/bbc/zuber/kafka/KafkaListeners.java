package com.bbc.zuber.kafka;

import com.bbc.zuber.exceptions.KafkaMessageProcessingException;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus;
import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.model.riderequest.RideRequest;
import com.bbc.zuber.model.user.User;
import com.bbc.zuber.service.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final UserService userService;
    private final DriverService driverService;
    private final RideRequestService rideRequestService;
    private final RideAssignmentService rideAssignmentService;
    private final RideAssignmentResponseService rideAssignmentResponseService;
    private final RideInfoService rideInfoService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user-registration")
    void userRegistrationListener(String userJson) {
        try {
            User user = objectMapper.readValue(userJson, User.class);
            userService.save(user);
            System.out.println("successfully saved user from zuber_user");
        } catch (IOException e) {
            throw new KafkaMessageProcessingException("Problem with save user from zuber_user");
        }
    }

    @KafkaListener(topics = "driver-registration")
    void driverRegistrationListener(String driverJson) {
        try {
            Driver driver = objectMapper.readValue(driverJson, Driver.class);
            driverService.save(driver);
            System.out.println("Successfully saved driver from zuber_driver");
        } catch (IOException e) {
            throw new KafkaMessageProcessingException("Problem with save driver from zuber_driver");
        }
    }

    @KafkaListener(topics = "ride-request")
    void rideRequestListener(String rideRequestJson) {
        try {
            RideRequest rideRequest = objectMapper.readValue(rideRequestJson, RideRequest.class);
            rideRequestService.save(rideRequest);
            System.out.println("Ride request successfully saved [from zuber_user]");
            JsonNode jsonNode = objectMapper.readTree(rideRequestJson);
            String pickUpLocationFromJson = jsonNode.get("pickUpLocation").asText();
            Driver driver = driverService.getNearestAvailableDriver(pickUpLocationFromJson);
            System.out.println("Driver who will be asked for a ride: " + driver);

            RideAssignment rideAssignment = RideAssignment.builder()
                    .rideRequestUUID(rideRequest.getUuid())
                    .driverUUID(driver.getUuid())
                    .pickUpLocation(rideRequest.getPickUpLocation())
                    .dropOffLocation(rideRequest.getDropOffLocation())
                    .status(RideAssignmentStatus.PENDING)
                    .build();

            rideAssignmentService.save(rideAssignment);
        } catch (IOException e) {
            throw new KafkaMessageProcessingException("Problem with save ride-request from zuber_user");
        }
    }

    @KafkaListener(topics = "ride-assignment-response")
    void rideAssignmentResponseListener(String rideAssignmentResponseJson) {
        try {
            RideAssignmentResponse rideAssignmentResponse = objectMapper.readValue(rideAssignmentResponseJson, RideAssignmentResponse.class);
            rideAssignmentService.updateStatus(rideAssignmentResponse.getId(), rideAssignmentResponse.getAccepted());
            System.out.println("Successfully updated RideAssignment status!");

            RideAssignment rideAssignment = rideAssignmentService.findById(rideAssignmentResponse.getId());
            RideRequest rideRequest = rideRequestService.findByUUID(rideAssignment.getRideRequestUUID());
            Driver driver = driverService.findByUUID(rideAssignment.getDriverUUID());

            RideInfo rideInfo = RideInfo.builder()
                    .userUuid(rideRequest.getUserId())
                    .driverUuid(rideAssignment.getDriverUUID())
                    .driverName(driver.getName())
                    .driverLocation(driver.getLocation())
                    .build();

            rideInfoService.save(rideInfo);
        } catch (IOException e) {
            throw new KafkaMessageProcessingException("Problem with save ride-request from zuber_driver");
        }
    }


//    @KafkaListener(topics = "user-registration")
//    void userRegistrationListener(User user) {
//        userService.save(user);
//        System.out.println("succesfully saved user from zuber_user");
//    }
//
//    @KafkaListener(topics = "driver-registration")
//    void driverRegistrationListener(Driver driver) {
//        driverService.save(driver);
//        System.out.println("Succesfully saved driver from zuber_driver");
//    }
//
//    @KafkaListener(topics = "ride-request")
//    void rideRequestListener(RideRequest rideRequest) {
//        rideRequestService.save(rideRequest);
//        System.out.println("Ride request successfully saved [from zuber_user]");
//        Driver driver = driverService.getFirstAvailableDriver();
//        System.out.println("Driver who will be ask for ride: " + driver);
//
//        RideAssignment rideAssignment = RideAssignment.builder()
//                .rideRequestUUID(rideRequest.getUuid())
//                .driverUUID(driver.getUuid())
//                .pickUpLocation(rideRequest.getPickUpLocation())
//                .dropOffLocation(rideRequest.getDropOffLocation())
//                .status(RideAssignmentStatus.PENDING)
//                .build();
//
//        rideAssignmentService.save(rideAssignment);
//
//    }
//
//    @KafkaListener(topics = "ride-assignment-response")
//    void rideAssignmentResponseListener(RideAssignmentResponse rideAssignmentResponse) {
//        rideAssignmentResponseService.save(rideAssignmentResponse);
//        rideAssignmentService.updateStatus(rideAssignmentResponse.getId(), rideAssignmentResponse.getAccepted());
//        System.out.println("Successfully updated RideAssignment status!");
//
//        RideAssignment rideAssignment = rideAssignmentService.findById(rideAssignmentResponse.getId());
//        RideRequest rideRequest = rideRequestService.findByUUID(rideAssignment.getRideRequestUUID());
//        Driver driver = driverService.findByUUID(rideAssignment.getDriverUUID());
//
//        RideInfo rideInfo = RideInfo.builder()
//                .userUuid(rideRequest.getUserId())
//                .driverUuid(rideAssignment.getDriverUUID())
//                .driverName(driver.getName())
//                .driverLocation("RADOM")
//                .build();
//
//        rideInfoService.save(rideInfo);
//    }


    //TODO dodac LOGGERA

}
