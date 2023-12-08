package com.example.task.transformer;

import com.example.task.dto.PermissionDTO;
import com.example.task.entity.PermissionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PermissionTransformer extends CommonTransformer<PermissionDTO, PermissionEntity> implements ITransformer<PermissionDTO, PermissionEntity> {

    public PermissionTransformer(ModelMapper modelMapper) {
        super(PermissionDTO.class, PermissionEntity.class, modelMapper);
    }
}
