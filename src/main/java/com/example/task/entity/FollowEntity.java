package com.example.task.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "follow")
@Data
public class FollowEntity extends BaseEntity {
    @Column
    private Long userId;
    @Column
    private Long taskId;
}
