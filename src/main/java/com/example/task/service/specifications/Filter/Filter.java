package com.example.task.service.specifications.Filter;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Filter {
    private String field;
    private QueryOperator operator;
    private String value;

    public Filter(String field, QueryOperator operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public enum QueryOperator {
        EQUALS,
        LIKE,
        IN
    }
}

