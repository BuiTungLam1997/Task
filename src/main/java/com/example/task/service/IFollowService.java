package com.example.task.service;

import com.example.task.dto.FollowDTO;

import java.util.List;

public interface IFollowService extends IBaseService<FollowDTO>{
    void save(FollowDTO followDTO);
    List<FollowDTO> findByUserId(Long userId);
    void delete (Long[] taskIds);
}
