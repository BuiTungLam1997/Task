package com.example.task.service.impl;

import com.example.task.dto.GroupDTO;
import com.example.task.repository.GroupRepository;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserGroupService;
import com.example.task.transformer.GroupTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService extends BaseService implements IGroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private IUserGroupService userGroupService;
    @Autowired
    private GroupTransformer groupTransformer;


    @Override
    public GroupDTO findById(Long groupId) {
        return groupTransformer.opToDto(groupRepository.findById(groupId));
    }

    @Override
    public List<GroupDTO> findByUserId(Long userId) {
        return userGroupService.findGroupId(userId).stream()
                .map(item -> groupRepository.findById(item))
                .map(item -> groupTransformer.opToDto(item))
                .collect(Collectors.toList());

    }

    @Override
    public GroupDTO save(GroupDTO groupDTO) {
        return groupTransformer.toDto(groupRepository.save(groupTransformer.toEntity(groupDTO)));
    }

    @Override
    public GroupDTO update(GroupDTO groupDTO) {
        return groupTransformer.toDto(groupRepository.save(groupTransformer.toEntity(groupDTO)));
    }

    @Override
    public void delete(Long[] ids) {

        for (Long item : ids) {
            userGroupService.deleteByGroupId(item);
            groupRepository.deleteById(item);
        }
    }

    @Override
    public List<GroupDTO> findAll() {
        return groupRepository.findAll().stream()
                .map(groupTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<GroupDTO> findAllPage(Pageable pageable) {
        return groupRepository.findAll(pageable)
                .map(groupTransformer::toDto);
    }

    @Override
    public List<GroupDTO> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable).stream()
                .map(groupTransformer::toDto)
                .collect(Collectors.toList());
    }
}
