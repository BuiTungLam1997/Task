package com.example.task.transformer;

import com.example.task.dto.GroupDTO;
import com.example.task.entity.GroupEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GroupTransformer extends CommonTransformer<GroupDTO, GroupEntity> implements ITransformer<GroupDTO, GroupEntity> {

    public GroupTransformer(ModelMapper modelMapper) {
        super(GroupDTO.class, GroupEntity.class, modelMapper);
    }
}
