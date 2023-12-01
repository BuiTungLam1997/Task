package com.example.task.service.impl;

import com.example.task.dto.PermissionGroupDTO;
import com.example.task.entity.PermissionGroupEntity;
import com.example.task.repository.PermissionGroupRepository;
import com.example.task.service.IPermissionGroupService;
import com.example.task.transformer.CommonTransformer;
import com.example.task.transformer.PermissionGroupTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class PermissionGroupService extends BaseService<PermissionGroupDTO, PermissionGroupEntity, PermissionGroupRepository> implements IPermissionGroupService {
    @Autowired
    private PermissionGroupRepository permissionGroupRepository;
    @Autowired
    private PermissionGroupTransformer permissionGroupTransformer;

    public PermissionGroupService(PermissionGroupRepository repo, CommonTransformer<PermissionGroupDTO, PermissionGroupEntity> transformer, EntityManager em) {
        super(repo, transformer, em);
    }
    @Override
    public void save(PermissionGroupDTO permissionGroupDTO) {
        for (Long permissionId : permissionGroupDTO.getPermissionIds()) {
            permissionGroupDTO.setPermissionId(permissionId);
            permissionGroupRepository.save(permissionGroupTransformer.toEntity(permissionGroupDTO));
        }
    }

    @Override
    public void update(PermissionGroupDTO permissionGroupDTO) {
        PermissionGroupEntity permissionGroupEntity = permissionGroupTransformer.toEntity(permissionGroupDTO);
        permissionGroupRepository.save(permissionGroupEntity);
    }

    @Override
    public void delete(PermissionGroupDTO permissionGroupDTO) {
        permissionGroupRepository.deleteById(permissionGroupDTO.getId());
    }
}
