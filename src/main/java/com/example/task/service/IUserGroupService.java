package com.example.task.service;

import com.example.task.dto.UserDTO;
import com.example.task.dto.UserGroupDTO;

import java.util.List;

public interface IUserGroupService {
    List<UserGroupDTO> findByUserId(Long userId);
}
