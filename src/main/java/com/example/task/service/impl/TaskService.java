package com.example.task.service.impl;

import com.example.task.dto.TaskDTO;
import com.example.task.entity.TaskEntity;
import com.example.task.repository.TaskRepository;
import com.example.task.service.ITaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
        List<TaskEntity> taskEntities = taskRepository.findAll();
        for (TaskEntity item : taskEntities) {
            taskDTOs.add(mapper.map(item, TaskDTO.class));
        }
        return taskDTOs;
    }

    @Override
    public List<TaskDTO> findAll(Pageable pageable) {
        List<TaskDTO> taskDTOs = new ArrayList<>();
        List<TaskEntity> taskEntities = taskRepository.findAll(pageable).getContent();
        for (TaskEntity item : taskEntities) {
            taskDTOs.add(mapper.map(item, TaskDTO.class));
        }
        return taskDTOs;
    }

    @Override
    public List<TaskDTO> findAllByUsername(Pageable pageable, String username) {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        List<TaskEntity> taskEntities = taskRepository.findAllByPerformer(username);
        for (TaskEntity item : taskEntities) {
            taskDTOS.add(mapper.map(item, TaskDTO.class));
        }
        return taskDTOS;
    }

    @Override
    public Integer getTotalItem() {
        return (int) taskRepository.count();
    }

    @Override
    public Integer getTotalItemByUsername(String username) {
        return taskRepository.countByPerformer(username);
    }

    @Override
    public TaskDTO findById(Long id) {
        TaskDTO taskDTO = mapper.map(taskRepository.findById(id), TaskDTO.class);
        return taskDTO;
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        TaskEntity taskEntity = mapper.map(taskDTO, TaskEntity.class);
        return mapper.map(taskRepository.save(taskEntity), TaskDTO.class);
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO) {
        TaskEntity taskEntity = mapper.map(taskDTO, TaskEntity.class);
        return mapper.map(taskRepository.save(taskEntity), TaskDTO.class);
    }
}
