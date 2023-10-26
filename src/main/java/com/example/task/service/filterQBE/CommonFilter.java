package com.example.task.service.filterQBE;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CommonFilter<T,E> implements IFilter<T,E> {
    private final ModelMapper modelMapper;
    private final JpaRepository<E ,Long> repository;
    private final Class<E> eClass;
    private final Class<T> tClass;

    public CommonFilter(ModelMapper modelMapper, JpaRepository<E, Long> repository, Class<E> eClass, Class<T> tClass) {
        this.modelMapper = modelMapper;
        this.repository = repository;
        this.eClass = eClass;
        this.tClass = tClass;
    }

    @Override
    public List<T> search(T t,E e, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        E entity = modelMapper.map(t, eClass);
        Example<E> example = Example.of(entity, matcher);

        return  repository.findAll(example,pageable).stream()
                .map(item -> modelMapper.map(item, tClass))
                .collect(Collectors.toList());
    }
}
