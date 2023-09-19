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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.task.service.specifications.QueryOperator.LIKE;

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
                .map(item -> taskTransformer.toDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAll(Pageable pageable) {
        List<TaskEntity> taskEntities = taskRepository.findAll(pageable).getContent();
        return taskEntities.stream()
                .map(item -> taskTransformer.toDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllByUsername(Pageable pageable, String username) {
        List<TaskEntity> taskEntities = taskRepository.findAllByPerformer(username);
        return taskEntities.stream()
                .map(item -> taskTransformer.toDto(item))
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
        return taskTransformer.toDto(taskRepository.save(taskEntity));
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO) {
        TaskEntity taskEntity = modelMapper.map(taskDTO, TaskEntity.class);
        return taskTransformer.toDto(taskRepository.save(taskEntity));
    }

    @Override
    public Page<TaskDTO> query(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(item -> taskTransformer.toDto(item));
    }

    @Override
    public Page<TaskDTO> queryExample(Pageable pageable) {
        TaskDTO taskDTO = new TaskDTO();
        Example<TaskEntity> example = getExample(taskDTO);
        return taskRepository.findAll(example, pageable)
                .map(item -> taskTransformer.toDto(item));
    }

    private Example<TaskEntity> getExample(TaskDTO taskDTO) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        TaskEntity entity = modelMapper.map(taskDTO, TaskEntity.class);
        return Example.of(entity, matcher);
    }

    @Override
    public int countByTitle(String title) {
        return taskRepository.countByTitleContaining(title);
    }

    @Override
    public int countByPerformer(String performer) {
        return taskRepository.countByPerformerContaining(performer);
    }

    @Override
    public List<TaskDTO> searchTask(String search) {
        if (countByTitle(search) != 0) {
            Filter filter = new FilterBuilder()
                    .buildField("title")//check field trước
                    .buildOperator(LIKE)
                    .buildValue(search)
                    .build();
            return taskRepository.findAll(taskSearch.createSpecification(filter))
                    .stream()
                    .map(item -> taskTransformer.toDto(item))
                    .collect(Collectors.toList());
        }
        return taskRepository.findAll(taskSearch.performerLike(search)).stream()
                .map(item -> taskTransformer.toDto(item))
                .collect(Collectors.toList());

    }
}
