package com.example.task.service.threadpool;

import com.example.task.dto.constant.StatusSent;
import com.example.task.dto.constant.SystemConstant;
import com.example.task.entity.EmailEntity;
import com.example.task.service.impl.EmailService;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class AutoSendEmail {
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    EmailService emailService;

    private static final String UNSENT = String.valueOf(StatusSent.UNSENT);
    private static final int LIMIT = SystemConstant.LIMIT;

    public AutoSendEmail(EmailService emailService) {
        this.emailService = emailService;
        scheduledExecutorService.scheduleAtFixedRate((this::job), 0, 2, TimeUnit.MINUTES);
    }

    public void job() {
        try {
            List<EmailEntity> entities = emailService.findBySent(UNSENT, LIMIT);
            while (!entities.isEmpty()) {
                for (EmailEntity email : entities) {
                    executorService.submit(sendEmail(email));
                }
                entities = emailService.findBySent(UNSENT, LIMIT);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Runnable sendEmail(EmailEntity email) {
        return () -> {
            try {
                Thread.sleep(300);
                emailService.sendMail(email.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @PreDestroy
    public void preDestroy() {
        scheduledExecutorService.shutdown();
        executorService.shutdown();
    }
}
