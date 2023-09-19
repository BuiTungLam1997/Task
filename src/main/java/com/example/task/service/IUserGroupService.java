package com.example.task.service;

import com.example.task.dto.UserGroupDTO;

import java.util.List;

public interface IUserGroupService {
    List<Long> findGroupId(Long userId);
    UserGroupDTO save(UserGroupDTO userGroupDTO);
    UserGroupDTO update(UserGroupDTO userGroupDTO);
    void delete(Long[] ids);
    void save(Long groupId,Long[] userId,Long[] permissionId );
}
