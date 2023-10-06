package com.example.task.service.impl;

import com.example.task.dto.UserGroupDTO;
import com.example.task.repository.UserGroupRepository;
import com.example.task.service.IUserGroupService;
import com.example.task.transformer.UserGroupTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void deleteByUserId(Long userId) {
        userGroupRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteByPermissionId(Long permissionId) {
        userGroupRepository.deleteByPermissionId(permissionId);
    }

    @Override
    public void deleteByGroupId(Long groupId) {
        userGroupRepository.deleteByGroupId(groupId);
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

    @Override
    public List<UserGroupDTO> findByGroupId(Long groupId) {
        return userGroupRepository.findAllByGroupId(groupId).stream()
                .map(userGroupTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserGroupDTO> findAllPage(Pageable pageable) {
        return userGroupRepository.findAll(pageable)
                .map(userGroupTransformer::toDto);
    }

    @Override
    public List<UserGroupDTO> findAll(Pageable pageable) {
        return userGroupRepository.findAll(pageable).stream()
                .map(userGroupTransformer::toDto)
                .collect(Collectors.toList());
    }
}
