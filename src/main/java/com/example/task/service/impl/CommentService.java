package com.example.task.service.impl;

import com.example.task.dto.CommentDTO;
import com.example.task.repository.CommentRepository;
import com.example.task.repository.projection.CommentProjection;
import com.example.task.service.ICommentService;
import com.example.task.transformer.CommentTransformer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService implements ICommentService {
    private CommentRepository commentRepository;
    private CommentTransformer commentTransformer;

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

    @Override
    public List<CommentDTO> findAll() {
        return commentRepository.findAll().stream()
                .map(item -> commentTransformer.toDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> findAll(Pageable pageable) {
        return commentRepository.findAll().stream()
                .map(commentTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CommentDTO> findById(Long id) {
        return commentRepository.findById(id).map(commentTransformer::toDto);
    }

    @Override
    public Page<CommentDTO> query(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(item -> commentTransformer.toDto(item));
    }
}
