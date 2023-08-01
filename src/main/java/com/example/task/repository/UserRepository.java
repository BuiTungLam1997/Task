package com.example.task.repository;

import com.example.task.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUsernameAndStatus(String username,String status);
}
