package com.example.task.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class CustomAppException extends RuntimeException {
    private int code;
    private String message;

}
