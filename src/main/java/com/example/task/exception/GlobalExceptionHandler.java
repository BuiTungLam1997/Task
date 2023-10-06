package com.example.task.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerException(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(500).body("Unknown error!");
    }
    @ExceptionHandler(CustomAppException.class)
    public  ResponseEntity<String> customException(CustomAppException ex){
        ex.printStackTrace();
        return ResponseEntity.status(500).body("Custom error");
    }
}
