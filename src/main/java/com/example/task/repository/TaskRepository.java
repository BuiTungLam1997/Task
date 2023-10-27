package com.example.task.repository;

import com.example.task.dto.TaskDTO;
import com.example.task.entity.TaskEntity;
import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Primary
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {
    List<TaskEntity> findAllByPerformer(String performer);

    Page<TaskEntity> findAllByPerformer(Pageable pageable, String performer);

    Integer countByPerformer(String performer);

    @Query(value = "select t from TaskEntity t where t.deadlineEnd <= ?1")
    List<TaskEntity> findLessThanOrEqualDeadlineEnd(LocalDate deadlineEnd);

    @Query(value = "select t from TaskEntity t inner join FollowEntity f on f.taskId = t.id where f.userId = ?1")
    List<TaskEntity> findFollowByUserId(Long userId);

    @Query(value = "select t from TaskEntity t inner join FollowEntity f on f.taskId=t.id where f.userId = ?1")
    List<TaskEntity> findAllByUserFollow(Long userId);
}
