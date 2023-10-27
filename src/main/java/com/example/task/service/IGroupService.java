package com.example.task.service;

import com.example.task.dto.GroupDTO;
import com.example.task.dto.UserGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGroupService extends IBaseService<GroupDTO>{
    List<GroupDTO> findByUserId(Long userId);
    GroupDTO save(GroupDTO groupDTO);
    GroupDTO update(GroupDTO groupDTO);
    void delete(Long[] ids);
}
