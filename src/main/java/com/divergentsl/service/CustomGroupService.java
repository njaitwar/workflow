package com.divergentsl.service;

import com.divergentsl.domain.CustomGroup;

import java.util.List;
import java.util.Optional;

public interface CustomGroupService {
    Optional<CustomGroup> findByGroupId(String groupId);

    List<CustomGroup> findAllGroups();

    CustomGroup createGroup(CustomGroup customGroup);

    CustomGroup updateGroup(String groupId, CustomGroup customGroup);

    void deleteGroup(String groupId);

    void addUserToGroup(String userId, String groupId);

    void removeUserFromGroup(String userId, String groupId);
}
