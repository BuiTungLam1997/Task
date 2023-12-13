package com.example.task.repository;
import com.example.task.entity.TaskEntity;
import com.example.task.customrepository.ITaskRepository;
import com.example.task.repository.specifications.TaskSearch;
import com.example.task.service.builderpattern.Filter.Filter;
import com.example.task.service.builderpattern.Filter.FilterBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity>, ITaskRepository {
    TaskSearch taskSearch = new TaskSearch();

    Page<TaskEntity> findAllByPerformer(Pageable pageable, String performer);

    Integer countByPerformer(String performer);

    @Query(value = "select t from TaskEntity t where t.deadlineEnd <= ?1")
    List<TaskEntity> findLessThanOrEqualDeadlineEnd(LocalDate deadlineEnd);

    @Query(value = "select t from TaskEntity t inner join FollowEntity f on f.taskId = t.id where f.userId = ?1")
    List<TaskEntity> findFollowByUserId(Long userId);

    @Query(value = "select t from TaskEntity t inner join FollowEntity f on f.taskId=t.id where f.userId = ?1")
    List<TaskEntity> findAllByUserFollow(Long userId);

    default Page<TaskEntity> searchTask(Pageable pageable, String search) {
        Filter filterByTile = new FilterBuilder()
                .buildField(TaskEntity.Fields.title)
                .buildOperator(Filter.QueryOperator.LIKE)
                .buildValue(search)
                .build();
        Filter filterByPerformer = new FilterBuilder()
                .buildField(TaskEntity.Fields.performer)
                .buildOperator(Filter.QueryOperator.LIKE)
                .buildValue(search)
                .build();
        Filter filterByStatus = new FilterBuilder()
                .buildField(TaskEntity.Fields.status)
                .buildOperator(Filter.QueryOperator.LIKE)
                .buildValue(search)
                .build();
        Specification<TaskEntity> specification = taskSearch.createSpecification(filterByStatus)
                .or(taskSearch.createSpecification(filterByPerformer))
                .or(taskSearch.createSpecification(filterByTile));
        return findAll(specification, pageable);
    }
}
