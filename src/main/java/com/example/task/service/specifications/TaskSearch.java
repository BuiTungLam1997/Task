package com.example.task.service.specifications;

import com.example.task.entity.TaskEntity;
import com.example.task.repository.TaskRepository;
import com.example.task.service.specifications.Filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TaskSearch extends CommonSearch<TaskEntity>{
    @Autowired
    private TaskRepository taskRepository;

    public Specification<TaskEntity> titleLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(TaskEntity.Fields.title), "%" + name + "%");
    }

    public Specification<TaskEntity> performerLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(TaskEntity.Fields.performer), "%" + name + "%");
    }
}
