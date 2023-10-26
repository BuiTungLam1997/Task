package com.example.task.service.impl;

import com.example.task.dto.GroupDTO;
import com.example.task.entity.GroupEntity;
import com.example.task.repository.GroupRepository;
import com.example.task.service.IGroupService;
import com.example.task.service.IUserGroupService;
import com.example.task.transformer.CommonTransformer;
import com.example.task.transformer.GroupTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService extends BaseService<GroupDTO, GroupEntity, GroupRepository> implements IGroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private IUserGroupService userGroupService;
    @Autowired
    private GroupTransformer groupTransformer;

    public GroupService(GroupRepository repo, CommonTransformer<GroupDTO, GroupEntity> transformer, EntityManager em) {
        super(repo, transformer, em);
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
}
