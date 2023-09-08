package com.example.task.service.impl;

import com.example.task.dto.GroupDTO;
import com.example.task.entity.GroupEntity;
import com.example.task.entity.TaskEntity;
import com.example.task.repository.GroupRepository;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService extends BaseService implements IGroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private IUserGroupService userGroupService;



    @Override
    public GroupDTO findById(Long groupId) {
        return modelMapper.map(groupRepository.findById(groupId), GroupDTO.class);
    }

    @Override
    public List<GroupDTO> findByUserId(Long userId) {
        List<Long> groupIdS = userGroupService.findGroupId(userId);
        return groupIdS.stream()
                .map(item -> groupRepository.findById(item))
                .map(item -> modelMapper.map(item, GroupDTO.class))
                .collect(Collectors.toList());

    }
}
