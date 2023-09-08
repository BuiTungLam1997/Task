package com.example.task.dto;

import com.example.task.dto.constant.Roles;
import com.example.task.dto.constant.StatusTask;

import java.sql.Timestamp;


public class TaskDTO extends BaseDTO<TaskDTO> {

    private String title;

    private String content;

    private Timestamp deadlineStart;

    private Timestamp deadlineEnd;

    private StatusTask status = StatusTask.READY;

    private String createdBy;

    private String performer = String.valueOf(Roles.admin);

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

    public StatusTask getStatus() {
        return status;
    }

    public void setStatus(StatusTask status) {
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
