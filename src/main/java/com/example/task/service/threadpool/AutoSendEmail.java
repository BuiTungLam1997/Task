package com.example.task.service.threadpool;

import com.example.task.dto.EmailDTO;
import com.example.task.dto.constant.StatusSent;
import com.example.task.dto.constant.SystemConstant;
import com.example.task.service.impl.EmailService;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.task.dto.constant.StatusSent.*;
import static com.example.task.dto.constant.SystemConstant.LIMIT;

@Component
public class AutoSendEmail {
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    EmailService emailService;

    public AutoSendEmail(EmailService emailService) {
        this.emailService = emailService;
        scheduledExecutorService.scheduleAtFixedRate(this::job, 0, 2, TimeUnit.MINUTES);
    }

    public void job() {
        try {
            List<EmailDTO> emailDTOS = emailService.findBySent(String.valueOf(UNSENT), LIMIT);
            while (!emailDTOS.isEmpty()) {
                for (EmailDTO email : emailDTOS) {
                    //executorService.submit(sendEmail(email));
                }
                // emailDTOS = emailService.findBySent(String.valueOf(UNSENT), LIMIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Runnable sendEmail(EmailDTO email) {
        return () -> {
            try {
                emailService.sendMail(email.getId());
                email.setStatusSent(String.valueOf(SENT));
                emailService.update(email);
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
