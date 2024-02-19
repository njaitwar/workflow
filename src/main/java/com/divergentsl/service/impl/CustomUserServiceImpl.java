package com.divergentsl.service.impl;

import com.divergentsl.domain.CustomUser;
import com.divergentsl.exception.ResourceNotFoundException;
import com.divergentsl.repository.CustomUserRepository;
import com.divergentsl.service.CustomUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomUserServiceImpl implements CustomUserService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserServiceImpl.class);

    private final CustomUserRepository customUserRepository;
//    private final PasswordEncoder passwordEncoder;

    public CustomUserServiceImpl(CustomUserRepository customUserRepository) {

        this.customUserRepository = customUserRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<CustomUser> findByUserId(String userId) {
        return customUserRepository.findByUserId(userId);
    }

    @Override
    public List<CustomUser> findAllUsers() {
        return customUserRepository.findAll();
    }

    @Override
    public CustomUser createUser(CustomUser customUser) {
        try {
//            customUser.setPassword(passwordEncoder.encode(customUser.getPassword()));
            customUserRepository.save(customUser);
            logger.info("User created successfully with ID: {}", customUser.getUserId());
            return customUser;
        } catch (Exception e) {
            logger.error("Error occurred while creating user: {}", e.getMessage());
            throw new ResourceNotFoundException("Error occurred while creating user.");
        }
    }

    @Override
    public CustomUser updateUser(String userId, CustomUser updatedUser) {
        Optional<CustomUser> optionalUser = customUserRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            CustomUser existingUser = optionalUser.get();
            existingUser.setFirstName(updatedUser.getFirstName() != null ? updatedUser.getFirstName() : existingUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName() != null ? updatedUser.getLastName() : existingUser.getLastName());
            customUserRepository.save(existingUser);
            logger.info("User updated successfully with ID: {}", userId);
            return existingUser;
        } else {
            logger.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public void deleteUser(String userId) {
        Optional<CustomUser> optionalUser = customUserRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            customUserRepository.delete(optionalUser.get());
            logger.info("User deleted successfully with ID: {}", userId);
        } else {
            logger.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }
}