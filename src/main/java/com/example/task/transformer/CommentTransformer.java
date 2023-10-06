package com.example.task.transformer;

import com.example.task.dto.CommentDTO;
import com.example.task.entity.CommentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentTransformer extends CommonTransformer<CommentDTO, CommentEntity> implements ITransformer<CommentDTO, CommentEntity> {

    public CommentTransformer(ModelMapper modelMapper) {
        super(CommentDTO.class, CommentEntity.class, modelMapper);
    }



}
