package com.example.task.service;

import com.example.task.dto.GroupDTO;

import java.util.List;

public interface IGroupService {
    GroupDTO findById(Long groupId);
    List<GroupDTO> findByUserId(Long userId);
}
