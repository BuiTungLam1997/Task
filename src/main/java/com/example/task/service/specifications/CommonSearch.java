package com.example.task.service.specifications;

import com.example.task.entity.TaskEntity;
import com.example.task.service.specifications.Filter.Filter;
import org.springframework.data.jpa.domain.Specification;

public abstract class CommonSearch<E> {
    public Specification<E> createSpecification(Filter input) {
        switch (input.getOperator()) {
            case LIKE:
                return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(input.getField()), "%" + input.getValue() + "%");
            case EQUALS:
                return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(input.getField()), "%" + input.getValue() + "%");
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }
}
