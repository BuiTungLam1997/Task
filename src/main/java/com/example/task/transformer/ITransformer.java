package com.example.task.transformer;

import com.example.task.dto.BaseDTO;
import com.example.task.entity.BaseEntity;
import java.util.Optional;

public interface ITransformer<T extends BaseDTO,E extends BaseEntity> {
    T toDto (E e);
    T opToDto(Optional<E> e);
    E toEntity(T t);
    E opToEntity(Optional<T> t);
}
