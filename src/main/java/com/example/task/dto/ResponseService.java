package com.example.task.dto;

import lombok.Data;

@Data
public class ResponseService {
    private String message;
    private Object data;
    private Integer totalPages;
    private Integer currentPage;
    private Integer limit;
    private String status;


    public ResponseService(String message, Object data, int totalPages, int currentPage, int limit, String status) {
        this.message = message;
        this.data = data;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.status = status;
        this.limit = limit;
    }

    public ResponseService(Object date, int totalPages, int currentPage) {
        this.data = date;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public ResponseService(String message, Object data, String status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public ResponseService(Object data, String status) {
        this.data = data;
        this.status = status;
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
}
