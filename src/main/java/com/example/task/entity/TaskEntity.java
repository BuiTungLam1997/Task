package com.example.task.entity;

import com.example.task.dto.constant.Roles;
import com.example.task.dto.constant.StatusTask;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)
@FieldNameConstants
@Data
public class TaskEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7941769011539363185L;
    @Column()
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column()
    private String status = String.valueOf(StatusTask.READY);
    @Column()
    private String performer;
    @Column(name = "deadline_start")
    private LocalDate deadlineStart;
    @Column(name = "deadline_end")
    private LocalDate deadlineEnd;
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Column
    private String note;
    @Column
    private Integer point = 0;
}
