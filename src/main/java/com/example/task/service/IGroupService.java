package com.example.task.service;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IGroupService {
    List<GroupDTO> findByUserId(Long userId);
    GroupDTO save(GroupDTO groupDTO);
    GroupDTO update(GroupDTO groupDTO);
    void delete(Long[] ids);
    List<GroupDTO> findAll();

    List<GroupDTO> findAll(Pageable pageable);

    Optional<GroupDTO> findById(Long id);

    Page<GroupDTO> query(Pageable pageable);
}
