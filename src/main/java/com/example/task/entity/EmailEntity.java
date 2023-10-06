package com.example.task.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "email")
@EntityListeners(AuditingEntityListener.class)
public class EmailEntity extends BaseEntity {
    @Column(name = "to_email")
    private String toEmail;
    @Column
    private String title;
    @Column
    private String content;
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "status_sent")
    private String statusSent;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

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

    public String getStatusSent() {
        return statusSent;
    }

    public void setStatusSent(String statusSent) {
        this.statusSent = statusSent;
    }
}
