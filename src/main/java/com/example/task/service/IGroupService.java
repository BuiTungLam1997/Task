package com.example.task.service;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGroupService {
    GroupDTO findById(Long groupId);
    List<GroupDTO> findByUserId(Long userId);
    GroupDTO save(GroupDTO groupDTO);
    GroupDTO update(GroupDTO groupDTO);
    void delete(Long[] ids);
    List<GroupDTO> findAll();
    Page<GroupDTO> findAllPage(Pageable pageable);
    List<GroupDTO> findAll(Pageable pageable);
}
