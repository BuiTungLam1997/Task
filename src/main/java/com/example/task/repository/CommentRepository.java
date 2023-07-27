package com.example.task.repository;

import com.example.task.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
}
