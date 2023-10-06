package com.example.task.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class SendEmailService {
    @Autowired
    private MailSender mailSender;

    public void sendMail(String to, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Bạn vừa nhận được công việc có tiêu đề là " + title);
        message.setText("Đây là nội dung công việc ,bạn có thể truy cập website để biết thêm thông tin :" + content);
        mailSender.send(message);
    }
}
