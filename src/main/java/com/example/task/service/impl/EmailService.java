package com.example.task.service.impl;

import com.example.task.dto.EmailDTO;
import com.example.task.entity.EmailEntity;
import com.example.task.mail.SendEmailService;
import com.example.task.repository.EmailRepository;
import com.example.task.service.IEmailService;
import com.example.task.transformer.EmailTransformer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmailService implements IEmailService {

    EmailRepository emailRepository;
    SendEmailService sendEmailService;
    EmailTransformer emailTransformer;

    @Override
    public List<EmailDTO> findBySent(String statusSent, int limit) {
        return emailRepository.findByStatusSent(statusSent, limit)
                .stream()
                .map(emailTransformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void sendMail(Long id) {
        Optional<EmailEntity> emailEntity = emailRepository.findById(id);
        if (emailEntity.isPresent()) {
            EmailEntity email = emailEntity.get();

            String to = email.getToEmail();
            String title = email.getTitle();
            String content = email.getContent();

            sendEmailService.sendMail(to, title, content);
        }
    }

    @Override
    public EmailDTO save(EmailDTO emailDTO) {
        return emailTransformer.toDto(emailRepository.save(emailTransformer.toEntity(emailDTO)));
    }

    @Override
    @Transactional
    public void update(EmailDTO emailDTO) {
        try {
            EmailEntity email = emailTransformer.toEntity(emailDTO);
            emailRepository.save(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmailDTO findById(Long id) {
        return emailRepository.findById(id).map(emailTransformer::toDto).orElseThrow(NullPointerException::new);
    }
}
