package com.example.task.dto;

import lombok.Data;

@Data
public class PermissionGroupDTO extends BaseDTO<PermissionGroupDTO> {
    private Long permissionId;
    private Long groupId;
    private Long[] permissionIds;
}
