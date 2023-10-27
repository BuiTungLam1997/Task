package com.example.task.service.builderpattern.Filter;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Filter {
    private String field;
    private QueryOperator operator;
    private Object value;

    public Filter(String field, QueryOperator operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public enum QueryOperator {
        EQUALS,
        LIKE,
        IN,
        LESSTHAN,
        LESSTHANOREQUAL
    }
}

