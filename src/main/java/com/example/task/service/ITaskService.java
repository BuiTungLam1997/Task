package com.example.task.service;

import com.example.task.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITaskService {
    List<TaskDTO> findAll();
    List<TaskDTO> findAll(Pageable pageable);
    Page<TaskDTO> query(Pageable pageable);
    Page<TaskDTO> queryExample(Pageable pageable);
    List<TaskDTO> findAllByUsername(Pageable pageable,String username);
    Integer getTotalItem();
    Integer getTotalItemByUsername(String username);
    TaskDTO findById(Long id);
    TaskDTO save(TaskDTO taskDTO);
    TaskDTO update(TaskDTO taskDTO);
    int countByTitle(String title);
    int countByPerformer(String performer);
    List<TaskDTO> searchTask(String search);
}
