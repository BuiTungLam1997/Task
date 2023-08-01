package com.example.task.service.impl;

import com.example.task.dto.UserGroupDTO;
import com.example.task.entity.UserGroupEntity;
import com.example.task.repository.UserGroupRepository;
import com.example.task.service.IUserGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserGroupService implements IUserGroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;
    ModelMapper mapper = new ModelMapper();

    @Override
    public List<UserGroupDTO> findByUserId(Long userId) {
        List<UserGroupEntity> userGroupEntities = userGroupRepository.findByUserId(userId);
        List<UserGroupDTO> userGroupDTOS = new ArrayList<>();
        for (UserGroupEntity item : userGroupEntities) {
            userGroupDTOS.add(mapper.map(item, UserGroupDTO.class));
        }
        return userGroupDTOS;
    }
}
