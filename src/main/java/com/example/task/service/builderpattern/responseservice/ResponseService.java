package com.example.task.service.builderpattern.responseservice;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseService {
    private String message;
    private Object data;
    private HttpStatus status;

    public ResponseService(String message, Object data, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }
}
