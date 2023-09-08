package com.example.task.service.impl;

import com.example.task.dto.CommentDTO;
import com.example.task.entity.CommentEntity;
import com.example.task.entity.TaskEntity;
import com.example.task.repository.CommentRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.ICommentService;
import com.example.task.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService extends BaseService implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserService userService;

    @Override
    public List<CommentDTO> findAll() {
        return null;
    }

    @Override
    public List<CommentDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        CommentEntity commentEntity = modelMapper.map(commentDTO, CommentEntity.class);
        return modelMapper.map(commentRepository.save(commentEntity), CommentDTO.class);
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO) {
        return null;
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            commentRepository.deleteById(id);
        }
    }

    @Override
    public List<CommentDTO> findByTaskId(Long taskId) {
        List<CommentEntity> commentEntity = commentRepository.findByTaskId(taskId);
        return commentEntity.stream()
                .map(item -> modelMapper.map(item, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int countByTaskId(Long taskId) {
        return commentRepository.countByTaskId(taskId);
    }

    @Override
    public List<CommentDTO> setListResult(Long taskId) {
        List<CommentEntity> commentEntities = commentRepository.findByTaskId(taskId);
        return commentEntities.stream()
                .map(item -> modelMapper.map(item, CommentDTO.class))
                .peek(item -> item.setFullName(userRepository.findById(item.getUserId()).get().getFullName()))
                .collect(Collectors.toList());
    }
}
