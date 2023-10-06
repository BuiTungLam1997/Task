package com.example.task.service.filter;

import com.example.task.dto.BaseDTO;
import com.example.task.entity.BaseEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFilter<T,E> {
    List<T> search(T t,E e, Pageable pageable);
}
