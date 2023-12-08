package com.example.task.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ResponseService<T> {
    private String message;
    private T data;
    private Integer totalPages;
    private Integer currentPage;
    private Integer limit;
    private String status;


    public ResponseService(String message, T data, int totalPages, int currentPage, int limit, String status) {
        this.message = message;
        this.data = data;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.status = status;
        this.limit = limit;
    }

    public ResponseService(T data, int totalPages, int currentPage) {
        this.data = data;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public ResponseService(String message, T data, String status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public ResponseService(T data, String status) {
        this.data = data;
        this.status = status;
    }

    public ResponseService(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseService(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public ResponseService(String message) {
        this.message = message;
    }

    public ResponseService() {
    }

    public static ResponseEntity<ResponseService> success() {
        return new ResponseEntity<>(new ResponseService<>("Success", "200"), HttpStatus.OK);
    }

    public static ResponseEntity<ResponseService> error() {
        return new ResponseEntity<>(new ResponseService<>("Error!", "400"), HttpStatus.OK);
    }
}
