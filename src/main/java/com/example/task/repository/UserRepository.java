package com.example.task.repository;

import com.example.task.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameAndStatus(String username, String status);

    Optional<UserEntity> findByUsername(String username);

    @Query(value = "select count(*) from USER where USERNAME like ?1%", nativeQuery = true)
    int lastNumberUser(String username);
}
