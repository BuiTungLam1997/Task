package com.example.task.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "comment")
@SuperBuilder
@NoArgsConstructor
@Data
public class CommentEntity extends BaseEntity {

    private Long userId;
    private Long taskId;
    private String content;
    @Column
    @CreatedDate
    private LocalDate createdDate;
    @Transient
    private String fullName;

}
