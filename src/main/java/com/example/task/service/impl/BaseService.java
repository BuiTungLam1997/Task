package com.example.task.service.impl;

import com.example.task.dto.BaseDTO;
import com.example.task.entity.BaseEntity;
import com.example.task.service.IBaseService;
import com.example.task.transformer.CommonTransformer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BaseService<T extends BaseDTO<T>, E extends BaseEntity, R extends JpaRepository<E, Long> & JpaSpecificationExecutor<E>>
        implements IBaseService<T> {
    protected R repo;

    protected CommonTransformer<T, E> transformer;

    @PersistenceContext
    EntityManager em;

    public EntityManager getEm() {
        return em;
    }
    @Override
    public List<T> findAll() {
        return repo.findAll().stream()
                .map(item -> transformer.toDto(item))
                .collect(Collectors.toList());
    }

    @Override
    public List<T> findAll(Pageable pageable) {
        return repo.findAll().stream()
                .map(transformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<T> findById(Long id) {
        return repo.findById(id).map(transformer::toDto);
    }

    @Override
    public Page<T> query(Pageable pageable) {
        return repo.findAll(pageable)
                .map(item -> transformer.toDto(item));
    }
}
