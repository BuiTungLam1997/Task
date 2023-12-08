package com.example.task.service;

import com.example.task.dto.EmailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEmailService {
    List<EmailDTO> findBySent(String statusSent, int limit);

    void sendMail(Long id);

    EmailDTO save(EmailDTO emailDTO);

    void update(EmailDTO emailDTO);

    List<EmailDTO> findAll();

    List<EmailDTO> findAll(Pageable pageable);

    Optional<EmailDTO> findById(Long id);

    Page<EmailDTO> query(Pageable pageable);
}
