package com.example.task.service.impl;

import com.example.task.dto.UserGroupDTO;
import com.example.task.entity.UserGroupEntity;
import com.example.task.repository.UserGroupRepository;
import com.example.task.service.IUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService extends BaseService implements IUserGroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;


    @Override
    public List<Long> findGroupId(Long userId) {
        return userGroupRepository.findByUserId(userId);
    }

    @Override
    public UserGroupDTO save(UserGroupDTO userGroupDTO) {
        UserGroupEntity userGroupEntity = modelMapper.map(userGroupDTO, UserGroupEntity.class);
        return modelMapper.map(userGroupRepository.save(userGroupEntity), UserGroupDTO.class);
    }

    @Override
    public void save(Long groupId, Long[] userId, Long[] permissionId) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupDTO.setGroupId(groupId);
        for (int i = 0; i < userId.length; i++) {
            for (int j = 0; j < permissionId.length; j++) {
                userGroupDTO.setUserId(userId[i]);
                userGroupDTO.setPermissionId(permissionId[j]);
                userGroupEntity = modelMapper.map(userGroupDTO, UserGroupEntity.class);
                userGroupDTO = modelMapper.map(userGroupRepository.save(userGroupEntity), UserGroupDTO.class);
            }
        }
    }
}
