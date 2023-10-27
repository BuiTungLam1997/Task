package com.example.task.service;

import com.example.task.dto.EmailDTO;

import java.util.List;

public interface IEmailService {
    List<EmailDTO> findBySent(String statusSent,int limit);

    void sendMail(Long id);

    EmailDTO save(EmailDTO emailDTO);

    void update(EmailDTO emailDTO);
    EmailDTO findById(Long id);
}
