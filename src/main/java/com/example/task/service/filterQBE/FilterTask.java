package com.example.task.service.filterQBE;

import com.example.task.dto.TaskDTO;
import com.example.task.entity.TaskEntity;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class FilterTask extends CommonFilter<TaskDTO, TaskEntity> {
    public FilterTask(ModelMapper modelMapper, JpaRepository<TaskEntity, Long> repository) {
        super(modelMapper, repository, TaskEntity.class, TaskDTO.class);
    }
}
