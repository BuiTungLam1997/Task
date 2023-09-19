package com.example.task.entity;

import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)
@FieldNameConstants
public class TaskEntity extends BaseEntity  implements Serializable {
    private static final long serialVersionUID = -7941769011539363185L;
    @Column()
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column()
    private String status;
    @Column()
    private String performer;
    @Column(name = "deadline_start")
    private Timestamp deadlineStart;
    @Column(name = "deadline_end")
    private Timestamp deadlineEnd;
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public Timestamp getDeadlineStart() {
        return deadlineStart;
    }

    public void setDeadlineStart(Timestamp deadlineStart) {
        this.deadlineStart = deadlineStart;
    }

    public Timestamp getDeadlineEnd() {
        return deadlineEnd;
    }

    public void setDeadlineEnd(Timestamp deadlineEnd) {
        this.deadlineEnd = deadlineEnd;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
