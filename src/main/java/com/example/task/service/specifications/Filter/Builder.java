package com.example.task.service.specifications.Filter;

public interface Builder {
   Builder buildField(String field);
   Builder buildOperator(Filter.QueryOperator operator);
   Builder buildValue(String value);
   Filter build();

}
