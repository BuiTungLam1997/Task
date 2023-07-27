package com.example.task.dto;

import java.util.ArrayList;
import java.util.List;

public class BaseDTO<T> {
    private Long id;
    private List<T> listResult = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<T> getListResult() {
        return listResult;
    }

    public void setListResult(List<T> listResult) {
        this.listResult = listResult;
    }
}
