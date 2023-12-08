package com.example.task.repository.customRepository;

import com.example.task.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITaskRepository {
    List<TaskEntity> findByPeriod(Long userId, int period);
}
