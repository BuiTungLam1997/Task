package com.example.task.repository;

import com.example.task.entity.CommentEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    List<CommentEntity> findByTaskId(Long taskId);
    int countByTaskId(Long taskId);
}
