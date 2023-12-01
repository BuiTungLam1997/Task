package com.example.task.dto;

import com.example.task.dto.constant.StatusTask;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class TaskDTO extends BaseDTO<TaskDTO> {

    private String title;

    private String content;

    private LocalDate deadlineStart;

    private LocalDate deadlineEnd;

    private StatusTask status;

    private String createdBy;

    private String performer;
    private List<String> listUsername;
    private String note;
    private int levelOfDifficulty;
    private Integer point;
}
