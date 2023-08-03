package com.example.task.service;

import com.example.task.dto.TaskDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITaskService {
    List<TaskDTO> findAll();
    List<TaskDTO> findAll(Pageable pageable);
    Integer getTotalItem();
}
