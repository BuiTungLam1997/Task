package com.example.task.customrepository;

import com.example.task.entity.TaskEntity;

import java.util.List;

public interface ITaskRepository {
    List<TaskEntity> findByPeriod(Long userId, int period);
}
