package com.example.task.dto;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.util.Date;

public class EmailDTO extends BaseDTO<EmailDTO>{

    private String toEmail;

    private String title;

    private String content;

    private Date createdDate;
    private String statusSent;

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatusSent() {
        return statusSent;
    }

    public void setStatusSent(String statusSent) {
        this.statusSent = statusSent;
    }
}
