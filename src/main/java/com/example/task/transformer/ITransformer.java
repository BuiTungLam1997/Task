package com.example.task.transformer;

import java.util.Optional;

public interface ITransformer<T,E> {
    T toDto (E e);
    T opToDto(Optional<E> e);
    E toEntity(T t);
    E opToEntity(Optional<T> t);
}
