package com.example.task.service.impl;

import com.example.task.dto.PermissionGroupDTO;
import com.example.task.entity.PermissionGroupEntity;
import com.example.task.repository.PermissionGroupRepository;
import com.example.task.service.IPermissionGroupService;
import com.example.task.transformer.CommonTransformer;
import com.example.task.transformer.PermissionGroupTransformer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PermissionGroupService implements IPermissionGroupService {
    private PermissionGroupRepository permissionGroupRepository;

    private PermissionGroupTransformer permissionGroupTransformer;


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

    @Override
    public List<PermissionGroupDTO> findAll() {
        return permissionGroupRepository.findAll().stream()
                .map(item -> permissionGroupTransformer.toDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionGroupDTO> findAll(Pageable pageable) {
        return permissionGroupRepository.findAll().stream()
                .map(permissionGroupTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PermissionGroupDTO> findById(Long id) {
        return permissionGroupRepository.findById(id).map(permissionGroupTransformer::toDto);
    }

    @Override
    public Page<PermissionGroupDTO> query(Pageable pageable) {
        return permissionGroupRepository.findAll(pageable)
                .map(item -> permissionGroupTransformer.toDto(item));
    }
}
