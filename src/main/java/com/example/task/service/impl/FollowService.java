package com.example.task.service.impl;

import com.example.task.dto.FollowDTO;
import com.example.task.entity.FollowEntity;
import com.example.task.repository.FollowRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.IFollowService;
import com.example.task.service.IUserService;
import com.example.task.transformer.CommonTransformer;
import com.example.task.transformer.FollowTransformer;
import com.example.task.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FollowService implements IFollowService {

    private FollowRepository followRepository;
    private UserRepository userRepository;
    private FollowTransformer followTransformer;

    @Override
    public void save(FollowDTO followDTO) {
        String username = SecurityUtils.getPrincipal().getUsername();
        Long userId = userRepository.findByUsername(username).orElseThrow(NullPointerException::new).getId();
        List<FollowDTO> listFollow = findByUserId(userId);
        for (Long taskId : followDTO.getTaskIds()) {
            for (FollowDTO item : listFollow) {
                if (Objects.equals(taskId, item.getTaskId())) {
                    continue;
                }
                followDTO.setTaskId(taskId);
                followDTO.setUserId(userId);
            }
        }
        followRepository.save(followTransformer.toEntity(followDTO));
    }

    @Override
    public List<FollowDTO> findByUserId(Long userId) {
        return followRepository.findByUserId(userId)
                .stream()
                .map(followTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long[] taskIds) {
            followRepository.deleteAllById(Arrays.asList(taskIds));

    }

    @Override
    public List<FollowDTO> findAll() {
        return followRepository.findAll().stream()
                .map(followTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FollowDTO> findAll(Pageable pageable) {
        return followRepository.findAll().stream()
                .map(followTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FollowDTO> findById(Long id) {
        return followRepository.findById(id).map(followTransformer::toDto);
    }

    @Override
    public Page<FollowDTO> query(Pageable pageable) {
        return followRepository.findAll(pageable)
                .map(item -> followTransformer.toDto(item));
    }
}
