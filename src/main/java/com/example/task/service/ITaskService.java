package com.example.task.service;

import com.example.task.dto.TaskDTO;

import java.util.List;

public interface ITaskService {
    List<TaskDTO> findAll();
}
