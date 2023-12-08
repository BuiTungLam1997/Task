package com.example.task.transformer;

import com.example.task.dto.PermissionGroupDTO;
import com.example.task.entity.PermissionGroupEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PermissionGroupTransformer extends CommonTransformer<PermissionGroupDTO, PermissionGroupEntity>
        implements ITransformer<PermissionGroupDTO, PermissionGroupEntity> {

    public PermissionGroupTransformer(ModelMapper modelMapper) {
        super(PermissionGroupDTO.class, PermissionGroupEntity.class, modelMapper);
    }
}
