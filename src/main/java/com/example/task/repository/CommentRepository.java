package com.example.task.repository;

import com.example.task.entity.CommentEntity;
import com.example.task.repository.projection.CommentProjection;
import com.example.task.repository.projection.CommentProjection.CommentWithFullName;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>, JpaSpecificationExecutor<CommentEntity> {
    @Query("SELECT c.id as id, c.userId as userId, c.taskId as taskId, c.content as content , c.createdDate as createdDate,u.fullName as fullName " +
            "from CommentEntity c INNER JOIN UserEntity u ON u.id = c.userId WHERE c.taskId = ?1")
    List<CommentWithFullName> findByTaskId(Long taskId);

    int countByTaskId(Long taskId);
}
