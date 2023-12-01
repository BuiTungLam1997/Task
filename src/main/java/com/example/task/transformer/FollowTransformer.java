package com.example.task.transformer;

import com.example.task.dto.FollowDTO;
import com.example.task.dto.UserDTO;
import com.example.task.entity.FollowEntity;
import com.example.task.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FollowTransformer extends CommonTransformer<FollowDTO, FollowEntity> implements ITransformer<FollowDTO, FollowEntity> {

    public FollowTransformer(ModelMapper modelMapper) {
        super(FollowDTO.class, FollowEntity.class, modelMapper);
    }
}
