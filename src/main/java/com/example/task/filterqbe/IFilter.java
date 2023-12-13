package com.example.task.filterqbe;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFilter<T,E> {
    List<T> search(T t,E e, Pageable pageable);
}
