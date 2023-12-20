package com.bbc.zuber.service;

import com.bbc.zuber.exceptions.InsufficientFundsException;
import com.bbc.zuber.exceptions.UserNotFoundException;
import com.bbc.zuber.exceptions.UserUuidNotFoundException;
import com.bbc.zuber.model.user.User;
import com.bbc.zuber.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User findByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserUuidNotFoundException(uuid));
    }

    @Transactional
    public void payForRide(UUID userUuid, BigDecimal amount) {
        User user = findByUuid(userUuid);
        BigDecimal newBalance = user.getBalance().subtract(amount);

        if(newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException(String.format("User with uuid: %s don't have enough money for that ride!", user.getUuid()));
        }

        user.setBalance(newBalance);
        save(user);
    }

}
