package com.example.task.service.impl;

import com.example.task.dto.CommentDTO;
import com.example.task.entity.CommentEntity;
import com.example.task.repository.CommentRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.ICommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService implements ICommentService {
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

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
        CommentEntity commentEntity = mapper.map(commentDTO, CommentEntity.class);
        return mapper.map(commentRepository.save(commentEntity), CommentDTO.class);
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
        List<CommentDTO> commentDTOS = new ArrayList<>();
        if (commentEntity.size() != 0) {
            for (CommentEntity item : commentEntity) {
                commentDTOS.add(mapper.map(item, CommentDTO.class));
            }
        }
        return commentDTOS;
    }

    @Override
    public int countByTaskId(Long taskId) {
        return commentRepository.countByTaskId(taskId);
    }

    @Override
    public List<CommentDTO> setListResult(Long taskId) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        List<CommentEntity> commentEntities = commentRepository.findByTaskId(taskId);
        for (CommentEntity item : commentEntities) {
            commentDTOS.add(mapper.map(item, CommentDTO.class));
        }
        for (CommentDTO item : commentDTOS) {
            item.setFullName(userRepository.findById(item.getUserId()).get().getFullName());
        }
        return commentDTOS;
    }
}
