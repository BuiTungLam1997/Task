package com.example.task.transformer;

import com.example.task.dto.EmailDTO;
import com.example.task.entity.EmailEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmailTransformer extends CommonTransformer<EmailDTO, EmailEntity> implements ITransformer<EmailDTO, EmailEntity> {

    public EmailTransformer(ModelMapper modelMapper) {
        super(EmailDTO.class, EmailEntity.class, modelMapper);
    }
}
