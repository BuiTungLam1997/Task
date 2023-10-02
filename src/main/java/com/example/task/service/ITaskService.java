package com.example.task.service;

import com.example.task.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    List<TaskDTO> findAll();
    List<TaskDTO> findAll(Pageable pageable);
    Page<TaskDTO> query(Pageable pageable);
    Page<TaskDTO> queryExample(Pageable pageable,String search);
    List<TaskDTO> findAllByUsername(Pageable pageable,String username);
    Integer getTotalItem();
    Integer getTotalItemByUsername(String username);
    Optional<TaskDTO> findById(Long id);
    TaskDTO save(TaskDTO taskDTO);
    TaskDTO update(TaskDTO taskDTO);
    void delete(Long[] ids);
    List<TaskDTO> searchTask(Pageable pageable,String search);
    boolean isValidDate(TaskDTO taskDTO);
}
