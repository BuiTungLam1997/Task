package com.example.task.dto;


public class UserGroupDTO extends BaseDTO {

    private Long userId;

    private Long permissionId;
    private Long[] permissionIds;

    private Long groupId;

    public Long[] getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
