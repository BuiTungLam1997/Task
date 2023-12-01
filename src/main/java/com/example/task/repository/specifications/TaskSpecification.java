package com.example.task.repository.specifications;

import com.example.task.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<TaskEntity> titleLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(TaskEntity.Fields.title), "%" + name + "%");
    }

    public static Specification<TaskEntity> performerLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(TaskEntity.Fields.performer), "%" + name + "%");
    }

}
