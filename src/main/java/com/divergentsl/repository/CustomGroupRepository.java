package com.divergentsl.repository;

import com.divergentsl.domain.CustomGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomGroupRepository extends JpaRepository<CustomGroup, String> {
    Optional<CustomGroup> findByGroupId(String id);
}
