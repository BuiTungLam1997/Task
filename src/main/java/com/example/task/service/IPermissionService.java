package com.example.task.service;

import com.example.task.dto.PermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPermissionService extends IBaseService<PermissionDTO>{
    PermissionDTO save(PermissionDTO permissionDTO);
    PermissionDTO update(PermissionDTO permissionDTO);
    void delete(Long[] ids);
    List<PermissionDTO> findByGroupId(Long groupId);
}
