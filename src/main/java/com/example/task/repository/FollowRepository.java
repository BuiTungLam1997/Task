package com.example.task.repository;

import com.example.task.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity,Long> , JpaSpecificationExecutor<FollowEntity> {
    List<FollowEntity> findByUserId(Long userId);
    void deleteByTaskId(Long taskId);
}
