package com.example.task.dto;

import java.sql.Timestamp;
import java.util.Date;


public class TaskDTO extends BaseDTO<TaskDTO> {

    private String title;

    private String content;

    private Timestamp deadlineStart;

    private Timestamp deadlineEnd;

    private String status ;

    private String createdBy;

    private String performer;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }
}
