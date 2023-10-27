package com.example.task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permission_group")
@Data
public class PermissionGroupEntity extends BaseEntity {
    @Column
    private Long permissionId;
    @Column
    private Long GroupId;
}
