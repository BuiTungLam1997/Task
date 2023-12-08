package com.example.task.service;

import com.example.task.dto.PermissionGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPermissionGroupService {
    void save(PermissionGroupDTO permissionGroupDTO);

    void update(PermissionGroupDTO permissionGroupDTO);

    void delete(PermissionGroupDTO permissionGroupDTO);
    List<PermissionGroupDTO> findAll();

    List<PermissionGroupDTO> findAll(Pageable pageable);

    Optional<PermissionGroupDTO> findById(Long id);

    Page<PermissionGroupDTO> query(Pageable pageable);
}
