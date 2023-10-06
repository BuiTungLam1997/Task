package com.example.task.service;

import com.example.task.dto.EmailDTO;
import com.example.task.dto.constant.StatusSent;
import com.example.task.entity.EmailEntity;

import java.util.List;

public interface IEmailService {
    List<EmailEntity> findBySent(String statusSent,int limit);

    void sendMail(Long id);

    EmailDTO save(EmailDTO emailDTO);

    void update(EmailDTO emailDTO);
}
