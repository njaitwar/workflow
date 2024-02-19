package com.divergentsl.service.impl;

import com.divergentsl.domain.CustomGroup;
import com.divergentsl.domain.CustomUser;
import com.divergentsl.exception.ResourceNotFoundException;
import com.divergentsl.repository.CustomGroupRepository;
import com.divergentsl.repository.CustomUserRepository;
import com.divergentsl.service.CustomGroupService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomGroupServiceImpl implements CustomGroupService {
    private static final Logger logger = LoggerFactory.getLogger(CustomGroupServiceImpl.class);

    private final CustomGroupRepository customGroupRepository;
    private final CustomUserRepository customUserRepository;

    public CustomGroupServiceImpl(CustomGroupRepository customGroupRepository, CustomUserRepository customUserRepository) {
        this.customGroupRepository = customGroupRepository;
        this.customUserRepository = customUserRepository;
    }

    @Override
    public Optional<CustomGroup> findByGroupId(String groupId) {
        return customGroupRepository.findByGroupId(groupId);
    }

    @Override
    public List<CustomGroup> findAllGroups() {
        return customGroupRepository.findAll();
    }

    @Override
    public CustomGroup createGroup(CustomGroup customGroup) {
        try {
            customGroupRepository.save(customGroup);
            logger.info("Group created successfully with ID: {}", customGroup.getGroupId());
            return customGroup;
        } catch (Exception e) {
            logger.error("Error occurred while creating group: {}", e.getMessage());
            throw new ResourceNotFoundException("Error occurred while creating group.");
        }
    }

    @Override
    public CustomGroup updateGroup(String groupId, CustomGroup updatedGroup) {
        Optional<CustomGroup> optionalGroup = customGroupRepository.findByGroupId(groupId);
        if (optionalGroup.isPresent()) {
            CustomGroup existingGroup = optionalGroup.get();
            existingGroup.setName(updatedGroup.getName());
            customGroupRepository.save(existingGroup);
            logger.info("Group updated successfully with ID: {}", existingGroup.getGroupId());
            return existingGroup;
        } else {
            logger.error("Group not found with ID: {}", groupId);
            throw new ResourceNotFoundException("Group not found.");
        }
    }

    @Override
    public void deleteGroup(String groupId) {

    }

    @Override
    public void addUserToGroup(String userId, String groupId) {
        Optional<CustomUser> optionalUser = customUserRepository.findByUserId(userId);
        Optional<CustomGroup> optionalGroup = customGroupRepository.findByGroupId(groupId);
        if (optionalUser.isPresent() && optionalGroup.isPresent()) {
            CustomUser user = optionalUser.get();
            CustomGroup group = optionalGroup.get();
            group.getUsers().add(user);
            customGroupRepository.save(group);
            logger.info("User added to group successfully with IDs: {} and {}", userId, groupId);
        } else {
            logger.error("User or group not found with IDs: {} and {}", userId, groupId);
            throw new ResourceNotFoundException("User or group not found.");
        }
    }

    @Override
    public void removeUserFromGroup(String userId, String groupId) {
        Optional<CustomUser> optionalUser = customUserRepository.findByUserId(userId);
        Optional<CustomGroup> optionalGroup = customGroupRepository.findByGroupId(groupId);
        if (optionalUser.isPresent() && optionalGroup.isPresent()) {
            CustomUser user = optionalUser.get();
            CustomGroup group = optionalGroup.get();
            group.getUsers().remove(user);
            customGroupRepository.save(group);
            logger.info("User removed from group successfully with IDs: {} and {}", userId, groupId);
        } else {
            logger.error("User or group not found with IDs: {} and {}", userId, groupId);
            throw new ResourceNotFoundException("User or group not found.");
        }
    }
}