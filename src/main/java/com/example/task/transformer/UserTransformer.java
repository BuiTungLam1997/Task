package com.example.task.transformer;

import com.example.task.dto.UserDTO;
import com.example.task.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer extends CommonTransformer<UserDTO, UserEntity> implements ITransformer<UserDTO, UserEntity> {

    public UserTransformer(ModelMapper modelMapper) {
        super(UserDTO.class, UserEntity.class, modelMapper);
    }
}
