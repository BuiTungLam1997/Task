package com.example.task.service.impl;

import com.example.task.dto.PermissionDTO;
import com.example.task.repository.PermissionRepository;
import com.example.task.service.IPermissionService;
import com.example.task.service.IUserGroupService;
import com.example.task.transformer.PermissionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionService implements IPermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionTransformer permissionTransformer;
    @Autowired
    private IUserGroupService userGroupService;

    @Override
    public PermissionDTO save(PermissionDTO permissionDTO) {
        return permissionTransformer.toDto(permissionRepository.save(permissionTransformer.toEntity(permissionDTO)));
    }

    @Override
    public PermissionDTO update(PermissionDTO permissionDTO) {
        return permissionTransformer.toDto(permissionRepository.save(permissionTransformer.toEntity(permissionDTO)));
    }

    @Override
    public void delete(Long[] ids) {
        for (Long item : ids) {
            userGroupService.deleteByPermissionId(item);
            permissionRepository.deleteById(item);
        }
    }

    @Override
    public List<PermissionDTO> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable).stream()
                .map(permissionTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PermissionDTO> findAllPage(Pageable pageable) {
        return permissionRepository.findAll(pageable)
                .map(permissionTransformer::toDto);
    }

    @Override
    public Optional<PermissionDTO> findById(Long id) {
        return permissionRepository.findById(id).map(permissionTransformer::toDto);
    }
}
