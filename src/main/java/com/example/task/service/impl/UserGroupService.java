package com.example.task.service.impl;

import com.example.task.dto.UserGroupDTO;
import com.example.task.repository.UserGroupRepository;
import com.example.task.service.IUserGroupService;
import com.example.task.transformer.UserGroupTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService extends BaseService implements IUserGroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserGroupTransformer userGroupTransformer;


    @Override
    public List<Long> findGroupId(Long userId) {
        return userGroupRepository.findByUserId(userId);
    }

    @Override
    public UserGroupDTO save(UserGroupDTO userGroupDTO) {
        return userGroupTransformer.toDto(userGroupRepository.save(userGroupTransformer.toEntity(userGroupDTO)));
    }

    @Override
    public UserGroupDTO update(UserGroupDTO userGroupDTO) {
        return userGroupTransformer.toDto(userGroupRepository.save(userGroupTransformer.toEntity(userGroupDTO)));
    }

    @Override
    public void delete(Long[] ids) {
        for (Long item : ids) {
            userGroupRepository.deleteById(item);
        }
    }

    @Override
    public void save(Long groupId, Long[] userId, Long[] permissionId) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setGroupId(groupId);
        for (int i = 0; i < userId.length; i++) {
            for (int j = 0; j < permissionId.length; j++) {
                userGroupDTO.setUserId(userId[i]);
                userGroupDTO.setPermissionId(permissionId[j]);
                userGroupRepository.save(userGroupTransformer.toEntity(userGroupDTO));
            }
        }
    }
}
