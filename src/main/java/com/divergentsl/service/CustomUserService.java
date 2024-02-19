package com.divergentsl.service;

import com.divergentsl.domain.CustomUser;

import java.util.List;
import java.util.Optional;

public interface CustomUserService {
    Optional<CustomUser> findByUserId(String userId);

    List<CustomUser> findAllUsers();

    CustomUser createUser(CustomUser customUser);

    CustomUser updateUser(String userId, CustomUser customUser);

    void deleteUser(String userId);
}
