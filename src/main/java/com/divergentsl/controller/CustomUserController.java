package com.divergentsl.controller;

import com.divergentsl.domain.CustomUser;
import com.divergentsl.dto.ApiResponse;
import com.divergentsl.exception.ResourceNotFoundException;
import com.divergentsl.service.CustomUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class CustomUserController {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserController.class);
    private final CustomUserService customUserService;

    @Autowired
    public CustomUserController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CustomUser>> getUserById(@PathVariable String userId) {
        try {
            return customUserService.findByUserId(userId)
                    .map(user -> ResponseEntity.ok(ApiResponse.success("User found successfully", user)))
                    .orElseGet(() -> ResponseEntity.status(404).body(ApiResponse.error("User not found")));
        } catch (Exception e) {
            logger.error("Error occurred while getting user by ID: {}", userId, e);
            return ResponseEntity.status(500).body(ApiResponse.error("Internal server error"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomUser>> createUser(@Valid @RequestBody CustomUser customUser) {
        try {
            return ResponseEntity.ok(ApiResponse.success("User created successfully", customUserService.createUser(customUser)));
        } catch (Exception e) {
            logger.error("Error occurred while creating user", e);
            return ResponseEntity.status(500).body(ApiResponse.error("Internal server error"));
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<CustomUser>> updateUser(@PathVariable String userId, @Valid @RequestBody CustomUser customUser) {
        try {
            return ResponseEntity.ok(ApiResponse.success("User updated successfully", customUserService.updateUser(userId, customUser)));
        } catch (ResourceNotFoundException e) {
            logger.error("User not found with ID: {}", userId, e);
            return ResponseEntity.status(404).body(ApiResponse.error("User not found"));
        } catch (Exception e) {
            logger.error("Error occurred while updating user with ID: {}", userId, e);
            return ResponseEntity.status(500).body(ApiResponse.error("Internal server error"));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String userId) {
        try {
            customUserService.deleteUser(userId);
            return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
        } catch (ResourceNotFoundException e) {
            logger.error("User not found with ID: {}", userId, e);
            return ResponseEntity.status(404).body(ApiResponse.error("User not found"));
        } catch (Exception e) {
            logger.error("Error occurred while deleting user with ID: {}", userId, e);
            return ResponseEntity.status(500).body(ApiResponse.error("Internal server error"));
        }
    }
}
