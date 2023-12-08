package com.example.task.service.impl;

import com.example.task.dto.PermissionDTO;
import com.example.task.dto.PermissionGroupDTO;
import com.example.task.entity.PermissionEntity;
import com.example.task.entity.PermissionGroupEntity;
import com.example.task.repository.PermissionGroupRepository;
import com.example.task.repository.PermissionRepository;
import com.example.task.service.IPermissionService;
import com.example.task.service.IUserGroupService;
import com.example.task.transformer.CommonTransformer;
import com.example.task.transformer.PermissionTransformer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PermissionService implements IPermissionService {


    private PermissionRepository permissionRepository;

    private PermissionTransformer permissionTransformer;

    private PermissionGroupRepository permissionGroupRepository;

    @Override
    public PermissionDTO save(PermissionDTO permissionDTO) {
        return permissionTransformer.toDto(permissionRepository.save(permissionTransformer.toEntity(permissionDTO)));
    }

    @Override
    public PermissionDTO update(PermissionDTO permissionDTO) {
        return permissionTransformer.toDto(permissionRepository.save(permissionTransformer.toEntity(permissionDTO)));
    }

    @Override
    @Transactional
    public void delete(Long[] ids) {
        permissionRepository.deleteAllById(Arrays.asList(ids));
    }

    @Override
    public List<PermissionDTO> findByGroupId(Long groupId) {
        return permissionGroupRepository.findByGroupId(groupId).stream()
                .map(permissionTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findAll() {
        return permissionRepository.findAll().stream()
                .map(permissionTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDTO> findAll(Pageable pageable) {
        return permissionRepository.findAll().stream()
                .map(permissionTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PermissionDTO> findById(Long id) {
        return permissionRepository.findById(id).map(permissionTransformer::toDto);
    }

    @Override
    public Page<PermissionDTO> query(Pageable pageable) {
        return permissionRepository.findAll(pageable)
                .map(permissionTransformer::toDto);
    }
}
