package com.example.task.repository;

import com.example.task.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    List<TaskEntity> findAllByPerformer(String performer);
    Integer countByPerformer(String performer);
}
