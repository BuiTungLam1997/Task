package com.example.task.transformer;

import com.example.task.dto.BaseDTO;
import com.example.task.entity.BaseEntity;
import org.modelmapper.ModelMapper;

import java.util.Optional;

public abstract class CommonTransformer<T extends BaseDTO, E extends BaseEntity> implements ITransformer<T, E> {
    private final Class<T> tClass;
    private final Class<E> eClass;
    private final ModelMapper modelMapper;

    public CommonTransformer(Class<T> tClass, Class<E> eClass, ModelMapper modelMapper) {
        this.tClass = tClass;
        this.eClass = eClass;
        this.modelMapper = modelMapper;
    }

    @Override
    public T toDto(E e) {
        return modelMapper.map(e, tClass);
    }

    @Override
    public E toEntity(T t) {
        return modelMapper.map(t, eClass);
    }

    @Override
    public T opToDto(Optional<E> e) {
        return modelMapper.map(e, tClass);
    }

    @Override
    public E opToEntity(Optional<T> t) {
        return modelMapper.map(t, eClass);
    }
}
