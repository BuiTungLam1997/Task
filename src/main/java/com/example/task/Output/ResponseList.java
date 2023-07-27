package com.example.task.Output;

import java.util.ArrayList;
import java.util.List;

public class ResponseList<T> {
    private String status;
    private String message;
    private List<T> data = new ArrayList<>();

    public ResponseList() {
    }

    public ResponseList(String status, String message, List<T> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
