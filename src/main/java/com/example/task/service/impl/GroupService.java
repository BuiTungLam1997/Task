package com.example.task.service.impl;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserGroupDTO;
import com.example.task.entity.UserEntity;
import com.example.task.entity.UserGroupEntity;
import com.example.task.repository.GroupRepository;
import com.example.task.repository.UserGroupRepository;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService implements IGroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private IUserGroupService userGroupService;
    ModelMapper mapper = new ModelMapper();

    @Override
    public GroupDTO findById(Long groupId) {
        return mapper.map(groupRepository.findById(groupId), GroupDTO.class);
    }

    @Override
    public List<GroupDTO> findByUserId(Long userId) {
        List<GroupDTO> listGroup = new ArrayList<>();
        List<UserGroupDTO> userGroupDTOS = userGroupService.findByUserId(userId);
        for (UserGroupDTO item : userGroupDTOS) {
            listGroup.add(mapper.map(groupRepository.findById(item.getGroupId()), GroupDTO.class));
        }
        return listGroup;
    }
}
