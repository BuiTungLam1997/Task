package com.example.task.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class EmailDTO extends BaseDTO<EmailDTO>{

    private String toEmail;

    private String title;

    private String content;

    private LocalDate createdDate;
    private String statusSent;
}
