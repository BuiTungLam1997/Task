package com.example.task.service.impl;

import com.example.task.dto.GroupDTO;
import com.example.task.repository.GroupRepository;
import com.example.task.repository.UserGroupRepository;
import com.example.task.service.IGroupService;
import com.example.task.transformer.GroupTransformer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupService implements IGroupService {

    private GroupRepository groupRepository;

    private UserGroupRepository userGroupRepository;

    private GroupTransformer groupTransformer;


    @Override
    public List<GroupDTO> findByUserId(Long userId) {
        return userGroupRepository.findByGroupId(userId).stream()
                .map(groupRepository::findById)
                .map(item -> item.orElse(null))
                .filter(Objects::nonNull)
                .map(groupTransformer::toDto)
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
    @Transactional
    public void delete(Long[] ids) {
        for (Long item : ids) {
            userGroupRepository.deleteByGroupId(item);
            groupRepository.deleteById(item);
        }
    }

    @Override
    public List<GroupDTO> findAll() {
        return groupRepository.findAll().stream()
                .map(item -> groupTransformer.toDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupDTO> findAll(Pageable pageable) {
        return groupRepository.findAll().stream()
                .map(groupTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GroupDTO> findById(Long id) {
        return groupRepository.findById(id).map(groupTransformer::toDto);
    }

    @Override
    public Page<GroupDTO> query(Pageable pageable) {
        return groupRepository.findAll(pageable)
                .map(item -> groupTransformer.toDto(item));
    }
}
