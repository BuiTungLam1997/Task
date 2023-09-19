package com.example.task.repository;

import com.example.task.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUsernameAndStatus(String username, String status);

    Optional<UserEntity> findByUsername(String username);

    int countByUsernameStartsWith(String username);
}

