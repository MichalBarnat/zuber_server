package com.bbc.zuber.config.kafka;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.user.User;
import com.bbc.zuber.service.DriverService;
import com.bbc.zuber.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final UserService userService;
    private final DriverService driverService;

    @KafkaListener(topics = "user-registration")
    void userRegistrationListener(User user) {
        userService.save(user);
        System.out.println("succesfully added user from zuber_user");
    }

    @KafkaListener(topics = "user-deleted")
    void userDeleteListener(Long id) {

    }

    @KafkaListener(topics = "driver-registration")
    void driverRegistrationListener(Driver driver) {
        driverService.save(driver);
        System.out.println("Succesfully added driver from zuber_driver");
    }

    //TODO dodac LOGGERA

}
