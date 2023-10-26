package com.example.task.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class CommentDTO extends BaseDTO<CommentDTO> {
    private Long userId;
    private Long taskId;
    private String content;
    private LocalDate createdDate;
    private int totalComment;
    private String fullName;
    private String username;
}
