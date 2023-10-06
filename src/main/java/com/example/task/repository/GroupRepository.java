package com.example.task.repository;

import com.example.task.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity,Long> {

    Optional<GroupEntity> findByCode(String code);
}
