package com.example.task.service;

import com.example.task.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITaskService {

    Page<TaskDTO> findAllByUsername(Pageable pageable, String username);

    Integer getTotalItemByUsername(String username);

    TaskDTO save(TaskDTO taskDTO);

    TaskDTO update(TaskDTO taskDTO);

    void delete(Long[] ids);

    Page<TaskDTO> searchTask(Pageable pageable, String search);

    boolean isValidDate(TaskDTO taskDTO);

    List<TaskDTO> expire();

    List<TaskDTO> findFollowByUserId();

    Page<TaskDTO> findByPeriod(Long userId,int period);
    int totalItemByPeriod(Long userId,int period);
    void saveEmail(TaskDTO taskDTO);
    void setEmail(TaskDTO taskOld, TaskDTO taskDTO);
    List<TaskDTO> findAll();

    List<TaskDTO> findAll(Pageable pageable);

    Optional<TaskDTO> findById(Long id);

    Page<TaskDTO> query(Pageable pageable);
}
