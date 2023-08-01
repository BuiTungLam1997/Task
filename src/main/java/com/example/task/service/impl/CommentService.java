package com.example.task.service.impl;

import com.example.task.dto.CommentDTO;
import com.example.task.repository.CommentRepository;
import com.example.task.service.ICommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private CommentRepository commentRepository;

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
        return null;
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
}
