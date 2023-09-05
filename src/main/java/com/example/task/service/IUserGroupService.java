package com.example.task.service;

import com.example.task.dto.UserGroupDTO;

import java.util.List;

public interface IUserGroupService {
    List<Long> findGroupId(Long userId);
    UserGroupDTO save(UserGroupDTO userGroupDTO);
    void save(Long groupId,Long[] userId,Long[] permissionId );
}
