package com.example.task.service;

import com.example.task.dto.PermissionGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPermissionGroupService extends IBaseService<PermissionGroupDTO> {
    void save(PermissionGroupDTO permissionGroupDTO);

    void update(PermissionGroupDTO permissionGroupDTO);

    void delete(PermissionGroupDTO permissionGroupDTO);
}
