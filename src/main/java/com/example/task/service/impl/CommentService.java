package com.example.task.service.impl;

import com.example.task.dto.CommentDTO;
import com.example.task.entity.CommentEntity;
import com.example.task.repository.CommentRepository;
import com.example.task.repository.UserRepository;
import com.example.task.repository.projection.CommentProjection;
import com.example.task.service.ICommentService;
import com.example.task.transformer.CommentTransformer;
import com.example.task.transformer.CommonTransformer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService extends BaseService<CommentDTO, CommentEntity, CommentRepository> implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentTransformer commentTransformer;

    public CommentService(CommentRepository repo, CommonTransformer<CommentDTO, CommentEntity> transformer, EntityManager em) {
        super(repo, transformer, em);
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        return commentTransformer.toDto(commentRepository.save(commentTransformer.toEntity(commentDTO)));
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO) {
        return commentTransformer.toDto(commentRepository.save(commentTransformer.toEntity(commentDTO)));
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            commentRepository.deleteById(id);
        }
    }

    @Override
    public List<CommentDTO> findByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId).stream()
                .map(CommentProjection::toEntity)
                .map(commentTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public int countByTaskId(Long taskId) {
        return commentRepository.countByTaskId(taskId);
    }

    @Override
    public List<CommentDTO> setListResult(Long taskId) {
        return commentRepository.findByTaskId(taskId).stream()
                .map(CommentProjection::toEntity)
                .map(commentTransformer::toDto)
                .collect(Collectors.toList());
    }
}
