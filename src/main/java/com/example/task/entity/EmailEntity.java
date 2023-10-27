package com.example.task.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "email")
@EntityListeners(AuditingEntityListener.class)
@Data
public class EmailEntity extends BaseEntity {
    @Column
    private String toEmail;
    @Column
    private String title;
    @Column
    private String content;
    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;
    @Column
    private String statusSent;
}
