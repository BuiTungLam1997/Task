package com.example.task.service;

import com.example.task.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICommentService {
    List<CommentDTO> findAll();

    List<CommentDTO> findAll(Pageable pageable);

    CommentDTO save(CommentDTO commentDTO);

    CommentDTO update(CommentDTO commentDTO);

    void delete(Long[] ids);

    List<CommentDTO> findByTaskId(Long taskId);

    int countByTaskId(Long taskId);

    List<CommentDTO> setListResult(Long taskId);

    Optional<CommentDTO> findById(Long id);

    Page<CommentDTO> query(Pageable pageable);

}
