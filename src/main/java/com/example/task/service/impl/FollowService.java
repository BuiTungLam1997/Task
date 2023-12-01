package com.example.task.service.impl;

import com.example.task.dto.FollowDTO;
import com.example.task.entity.FollowEntity;
import com.example.task.repository.FollowRepository;
import com.example.task.service.IFollowService;
import com.example.task.service.IUserService;
import com.example.task.transformer.CommonTransformer;
import com.example.task.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FollowService extends BaseService<FollowDTO, FollowEntity, FollowRepository> implements IFollowService {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private IUserService userService;

    public FollowService(FollowRepository repo, CommonTransformer<FollowDTO, FollowEntity> transformer, EntityManager em) {
        super(repo, transformer, em);
    }

    @Override
    public void save(FollowDTO followDTO) {
        String username = SecurityUtils.getPrincipal().getUsername();
        Long userId = userService.findByUsername(username).orElseThrow(NullPointerException::new).getId();
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
        followRepository.save(transformer.toEntity(followDTO));
    }

    @Override
    public List<FollowDTO> findByUserId(Long userId) {
        return followRepository.findByUserId(userId)
                .stream()
                .map(transformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long[] taskIds) {
        for (Long taskId : taskIds) {
            followRepository.deleteByTaskId(taskId);
        }
    }
}
