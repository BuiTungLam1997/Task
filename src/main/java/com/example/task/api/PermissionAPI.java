package com.example.task.api;

import com.example.task.dto.PermissionDTO;
import com.example.task.service.impl.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/permission")
public class PermissionAPI {
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    public PermissionDTO createPermission(@RequestBody PermissionDTO permissionDTO) {
        return permissionService.save(permissionDTO);
    }

    @PutMapping
    public PermissionDTO updatePermission(@RequestBody PermissionDTO permissionDTO) {
        return permissionService.update(permissionDTO);
    }

    @DeleteMapping
    public void deletePermission(@RequestBody PermissionDTO permissionDTO) {
        permissionService.delete(permissionDTO.getIds());
    }
}
