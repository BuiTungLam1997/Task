package com.example.task.service;

import com.example.task.dto.FollowDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IFollowService {
    void save(FollowDTO followDTO);
    List<FollowDTO> findByUserId(Long userId);
    void delete (Long[] taskIds);
    List<FollowDTO> findAll();

    List<FollowDTO> findAll(Pageable pageable);

    Optional<FollowDTO> findById(Long id);

    Page<FollowDTO> query(Pageable pageable);
}
