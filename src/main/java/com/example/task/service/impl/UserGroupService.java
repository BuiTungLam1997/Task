package com.example.task.service.impl;

import com.example.task.dto.UserGroupDTO;
import com.example.task.entity.UserGroupEntity;
import com.example.task.repository.UserGroupRepository;
import com.example.task.service.IUserGroupService;
import com.example.task.transformer.CommonTransformer;
import com.example.task.transformer.UserGroupTransformer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserGroupService extends BaseService<UserGroupDTO, UserGroupEntity,UserGroupRepository> implements IUserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserGroupTransformer userGroupTransformer;

    public UserGroupService(UserGroupRepository repo, CommonTransformer<UserGroupDTO, UserGroupEntity> transformer, EntityManager em) {
        super(repo, transformer, em);
    }


    @Override
    public List<Long> findGroupId(Long userId) {
        return userGroupRepository.findByUserId(userId);
    }

    @Override
    public UserGroupDTO save(UserGroupDTO userGroupDTO) {
        if (userGroupDTO.getUserIds().length == 0) {
            return userGroupTransformer.toDto(userGroupRepository.save(userGroupTransformer.toEntity(userGroupDTO)));
        }
        for (Long userId : userGroupDTO.getUserIds()) {
            userGroupDTO.setUserId(userId);
            userGroupRepository.save(userGroupTransformer.toEntity(userGroupDTO));
        }
        return userGroupDTO;
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
    public void deleteByGroupId(Long groupId) {
        userGroupRepository.deleteByGroupId(groupId);
    }

    @Override
    public void save(Long groupId, Long[] userId) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setGroupId(groupId);
        for (Long id : userId) {
            userGroupDTO.setUserId(id);
            userGroupRepository.save(userGroupTransformer.toEntity(userGroupDTO));
        }
    }

    @Override
    public List<UserGroupDTO> findByGroupId(Long groupId) {
        return userGroupRepository.findAllByGroupId(groupId).stream()
                .map(userGroupTransformer::toDto)
                .collect(Collectors.toList());
    }
}
