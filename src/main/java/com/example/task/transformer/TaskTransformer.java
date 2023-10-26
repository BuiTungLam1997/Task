package com.example.task.transformer;

import com.example.task.dto.TaskDTO;
import com.example.task.entity.TaskEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class TaskTransformer extends CommonTransformer<TaskDTO, TaskEntity> implements ITransformer<TaskDTO, TaskEntity> {

    public TaskTransformer(ModelMapper modelMapper) {
        super(TaskDTO.class, TaskEntity.class, modelMapper);
    }
}
