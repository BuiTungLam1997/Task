package com.example.task.service.impl;

import com.example.task.dto.PermissionDTO;
import com.example.task.dto.PermissionGroupDTO;
import com.example.task.entity.PermissionEntity;
import com.example.task.entity.PermissionGroupEntity;
import com.example.task.repository.PermissionGroupRepository;
import com.example.task.repository.PermissionRepository;
import com.example.task.service.IPermissionService;
import com.example.task.service.IUserGroupService;
import com.example.task.transformer.CommonTransformer;
import com.example.task.transformer.PermissionTransformer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionService extends BaseService<PermissionDTO, PermissionEntity, PermissionRepository> implements IPermissionService {
    @Autowired

    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionTransformer permissionTransformer;
    @Autowired
    private PermissionGroupRepository permissionGroupRepository;
    @Autowired
    private IPermissionService permissionService;

    public PermissionService(PermissionRepository repo, CommonTransformer<PermissionDTO, PermissionEntity> transformer, EntityManager em) {
        super(repo, transformer, em);
    }


    @Override
    public PermissionDTO save(PermissionDTO permissionDTO) {
        return permissionTransformer.toDto(permissionRepository.save(permissionTransformer.toEntity(permissionDTO)));
    }

    @Override
    public PermissionDTO update(PermissionDTO permissionDTO) {
        return permissionTransformer.toDto(permissionRepository.save(permissionTransformer.toEntity(permissionDTO)));
    }

    @Override
    public void delete(Long[] ids) {
        for (Long item : ids) {
            permissionRepository.deleteById(item);
        }
    }

    @Override
    public List<PermissionDTO> findByGroupId(Long groupId) {
        List<PermissionEntity> list = permissionGroupRepository.findByGroupId(groupId);
        return list.stream()
                .map(transformer::toDto)
                .collect(Collectors.toList());

    }
}
