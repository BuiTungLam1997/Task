package com.example.task.repository;

import com.example.task.entity.PermissionEntity;
import com.example.task.entity.PermissionGroupEntity;
import com.example.task.entity.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionGroupRepository extends JpaRepository<PermissionGroupEntity, Long>,
        JpaSpecificationExecutor<PermissionGroupEntity> {

    @Query(value = "select p from PermissionEntity p inner join PermissionGroupEntity pg on pg.permissionId = p.id where pg.GroupId = ?1")
    List<PermissionEntity> findByGroupId(Long groupId);
}
