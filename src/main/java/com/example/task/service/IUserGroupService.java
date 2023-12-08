package com.example.task.service;

import com.example.task.dto.UserGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserGroupService {
    List<Long> findGroupId(Long userId);

    List<UserGroupDTO> findByGroupId(Long groupId);

    UserGroupDTO save(UserGroupDTO userGroupDTO);

    UserGroupDTO update(UserGroupDTO userGroupDTO);

    void delete(Long[] ids);

    void deleteByUserId(Long userId);

    void deleteByGroupId(Long groupId);

    void save(Long groupId, Long[] userId);
    List<UserGroupDTO> findAll();

    List<UserGroupDTO> findAll(Pageable pageable);

    Optional<UserGroupDTO> findById(Long id);

    Page<UserGroupDTO> query(Pageable pageable);
}
