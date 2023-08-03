package com.example.task.service.impl;

import com.example.task.dto.EmailDTO;
import com.example.task.dto.constant.SystemConstant;
import com.example.task.entity.EmailEntity;
import com.example.task.mail.SendEmailService;
import com.example.task.repository.EmailRepository;
import com.example.task.service.IEmailService;
import com.example.task.transformer.EmailTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.task.dto.constant.StatusSent.SENT;
import static com.example.task.dto.constant.StatusSent.UNSENT;

@Service
public class EmailService implements IEmailService {
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private EmailTransformer emailTransformer;

    @Override
    public List<EmailEntity> findBySent(String statusSent, int limit) {
        return emailRepository.findByStatusSent(statusSent, limit);
    }

    @Override
    public void sendMail(Long id) {
        Optional<EmailEntity> emailEntity = emailRepository.findById(id);
        if (emailEntity.isPresent()) {
            EmailEntity email = emailEntity.get();

            String to = email.getToEmail();
            String title = email.getTitle();
            String content = email.getContent();

            sendEmailService.sendMail(to, title, content);
            email.setStatusSent(String.valueOf(SENT));
            emailRepository.save(email);
        }
    }

    @Override
    public EmailDTO save(EmailDTO emailDTO) {
        return emailTransformer.toDto(emailRepository.save(emailTransformer.toEntity(emailDTO)));
    }

    @Override
    public void update(EmailDTO emailDTO) {
        try {
            emailRepository.save(emailTransformer.toEntity(emailDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
