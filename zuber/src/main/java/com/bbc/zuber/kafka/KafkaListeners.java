package com.bbc.zuber.kafka;

import com.bbc.zuber.exceptions.KafkaMessageProcessingException;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.fundsavailabilityresponse.FundsAvailabilityResponse;
import com.bbc.zuber.model.message.Message;
import com.bbc.zuber.model.message.command.CreateMessageCommand;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus;
import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.model.ridecancel.RideCancel;
import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.model.riderequest.RideRequest;
import com.bbc.zuber.model.user.User;
import com.bbc.zuber.service.DriverService;
import com.bbc.zuber.service.EmailService;
import com.bbc.zuber.service.GoogleDistanceMatrixService;
import com.bbc.zuber.service.MessageService;
import com.bbc.zuber.service.RideAssignmentService;
import com.bbc.zuber.service.RideCancelService;
import com.bbc.zuber.service.RideInfoService;
import com.bbc.zuber.service.RideRequestService;
import com.bbc.zuber.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaListeners {
    private final Logger logger = LoggerFactory.getLogger(KafkaListeners.class);

    private final UserService userService;
    private final DriverService driverService;
    private final RideRequestService rideRequestService;
    private final RideAssignmentService rideAssignmentService;
    private final RideInfoService rideInfoService;
    private final GoogleDistanceMatrixService googleDistanceMatrixService;
    private final RideCancelService rideCancelService;
    private final KafkaProducerService kafkaProducerService;
    private final MessageService messageService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user-registration")
    void userRegistrationListener(String userJson) {
        try {
            User user = objectMapper.readValue(userJson, User.class);
            userService.save(user);
            logger.info("Successfully saved user from zuber_user");
        } catch (IOException e) {
            throw new KafkaMessageProcessingException("Problem with save user from zuber_user");
        }
    }

    @KafkaListener(topics = "driver-registration")
    void driverRegistrationListener(String driverJson) {
        try {
            Driver driver = objectMapper.readValue(driverJson, Driver.class);
            if (driver.getCar() != null) {
                driver.getCar().setDriver(driver);
            }
            driverService.save(driver);
            logger.info("Successfully saved driver from zuber_driver");
        } catch (IOException e) {
            throw new KafkaMessageProcessingException("Problem with save driver from zuber_driver");
        }
    }

    @KafkaListener(topics = "user-funds-availability")
    void userFundsAvailabilityListener(String fundsAvailabilityJson) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(fundsAvailabilityJson);
        UUID uuid = UUID.fromString(jsonNode.get("uuid").asText());
        UUID userUuid = UUID.fromString(jsonNode.get("userUuid").asText());
        String from = jsonNode.get("pickUpLocation").asText();
        String to = jsonNode.get("dropOffLocation").asText();

        BigDecimal cost = BigDecimal.valueOf(googleDistanceMatrixService.getDistanceInt(from, to) * 0.002).setScale(2, RoundingMode.HALF_UP);
        userService.payForRide(userUuid, cost);

        FundsAvailabilityResponse response = FundsAvailabilityResponse.builder()
                .uuid(uuid)
                .userUuid(userUuid)
                .cost(cost)
                .build();

        kafkaProducerService.sendFundsAvailabilityResponse(response);
    }

    @KafkaListener(topics = "ride-request")
    void rideRequestListener(String rideRequestJson) {
        try {
            RideRequest rideRequest = objectMapper.readValue(rideRequestJson, RideRequest.class);
            rideRequestService.save(rideRequest);

            logger.info("Ride request successfully saved [from zuber_user]");

            JsonNode jsonNode = objectMapper.readTree(rideRequestJson);
            String pickUpLocationFromJson = jsonNode.get("pickUpLocation").asText();
            Driver driver = driverService.getNearestAvailableDriver(pickUpLocationFromJson);

            logger.info("Driver who will be asked for a ride: {}", driver);

            RideAssignment rideAssignment = RideAssignment.builder()
                    .uuid(UUID.randomUUID())
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
        RideAssignmentResponse rideAssignmentResponse = null;

        try {
            rideAssignmentResponse = objectMapper.readValue(rideAssignmentResponseJson, RideAssignmentResponse.class);

            RideAssignment currentRideAssignment = rideAssignmentService.findById(rideAssignmentResponse.getRideAssignmentId());

            if (!currentRideAssignment.getStatus().equals(RideAssignmentStatus.ACCEPTED)) {
                rideAssignmentService.updateStatus(rideAssignmentResponse.getRideAssignmentId(), rideAssignmentResponse.getAccepted());
            } else {
                logger.info("RideAssignment with ID {} is already ACCEPTED and cannot be changed.", currentRideAssignment.getId());
                return;
            }
        } catch (JsonProcessingException e) {
            throw new KafkaMessageProcessingException("Problem with receiving ride-assignment-response from zuber_driver");
        }

        logger.info("Successfully updated RideAssignment status!");

        if (rideAssignmentResponse.getAccepted()) {
            RideAssignment rideAssignment = rideAssignmentService.findById(rideAssignmentResponse.getRideAssignmentId());
            RideRequest rideRequest = rideRequestService.findByUUID(rideAssignment.getRideRequestUUID());
            Driver driver = driverService.findByUUID(rideAssignment.getDriverUUID());
            LocalDateTime now = LocalDateTime.now();
            String from = rideRequest.getPickUpLocation();
            String to = rideRequest.getDropOffLocation();

            int distanceBetween = googleDistanceMatrixService.getDistanceInt(from, to);

            RideInfo rideInfo = RideInfo.builder()
                    .rideAssignmentUuid(rideAssignment.getUuid())
                    .userUuid(rideRequest.getUserUuid())
                    .driverUuid(rideAssignment.getDriverUUID())
                    .driverName(driver.getName())
                    .driverLocation(driver.getLocation())
                    .pickUpLocation(rideRequest.getPickUpLocation())
                    .dropUpLocation(rideRequest.getDropOffLocation())
                    .orderTime(now)
                    .estimatedArrivalTime(now.plusSeconds(googleDistanceMatrixService.getDurationInt(from, to)))
                    .costOfRide(BigDecimal.valueOf(distanceBetween * 0.002))
                    .timeToArrivalInMinutes(googleDistanceMatrixService.getDurationString(from, to))
                    .rideLengthInKilometers(googleDistanceMatrixService.getDistanceString(from, to))
                    .build();

            rideInfoService.save(rideInfo);
            kafkaProducerService.sendRideInfo(rideInfo);
            emailService.sendRideInfoWhenRideRequestAcceptedToUser(rideInfo);
            emailService.sendRideInfoWhenRideRequestAcceptedToDriver(rideInfo);
        } else {
            logger.info("Ride assignment with id {} was REJECTED by driver.", rideAssignmentResponse.getRideAssignmentId());
            emailService.sendRideInfoWhenRideRequestRejectedToUser(rideAssignmentResponse);
            emailService.sendRideInfoWhenRideRequestRejectedToDriver(rideAssignmentResponse);
        }
    }

    @KafkaListener(topics = "ride-cancel")
    void rideCancelListener(String rideCancelResponseJson) throws JsonProcessingException {
        RideCancel rideCancel = objectMapper.readValue(rideCancelResponseJson, RideCancel.class);
        UUID rideUUID = rideCancel.getRideAssignmentUuid();

        rideCancelService.cancelRideAssignment(rideUUID);
    }

    @KafkaListener(topics = "user-message")
    void userMessageListener(String messageJson) throws JsonProcessingException {
        CreateMessageCommand command = objectMapper.readValue(messageJson, CreateMessageCommand.class);

        User user = userService.findByUuid(command.getSenderUuid());

        Message message = messageService.saveMessageFromUserToConversation(command);
        kafkaProducerService.sendMessageToUser(message, user);
    }

    @KafkaListener(topics = "driver-message")
    void driverMessageListener(String messageJson) throws JsonProcessingException {
        CreateMessageCommand command = objectMapper.readValue(messageJson, CreateMessageCommand.class);

        Driver driver = driverService.findByUUID(command.getSenderUuid());

        Message message = messageService.saveMessageFromDriverToConversation(command);
        kafkaProducerService.sendMessageToDriver(message, driver);
    }
}


