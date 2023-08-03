package com.example.task.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomAppException extends RuntimeException{
    private int code;
    private String message;

}
