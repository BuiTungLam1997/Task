package com.example.task.repository;

import com.example.task.entity.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroupEntity,Long> {

    @Query(value = "SELECT DISTINCT u.groupId FROM UserGroupEntity u WHERE u.userId = ?1")
    List<Long> findByUserId(Long userId);

    @Query(value = "SELECT DISTINCT u.userId FROM UserGroupEntity u WHERE u.groupId = ?1")
    List<Long> findByGroupId(Long groupId);
}
