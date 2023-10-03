package com.example.task.service.impl;

import com.example.task.dto.TaskDTO;
import com.example.task.entity.TaskEntity;
import com.example.task.repository.TaskRepository;
import com.example.task.service.ITaskService;
import com.example.task.service.specifications.Filter.Filter;
import com.example.task.service.specifications.Filter.FilterBuilder;
import com.example.task.service.specifications.TaskSearch;
import com.example.task.transformer.TaskTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.task.service.specifications.Filter.Filter.QueryOperator.LIKE;

@Service
public class TaskService extends BaseService implements ITaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskTransformer taskTransformer;
    @Autowired
    private TaskSearch taskSearch;

    @Override
    public List<TaskDTO> findAll() {

        List<TaskEntity> taskEntities = taskRepository.findAll();
        return taskEntities.stream()
                .map(taskTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAll(Pageable pageable) {
        List<TaskEntity> taskEntities = taskRepository.findAll(pageable).getContent();
        return taskEntities.stream()
                .map(taskTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllByUsername(Pageable pageable, String username) {
        List<TaskEntity> taskEntities = taskRepository.findAllByPerformer(username);
        return taskEntities.stream()
                .map(taskTransformer::toDto)
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
    public Optional<TaskDTO> findById(Long id) {
        return taskRepository.findById(id).map(taskTransformer::toDto);
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        TaskEntity taskEntity = modelMapper.map(taskDTO, TaskEntity.class);
        return taskTransformer.toDto(taskRepository.save(taskEntity));
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO) {
        TaskEntity taskEntity = modelMapper.map(taskDTO, TaskEntity.class);
        return taskTransformer.toDto(taskRepository.save(taskEntity));
    }

    @Override
    public void delete(Long[] ids) {
        for (Long item : ids) {
            taskRepository.deleteById(item);
        }

    }

    @Override
    public Page<TaskDTO> query(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(item -> taskTransformer.toDto(item));
    }

    @Override
    public Page<TaskDTO> queryExample(Pageable pageable, String search) {
        return taskRepository.findAll(buildFilter(search), pageable)
                .map(taskTransformer::toDto);
    }

    private Specification<TaskEntity> buildFilter(String search) {
        Filter filterTitle = new FilterBuilder()
                .buildField(TaskEntity.Fields.title)
                .buildOperator(LIKE)
                .buildValue(search)
                .build();
        Filter filterPerformer = new FilterBuilder()
                .buildField(TaskEntity.Fields.performer)
                .buildOperator(LIKE)
                .buildValue(search)
                .build();
        Filter filterStatus = new FilterBuilder()
                .buildField(TaskEntity.Fields.status)
                .buildOperator(LIKE)
                .buildValue(search)
                .build();
        return taskSearch.createSpecification(filterTitle)
                .or(taskSearch.createSpecification(filterPerformer))
                .or(taskSearch.createSpecification(filterStatus));
    }

    @Override
    public List<TaskDTO> searchTask(Pageable pageable, String search) {
        return taskRepository.findAll(buildFilter(search), pageable)
                .stream()
                .map(item -> taskTransformer.toDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValidDate(TaskDTO taskDTO) {
        int day = taskDTO.getDeadlineStart().compareTo(Timestamp.valueOf(LocalDateTime.now()));
        int day1 = taskDTO.getDeadlineEnd().compareTo(taskDTO.getDeadlineStart());
        return day >= 0 && day1 > 0;
    }
}
