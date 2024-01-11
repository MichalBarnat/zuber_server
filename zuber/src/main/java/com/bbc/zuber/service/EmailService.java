package com.bbc.zuber.service;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.model.riderequest.RideRequest;
import com.bbc.zuber.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserService userService;
    private final DriverService driverService;
    private final RideAssignmentService rideAssignmentService;
    private final RideRequestService rideRequestService;

    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zuber_server@example.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendRideInfoWhenRideRequestAcceptedToUser(RideInfo rideInfo) {
        User user = userService.findByUuid(rideInfo.getUserUuid());
        String to = user.getEmail();
        String subject = "Ride confirmed";
        String text = "Hello " + user.getName() +
                "\n\nGreat news! Your ride request from: " + rideInfo.getPickUpLocation() + " to: " + rideInfo.getDropUpLocation() + " has been confirmed." +
                "\n\nDetails of your ride:" +
                "\nEstimated time of arrival: " + rideInfo.getEstimatedArrivalTime() +
                "\nPrice for the ride:" + rideInfo.getCostOfRide() +
                "\n\nYour driver " + rideInfo.getDriverName() + ", is on the way to pick you up. Please be ready at the pickup location." +
                "\n\nHave a safe and enjoyable ride!";
        sendMessage(to, subject, text);
    }

    public void sendRideInfoWhenRideRequestAcceptedToDriver(RideInfo rideInfo) {
        Driver driver = driverService.findByUUID(rideInfo.getDriverUuid());
        User user = userService.findByUuid(rideInfo.getUserUuid());
        String to = driver.getEmail();
        String subject = "Ride Confirmation";
        String text = "Hello " + rideInfo.getDriverName() +
                "\n\nThank you for accepting the ride. Below are the details for the trip:" +
                "\n- Pickup location: " + rideInfo.getPickUpLocation() +
                "\n- Drop-off location: " + rideInfo.getDropUpLocation() +
                "\n- Estimated time of arrival: " + rideInfo.getEstimatedArrivalTime() +
                "\n- Ride distance: " + rideInfo.getRideLengthInKilometers() + " km" +
                "\n- Ride cost:" + rideInfo.getCostOfRide() +
                "\n\nAdditional Information:" +
                "\n- Passenger's name: " + user.getName() +
                "\n- Passenger's contact: " + user.getEmail() +
                "\n\nThank you for using our service. Have a safe trip!";
        sendMessage(to, subject, text);
    }

    public void sendRideInfoWhenRideRequestRejectedToUser(RideAssignmentResponse response) {
        RideAssignment rideAssignment = rideAssignmentService.findById(response.getRideAssignmentId());
        RideRequest request = rideRequestService.findByUUID(rideAssignment.getRideRequestUUID());
        User user = userService.findByUuid(request.getUserUuid());
        String to = user.getEmail();
        String subject = "Ride rejected";
        String text = "Hello " + user.getName() +
                "\n\nYour ride request from: " + request.getPickUpLocation() + " to: " + request.getDropOffLocation() + " has been rejected." +
                "\n\nPlease try again!";
        sendMessage(to, subject, text);
    }

    public void sendRideInfoWhenRideRequestRejectedToDriver(RideAssignmentResponse response) {
        RideAssignment rideAssignment = rideAssignmentService.findById(response.getRideAssignmentId());
        RideRequest request = rideRequestService.findByUUID(rideAssignment.getRideRequestUUID());
        Driver driver = driverService.findByUUID(rideAssignment.getDriverUUID());
        String to = driver.getEmail();
        String subject = "Ride request rejected";
        String text = "Hello " + driver.getName() +
                "\n\nYour ride request has been successfully rejected. The details are as follows:" +
                "\n\n- Pickup location: " + request.getPickUpLocation() +
                "\n- Drop-off location: " + request.getDropOffLocation() +
                "\n\nThank you for using Zuber";
        sendMessage(to, subject, text);
    }
}
