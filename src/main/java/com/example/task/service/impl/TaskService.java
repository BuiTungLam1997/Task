package com.example.task.service.impl;

import com.example.task.dto.TaskDTO;
import com.example.task.entity.TaskEntity;
import com.example.task.repository.TaskRepository;
import com.example.task.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService extends BaseService implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<TaskDTO> findAll() {

        List<TaskEntity> taskEntities = taskRepository.findAll();
        return taskEntities.stream()
                .map(item -> modelMapper.map(item, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAll(Pageable pageable) {
        List<TaskEntity> taskEntities = taskRepository.findAll(pageable).getContent();
        return taskEntities.stream()
                .map(item -> modelMapper.map(item,TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllByUsername(Pageable pageable, String username) {
        List<TaskEntity> taskEntities = taskRepository.findAllByPerformer(username);
        return taskEntities.stream()
                .map(item -> modelMapper.map(item, TaskDTO.class))
                .collect(Collectors.toList());
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
        return modelMapper.map(taskRepository.findById(id), TaskDTO.class);
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        TaskEntity taskEntity = modelMapper.map(taskDTO, TaskEntity.class);
        return modelMapper.map(taskRepository.save(taskEntity), TaskDTO.class);
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO) {
        TaskEntity taskEntity = modelMapper.map(taskDTO, TaskEntity.class);
        return modelMapper.map(taskRepository.save(taskEntity), TaskDTO.class);
    }

    @Override
    public int getTotalPage(TaskDTO taskDTO) {
        return (int) Math.ceil((double) (taskDTO.getTotalItem()) / taskDTO.getLimit());
    }
}
