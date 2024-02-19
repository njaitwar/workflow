package com.divergentsl.controller;

import com.divergentsl.domain.CustomGroup;
import com.divergentsl.dto.ApiResponse;
import com.divergentsl.exception.ResourceNotFoundException;
import com.divergentsl.service.CustomGroupService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/groups")
@Slf4j
public class CustomGroupController {
    private static final Logger logger = LoggerFactory.getLogger(CustomGroupController.class);
    private final CustomGroupService customGroupService;

    public CustomGroupController(CustomGroupService customGroupService) {
        this.customGroupService = customGroupService;
    }
    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponse<CustomGroup>> getGroupById(@PathVariable String groupId) {
        try {
            return customGroupService.findByGroupId(groupId)
                    .map(group -> ResponseEntity.ok(ApiResponse.success("Group found successfully", group)))
                    .orElseGet(() -> ResponseEntity.status(404).body(ApiResponse.error("Group not found")));
        } catch (Exception e) {
            logger.error("Error occurred while getting group by ID: {}", groupId, e);
            return ResponseEntity.status(500).body(ApiResponse.error("Internal server error"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomGroup>> createGroup(@Valid @RequestBody CustomGroup customGroup) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Group created successfully", customGroupService.createGroup(customGroup)));
        } catch (Exception e) {
            logger.error("Error occurred while creating group", e);
            return ResponseEntity.status(500).body(ApiResponse.error("Internal server error"));
        }
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<ApiResponse<CustomGroup>> updateGroup(@PathVariable String groupId, @Valid @RequestBody CustomGroup customGroup) {
        try {
            return ResponseEntity.ok(ApiResponse.success("Group updated successfully", customGroupService.updateGroup(groupId, customGroup)));
        } catch (ResourceNotFoundException e) {
            logger.error("Group not found with ID: {}", groupId, e);
            return ResponseEntity.status(404).body(ApiResponse.error("Group not found"));
        } catch (Exception e) {
            logger.error("Error occurred while updating group with ID: {}", groupId, e);
            return ResponseEntity.status(500).body(ApiResponse.error("Internal server error"));
        }
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Void>> deleteGroup(@PathVariable String groupId) {
        try {
            customGroupService.deleteGroup(groupId);
            return ResponseEntity.ok(ApiResponse.success("Group deleted successfully"));
        } catch (ResourceNotFoundException e) {
            logger.error("Group not found with ID: {}", groupId, e);
            return ResponseEntity.status(404).body(ApiResponse.error("Group not found"));
        } catch (Exception e) {
            logger.error("Error occurred while deleting group with ID: {}", groupId, e);
            return ResponseEntity.status(500).body(ApiResponse.error("Internal server error"));
        }
    }
}
