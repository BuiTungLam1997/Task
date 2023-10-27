package com.example.task.service;

import com.example.task.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T> {
    public List<T> findAll();

    public List<T> findAll(Pageable pageable);

    public Optional<T> findById(Long id);

    public Page<T> query(Pageable pageable);
}
