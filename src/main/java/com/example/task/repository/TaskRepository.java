package com.example.task.repository;

import com.example.task.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {
    List<TaskEntity> findAllByPerformer(String performer);

    Integer countByPerformer(String performer);

    Integer countByPerformerContaining(String performer);

    Integer countByTitleContaining(String title);

}
