package com.example.task.service;

import com.example.task.dto.PermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {
    PermissionDTO save(PermissionDTO permissionDTO);
    PermissionDTO update(PermissionDTO permissionDTO);
    void delete(Long[] ids);
    List<PermissionDTO> findAll(Pageable pageable);
    Page<PermissionDTO> findAllPage(Pageable pageable);
    Optional<PermissionDTO> findById(Long id);
}
