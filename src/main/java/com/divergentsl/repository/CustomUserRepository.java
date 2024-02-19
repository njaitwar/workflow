package com.divergentsl.repository;

import com.divergentsl.domain.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, String> {
    Optional<CustomUser> findByUserId(String id);

}
