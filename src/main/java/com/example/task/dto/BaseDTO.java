package com.example.task.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BaseDTO<T> {
    private Long id;
    private List<T> listResult = new ArrayList<>();
    private int page;
    private int totalPage;
    private int limit;
    private int totalItem;
    private String message;
    private Long[] ids;
    private String searchResponse;
    private Integer totalPoint;
}
