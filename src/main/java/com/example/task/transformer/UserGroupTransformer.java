package com.example.task.transformer;

import com.example.task.dto.UserGroupDTO;
import com.example.task.entity.UserGroupEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserGroupTransformer extends CommonTransformer<UserGroupDTO, UserGroupEntity>
        implements ITransformer<UserGroupDTO, UserGroupEntity> {

    public UserGroupTransformer(ModelMapper modelMapper) {
        super(UserGroupDTO.class, UserGroupEntity.class, modelMapper);
    }
}
