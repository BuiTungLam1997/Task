package com.example.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomAppException.class)
    public ResponseEntity<Object> customException(CustomAppException ex, WebRequest request) {
        return new ResponseEntity<>("Đối tượng không tồn tại",HttpStatus.NOT_FOUND);
    }
}
