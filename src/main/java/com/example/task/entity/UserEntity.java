package com.example.task.entity;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@FieldNameConstants
@Data
public class UserEntity extends BaseEntity {
    @Column()
    private String username;
    @Column()
    private String password;
    @Column(name = "full_name")
    private String fullName;
    @Column()
    private String status;
    @Column(name = "created_date")
    @CreatedDate
    private Date createdDate;
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Column()
    private String email;
    @Column
    private Integer totalPoint;
}
