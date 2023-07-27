package com.example.task.service.impl;

import com.example.task.dto.TaskDTO;
import com.example.task.entity.TaskEntity;
import com.example.task.repository.TaskRepository;
import com.example.task.service.ITaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;
    ModelMapper mapper = new ModelMapper();

    @Override
    public List<TaskDTO> findAll() {
        List<TaskDTO> taskDTOs = new ArrayList<>();
        List<TaskEntity> taskEntities = new ArrayList<>();
        taskEntities = taskRepository.findAll();
        for (TaskEntity item : taskEntities) {
            taskDTOs.add(mapper.map(item,TaskDTO.class));
        }
        return taskDTOs;
    }
}
