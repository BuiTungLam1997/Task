package com.example.task.repository;

import com.example.task.entity.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroupEntity,Long> {
    List<UserGroupEntity> findByUserId(Long userId);
}
